package com.github.chen0040.spark.sga.components;


import com.github.chen0040.data.commons.consts.SparkGraphMinerFilePath;
import com.github.chen0040.spark.sga.utils.HadoopFileUtils;
import com.github.chen0040.spark.sga.utils.HadoopProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by xschen on 11/2/2017.
 */
@Component
public class HadoopClient {

   @Value("${mine.bigdata.hdfs.uri}")
   private String hadoopUri;

   @Value("${mine.bigdata.hdfs.hadoop-username}")
   private String hadoopUsername;

   private static final Logger logger = LoggerFactory.getLogger(HadoopClient.class);

   public String getHadoopUri() {
      return hadoopUri;
   }



   public void copyFromLocalToHdfs(String path) {
      logger.info("Copy file {} into hdfs {}", path, hadoopUri);
      HadoopProperties properties = new HadoopProperties();
      properties.setUri(hadoopUri);
      properties.setUsername(hadoopUsername);

      String hdfsPath = SparkGraphMinerFilePath.getHdfsPath(path);
      HadoopFileUtils.copyFromLocalToHdfs(properties, path, hdfsPath);
      logger.info("file in hdfs? {}", HadoopFileUtils.pathExistsOnHdfs(properties, hdfsPath));
   }


   public void delete(String filePath) {
      logger.info("delete file {} from hdfs {}", filePath, hadoopUri);

      HadoopProperties properties = new HadoopProperties();
      properties.setUri(hadoopUri);
      properties.setUsername(hadoopUsername);

      String hdfsPath = SparkGraphMinerFilePath.getHdfsPath(filePath);
      HadoopFileUtils.deleteHdfsFile(properties, hdfsPath);
   }
}
