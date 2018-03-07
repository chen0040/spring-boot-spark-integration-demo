package com.github.chen0040.spark.sga.services;


import com.github.chen0040.data.commons.consts.SparkGraphMinerFilePath;
import com.github.chen0040.data.commons.models.CoPair;
import com.github.chen0040.data.commons.models.OneToManyToOneAssociation;
import com.github.chen0040.data.sga.services.CompanyService;
import com.github.chen0040.lang.commons.utils.CollectionUtil;
import com.github.chen0040.lang.commons.utils.StringUtils;
import com.github.chen0040.spark.sga.graph.GraphPageRanker;
import com.github.chen0040.spark.sga.graph.LabelPropagationCommunityFinder;
import com.github.chen0040.spark.sga.components.HadoopClient;
import com.google.common.base.Optional;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.*;
import java.util.*;


/**
 * Created by xschen on 9/2/2017.
 */
@Service
public class CompanyRuleMinerImpl implements CompanyRuleMiner {

   private static final Logger logger = LoggerFactory.getLogger(CompanyRuleMinerImpl.class);

   @Autowired
   private CompanyService companyService;

   @Value("${mine.debug}")
   private boolean debugMode;

   @Value("${mine.bigdata.hdfs.enabled}")
   private boolean hadoopEnabled;

   @Autowired
   private HadoopClient hadoopClient;

   @Override public String getVertexFilePath() {
      return SparkGraphMinerFilePath.CompanyVertexFilePath;
   }


   @Override public String getEdgeFilePath() {
      return SparkGraphMinerFilePath.CompanyEdgeFilePath;
   }

   @Override public String getRankFilePath() { return SparkGraphMinerFilePath.CompanyRankFilePath; }

   @Override public String getClusterFilePath() { return SparkGraphMinerFilePath.CompanyClusterFilePath; }


   @Override public void run(JavaSparkContext context, JavaPairRDD<String, String> skillCompanyRdd, int partitionCount) throws IOException {
      JavaPairRDD<String, Tuple2<String, Optional<String>>> rdd1 = (JavaPairRDD<String, Tuple2<String, Optional<String>>>)skillCompanyRdd.leftOuterJoin(skillCompanyRdd);

      rdd1 = rdd1.coalesce(partitionCount).cache();

      long count = rdd1.count();

      logger.info("left outer join for skill-company pair result: {}", count);



      JavaPairRDD<String, List<OneToManyToOneAssociation>> rdd2 = rdd1
              .mapToPair(entry -> {
                 String skill = entry._1();
                 Tuple2<String, Optional<String>> companyPair = entry._2();
                 String company1 = companyPair._1();
                 String company2 = companyPair._2().or("");
                 Set<String> skills = new HashSet<>();
                 skills.add(skill);
                 return new Tuple2<>(new CoPair(company1, company2), skills);
              })
              .filter(entry -> !StringUtils.isEmpty(entry._1().getItem1()) && !StringUtils.isEmpty(entry._1().getItem2()) && !entry._1().getItem1().equals(entry._1().getItem2()))
              .reduceByKey((a, b) -> {
                 Set<String> c = new HashSet<>(a);
                 c.addAll(b);
                 return c;
              })
              .mapToPair(entry -> {
                 CoPair pair = entry._1();
                 Set<String> skills = entry._2();
                 String company1 = pair.getItem1();
                 String company2 = pair.getItem2();
                 return new Tuple2<>(company1, new OneToManyToOneAssociation(company2, skills));
              })
              .combineByKey(a -> {
                 List<OneToManyToOneAssociation> result = new ArrayList<>();
                 result.add(a);
                 return result;
              }, (csa, a) -> {
                 List<OneToManyToOneAssociation> result = new ArrayList<>(csa);
                 boolean merged = false;
                 for(int i=0; i < result.size(); ++i) {
                    OneToManyToOneAssociation item = result.get(i);
                    if(item.getEntity2().equals(a.getEntity2())){
                       item.addLinks(a.getLinks());
                       merged =true;
                       break;
                    }
                 }
                 if(!merged) {
                    result.add(a);
                    if (result.size() > 10) {
                       result.sort((a1, a2) -> Integer.compare(a2.getCount(), a1.getCount()));
                       result = CollectionUtil.subList(result, 0, 10);
                    }
                 }

                 return result;
              }, (csa1, csa2) -> {
                 List<OneToManyToOneAssociation> result = new ArrayList<>(csa1);
                 result.addAll(csa2);
                 if(result.size() > 10){
                    result.sort((a1, a2) -> Integer.compare(a2.getCount(), a1.getCount()));
                    result = CollectionUtil.subList(result, 0, 10);
                 }

                 return result;
              }).mapValues(entry -> {
                 List<OneToManyToOneAssociation> result = new ArrayList<>(entry);
                 result.sort((a1, a2) -> Integer.compare(a2.getCount(), a1.getCount()));
                 return result;
              });

      Map<String, List<OneToManyToOneAssociation>> result = rdd2.collectAsMap();

      rdd1.unpersist();

      int maxMinCount = 0;
      Map<String, Integer> vertexIds = new HashMap<>();
      for(Map.Entry<String, List<OneToManyToOneAssociation>> entry : result.entrySet()) {
         String name = entry.getKey();
         if(!vertexIds.containsKey(name)){
            vertexIds.put(name, vertexIds.size());
         }
         List<OneToManyToOneAssociation> list = entry.getValue();
         OneToManyToOneAssociation last = list.get(list.size()-1);
         maxMinCount = Math.max(maxMinCount, last.getCount());
      }

      BufferedWriter vertexWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getVertexFilePath())));
      BufferedWriter edgeWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getEdgeFilePath())));

      for(Map.Entry<String, List<OneToManyToOneAssociation>> entry : result.entrySet()) {
         logger.info("company: {}", entry.getKey());
         vertexWriter.write(entry.getKey() + "\t" + vertexIds.get(entry.getKey()) + "\r\n");

         List<OneToManyToOneAssociation> assocs = entry.getValue();
         List<OneToManyToOneAssociation> refined = new ArrayList<>();
         for(int j=0; j < assocs.size(); ++j) {
            OneToManyToOneAssociation csa = assocs.get(j);
            String associated = csa.getEntity2();

            if(j != 0) {
               csa.truncateLinks(maxMinCount);
            }

            if(csa.getCount() == 0) {
               break;
            }

            refined.add(csa);

            if(debugMode) {
               logger.info("\t{} ({})", associated, csa.getCount());
            }

            if(entry.getKey().compareTo(associated) < 0){
               edgeWriter.write(vertexIds.get(entry.getKey()) + "\t" + vertexIds.get(associated) + "\t" + csa.getCount() + "\r\n");
            }
         }

         companyService.saveSimilarCompanies(entry.getKey(), refined);
         try {
            Thread.sleep(10L);
         }
         catch (InterruptedException e) {
            logger.error("sleep interrupted");
         }
      }

      vertexWriter.close();
      edgeWriter.close();

      String fPrefix;
      if(hadoopEnabled) {
         fPrefix = hadoopClient.getHadoopUri();

         hadoopClient.copyFromLocalToHdfs(getVertexFilePath());
         hadoopClient.copyFromLocalToHdfs(getEdgeFilePath());
      } else {
         fPrefix = "file://";
      }

      GraphPageRanker.run(context.sc(), fPrefix + getVertexFilePath(), fPrefix + getEdgeFilePath(), getRankFilePath());

      saveRanking();

      LabelPropagationCommunityFinder.run(context.sc(), fPrefix + getVertexFilePath(), fPrefix + getEdgeFilePath(),getClusterFilePath());

      saveClustering();
   }

   private void saveClustering() throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getClusterFilePath())));
      String line;
      Map<Long, Long> mapper = new HashMap<>();
      while((line = reader.readLine()) != null) {
         String[] parts = line.split("\t");
         String companyName = parts[0];
         long clusterId = StringUtils.parseLong(parts[1]);

         if(mapper.containsKey(clusterId)){
            clusterId = mapper.get(clusterId);
         } else {
            long mappedId = mapper.size()+1;
            mapper.put(clusterId, mappedId);
            clusterId = mappedId;
         }
         companyService.saveClusterId(companyName, clusterId);
      }
      reader.close();

      if(hadoopEnabled) {
         hadoopClient.copyFromLocalToHdfs(getClusterFilePath());
      }

      logger.info("db net-stat information on skill ranking: {}", companyService.countNetStats());

   }

   private void saveRanking() throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getRankFilePath())));
      String line;
      while((line = reader.readLine()) != null) {
         String[] parts = line.split("\t");
         String companyName = parts[0];
         double rank = StringUtils.parseDouble(parts[1]);

         companyService.saveRank(companyName, rank);
      }
      reader.close();

      if(hadoopEnabled) {
         hadoopClient.copyFromLocalToHdfs(getRankFilePath());
      }

      logger.info("db net-stat information on skill ranking: {}", companyService.countNetStats());
   }


   @Override public void cleanUp() {
      if(!debugMode) {
         String[] paths = getFiles();

         for (int i = 0; i < paths.length; ++i) {
            String filePath = paths[i];
            File file = new File(filePath);
            if (file.exists()) {
               file.delete();
            }
         }
      }
   }

   private String[] getFiles(){
      return new String[] {getEdgeFilePath(), getRankFilePath(), getVertexFilePath(), getClusterFilePath()};
   }

   @Override public void setUp() {
      if(hadoopEnabled) {
         String[] paths = getFiles();

         for (int i = 0; i < paths.length; ++i) {
            String filePath = paths[i];

            hadoopClient.delete(filePath);

         }
      }
   }
}
