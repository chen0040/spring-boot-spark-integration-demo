package com.github.chen0040.spark.sga.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.data.commons.consts.SparkGraphMinerCommand;
import com.github.chen0040.data.commons.messages.SystemEventMessage;
import com.github.chen0040.data.commons.models.MyWorkerCompleted;
import com.github.chen0040.data.commons.utils.MyEventMessageFactory;
import com.github.chen0040.data.sga.services.CompanyService;
import com.github.chen0040.lang.commons.services.GlobalFlagService;
import com.github.chen0040.redis.RedisProvider;
import com.github.chen0040.spark.sga.components.HadoopClient;
import com.github.chen0040.spark.sga.utils.SparkContextFactory;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.*;
import java.util.function.Consumer;


/**
 * Created by xschen on 6/2/2017.
 */
@Service
public class RuleMinerImpl implements RuleMiner {

   @Value("${mine.debug}")
   private boolean debugMode;

   @Value("${spring.application.name}")
   private String driverName;

   @Autowired
   private GlobalFlagService globalFlagService;

   @Autowired
   private RedisProvider redisProvider;

   @Value("${mine.redis.analysisChannel}")
   private String analysisChannel;

   @Value("${mine.redis.simulationProgressChannel}")
   private String redisSimulationProgressChannel;

   @Value("${mine.redis.eventMessageChannel}")
   private String redisEventMessageChannel;

   @Value("${mine.bigdata.hdfs.hadoop-username}")
   private String hadoopUsername;

   @Value("${mine.bigdata.hdfs.enabled}")
   private boolean hadoopEnabled;

   @Autowired
   private HadoopClient hadoopClient;

   @Autowired JobInsightFileService jobInsightFileService;

   @Autowired
   private CompanyService companyService;


   @Autowired
   private CompanyRuleMiner companyRuleMiner;

   @Autowired
   private SkillRuleMiner skillRuleMiner;

   private final String filename = "/tmp/job-insights.csv";

   private static final Logger logger = LoggerFactory.getLogger(RuleMinerImpl.class);

   @Override public void run(Consumer<String> terminationHandler) {

      //SparkContextFactory.setupHadoopHome();

      if (hadoopEnabled) {
         hadoopClient.delete(filename);
      }
      companyRuleMiner.setUp();
      skillRuleMiner.setUp();

      int partitionCount = 100;

      redisProvider.publish(redisEventMessageChannel, JSON.toJSONString(MyEventMessageFactory.createMessage4GraphMiningEvent("Start graph mining"), SerializerFeature.BrowserCompatible));

      logger.info("running {}", driverName);




      try {
         MyWorkerCompleted completed = jobInsightFileService.writeLargeFile(filename, progress -> {
            logger.info("job insight extraction progress: {}", progress.getMessage());
            SystemEventMessage message = MyEventMessageFactory.createMessage4GraphMiningProgress(progress);
            redisProvider.publish(redisEventMessageChannel, JSON.toJSONString(message, SerializerFeature.BrowserCompatible));
         });

         redisProvider.publish(redisEventMessageChannel, JSON.toJSONString(MyEventMessageFactory.createMessage4GraphMiningCompleted(completed), SerializerFeature.BrowserCompatible));

         logger.info("{} file exists = {}", filename, new File(filename).exists());


         if(hadoopEnabled) {
            hadoopClient.copyFromLocalToHdfs(filename);
         }



         JavaSparkContext context = SparkContextFactory.createSparkContext(driverName, hadoopUsername);

         JavaRDD<String> lines;
         if(hadoopEnabled) {
            String hadoopUri = hadoopClient.getHadoopUri();
            logger.info("load file {} from hdfs", filename, hadoopUri);
            lines = context.textFile(hadoopUri + filename, 3 * context.defaultParallelism()).cache();
         } else {
            logger.info("load file {} from local disk", filename);
            lines = context.textFile("file://" + filename).coalesce(partitionCount).cache();
         }

         long count = lines.count();

         logger.info("lines: {}", count);

         JavaPairRDD<String, String> skillCompanyRdd = lines
                 .mapToPair(line -> {
                     String[] parts = line.split("\t");
                     return new Tuple2<>(parts[0] + "\t" + parts[1], 1);
                 })
                 .reduceByKey((a, b) -> a + b)
                 .map(Tuple2::_1)
                 .mapToPair(line -> {
                     String[] parts = line.split("\t");
                     return new Tuple2<>(parts[0],parts[1]);
                 }).coalesce(partitionCount).cache();

         count = skillCompanyRdd.count();

         lines.unpersist();

         logger.info("company-skill pair count: {}", count);
         redisProvider.publish(redisEventMessageChannel, JSON.toJSONString(MyEventMessageFactory.createMessage4GraphMiningCompleted("Successfully cache job insight in spark RDD", count), SerializerFeature.BrowserCompatible));

         companyRuleMiner.run(context, skillCompanyRdd, partitionCount);
         redisProvider.publish(redisEventMessageChannel, JSON.toJSONString(MyEventMessageFactory.createMessage4GraphMiningCompleted("Successfully analyze companies in spark RDD", count), SerializerFeature.BrowserCompatible));

         skillRuleMiner.run(context, skillCompanyRdd, partitionCount);
         redisProvider.publish(redisEventMessageChannel, JSON.toJSONString(MyEventMessageFactory.createMessage4GraphMiningCompleted("Successfully analyze skills in spark RDD", count), SerializerFeature.BrowserCompatible));


      } catch(IOException ioe){
         logger.error("Failed to write data file " + filename, ioe);
      } catch(Exception ex) {
         logger.error("Failed mining task", ex);
      } finally {
         if(!debugMode) {
            File file = new File(filename);
            if (file.exists()) {
               logger.info("delete file {}", filename);
               file.delete();
            }
         }

         skillRuleMiner.cleanUp();
         companyRuleMiner.cleanUp();

         terminationHandler.accept(SparkGraphMinerCommand.COMMAND_GRAPH_MINING);
      }
   }



}
