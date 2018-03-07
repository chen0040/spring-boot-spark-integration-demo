package com.github.chen0040.spark.sga.utils;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Created by xschen on 11/1/2017.
 */
public class SparkContextFactory {

   private static InputStream getResource(String filename) throws IOException {
      ClassLoader classLoader = SparkContextFactory.class.getClassLoader();
      URL dataFile = classLoader.getResource(filename);
      return dataFile.openStream();
   }

   private static final Logger logger = LoggerFactory.getLogger(SparkContextFactory.class);

   public static void setupHadoopHome(){
      boolean windowsOS = System.getProperty("os.name").toLowerCase().contains("windows");
      if (windowsOS) {

         // for test only on windows OS
         String hadoop_dir = System.getProperty("hadoop.home.dir");
         if (hadoop_dir == null) {
            File tmpDir = new File("C:/tmp");
            if(!tmpDir.exists()){
               tmpDir.mkdir();
               tmpDir.setWritable(true);
            }
            File binDir = new File("C:/tmp/bin");
            if(!binDir.exists()) {
               binDir.mkdir();
               binDir.setWritable(true);

               String zipFileName = "C:/tmp/bin/win-bin.zip";
               try {

                  InputStream inStream = getResource("win-bin.zip");

                  FileOutputStream outStream = new FileOutputStream(new File(zipFileName));

                  byte[] buffer = new byte[1024];

                  int length;
                  while ((length = inStream.read(buffer)) > 0){
                     outStream.write(buffer, 0, length);
                  }

                  inStream.close();
                  outStream.close();

                  ZipFile zipFile = new ZipFile(zipFileName);
                  zipFile.extractAll("C:/tmp/bin");
               }
               catch (IOException e) {
                  logger.error("Failed to copy the dict.zip from resources to C:/tmp/bin", e);
               }
               catch (ZipException e) {
                  logger.error("Failed to unzip " + zipFileName, e);
               }
            }
            System.setProperty("hadoop.home.dir", "C:/tmp");
         }
      }
   }

   public static JavaSparkContext createSparkContext(String driverName, String hadoopUsername) {
      setupHadoopHome();

      SparkConf sparkConf = new SparkConf();

      sparkConf.set("spark.scheduler.mode", "FAIR")
              .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
              .set("spark.cleaner.ttl", "86400")
              .set("spark.app.id", driverName)
              .set("spark.app.name", driverName)

              // configure ports that might otherwise be random
              // .set("spark.driver.port", "51810")
              // .set("spark.fileserver.port", "51811")
              // .set("spark.broadcast.port", "51812")
              // .set("spark.replClassServer.port", "51813")
              // .set("spark.blockManager.port", "51814")
              // .set("spark.executor.port", "51815")

              // configure master default for running locally
              .setIfMissing("spark.master", "local[2]");

      sparkConf.setExecutorEnv("HADOOP_USER_NAME", hadoopUsername);
      HadoopFileUtils.setHadoopUsername(hadoopUsername);


      JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
      if (sparkContext.isLocal()) {
         logger.info("-------------spark in local mode-----------");
      }
      return sparkContext;
   }
}
