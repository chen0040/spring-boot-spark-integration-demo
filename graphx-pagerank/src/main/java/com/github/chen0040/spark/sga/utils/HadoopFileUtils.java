package com.github.chen0040.spark.sga.utils;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 29/6/2016.
 */
public class HadoopFileUtils {

   private static Logger logger = LoggerFactory.getLogger(HadoopFileUtils.class);

   private Map<String, InputStream> resources =new HashMap<>();


   private static void logError(String error) {
      logger.error(error);
   }


   private static void logError(String error, Exception exception) {
      logger.error(error, exception);
   }


   private static void logInfo(String info) {
      logger.info(info);
   }


   private static Configuration buildConfig() {
      Configuration conf = new Configuration();


      conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
      conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
      return conf;
   }


   private static FileSystem getFileSystem(HadoopProperties props, Configuration conf) throws IOException, InterruptedException {
      FileSystem fs;
      String hdfsUri = props.getUri();
      String hadoopUsername = props.getUsername();
      if (hdfsUri == null) {
         fs = FileSystem.get(conf);
      }
      else {
         fs = FileSystem.get(URI.create(hdfsUri), conf, hadoopUsername);
      }

      return fs;
   }








   public static boolean pathExistsOnHdfs(HadoopProperties props, String path) {

      Configuration conf = buildConfig();

      boolean found = false;
      try {
         FileSystem fs = getFileSystem(props, conf);
         Path fspath = new Path(path);
         found = fs.exists(fspath);
      }
      catch (IOException e) {
         logError("Failed to perform hdfs operation", e);
      }
      catch (InterruptedException e) {
         logError("Interrupted", e);
      }

      return found;
   }


   public static String copyFromHdfsToLocal(HadoopProperties properties, String path, String localDirPath) {
      String hdfsUriFullPath;

      String localFileName = path.substring(path.lastIndexOf('/') + 1);
      String lpath = localDirPath + "/" + localFileName;

      File ssf = new File(localDirPath);
      if (!ssf.exists()) {
         boolean created = ssf.mkdir();
         if (created) {
            logger.info("Directory {} created!", ssf.getAbsolutePath());
         }
         else {
            logger.warn("Failed to create directory {}", ssf.getAbsolutePath());
         }
      }


      Configuration conf = buildConfig();

      boolean found = false;
      try {
         FileSystem fs = getFileSystem(properties, conf);
         Path fspath = new Path(path);
         found = fs.exists(fspath);
         if (!found) {
            logError("Hdfs path " + path + " does not exist on hdfs " + properties.getUri());
         }
         else {
            FileStatus[] status = fs.listStatus(fspath);
            for (int i = 0; i < status.length; i++) {
               String ppath = status[i].getPath().toString();

               String ppath_partname = ppath.substring(ppath.lastIndexOf('/') + 1);
               if (ppath_partname.startsWith("part-")) {
                  logInfo("Copying from: " + ppath);
                  logInfo("Copying to: " + lpath);
                  fs.copyToLocalFile(true, status[i].getPath(), new Path(lpath));
               }
               else {
                  logInfo("Skipping: " + ppath);
               }
            }
         }
      }
      catch (IOException e) {
         logError("Failed to perform hdfs operation", e);
      }
      catch (InterruptedException e) {
         logError("Interrupted",e);
      }

      if (!found) {
         return null;
      }

      return lpath;

   }


   public static String copyFromLocalToHdfs(HadoopProperties properties, String localFilePath, String hdfsPath) {


      Configuration conf = buildConfig();

      boolean found = false;
      try {
         FileSystem fs = getFileSystem(properties, conf);

         boolean deleteSrc = false;
         boolean overwrite = true;
         fs.copyFromLocalFile(deleteSrc, overwrite, new Path(localFilePath), new Path(hdfsPath));
      }
      catch (IOException e) {
         logError("Failed to perform hdfs operation", e);
      }
      catch (InterruptedException e) {
         logError("Interrupted",e);
      }

      if (!found) {
         return null;
      }

      if(properties.getUri() == null){
         return hdfsPath;
      }

      return properties.getUri() + hdfsPath;

   }

   public static String copyFromLocalToHdfs(Configuration conf, String localFilePath, String path) {

      boolean found = false;
      try {
         FileSystem fs = getFileSystem(null, conf);

         boolean deleteSrc = false;
         boolean overwrite = true;
         fs.copyFromLocalFile(deleteSrc, overwrite, new Path(localFilePath), new Path(path));
      }
      catch (IOException e) {
         logError("Failed to perform hdfs operation", e);
      }
      catch (InterruptedException e) {
         logError("Interrupted", e);
      }

      if (!found) {
         return null;
      }

      return path;

   }

   public static String moveFromLocalToHdfs(HadoopProperties properties, String localFilePath, String path) {

      Configuration conf = buildConfig();

      boolean found = false;
      try {
         FileSystem fs = getFileSystem(properties, conf);

         boolean deleteSrc = true;
         boolean overwrite = true;
         fs.copyFromLocalFile(deleteSrc, overwrite, new Path(localFilePath), new Path(path));
      }
      catch (IOException e) {
         logError("Failed to perform hdfs operation", e);
      }
      catch (InterruptedException e) {
         logError("Interrupted", e);
      }

      if (!found) {
         return null;
      }

      if(properties.getUri() == null){
         return path;
      }

      return properties.getUri() + path;
   }


   public static String moveFromHdfsToLocal(HadoopProperties properties, String path, String localDirPath) {
      String hdfsUriFullPath = "";

      String localFileName = path.substring(path.lastIndexOf('/') + 1);
      String lpath = localDirPath + "/" + localFileName;

      File ssf = new File(localDirPath);
      if (!ssf.exists()) {
         boolean created = ssf.mkdir();
         if (created) {
            logger.info("Directory {} created!", ssf.getAbsolutePath());
         }
         else {
            logger.warn("Failed to create directory {}", ssf.getAbsolutePath());
         }
      }

      Configuration conf = buildConfig();

      boolean found = false;
      try {
         FileSystem fs = getFileSystem(properties, conf);
         Path fspath = new Path(path);
         found = fs.exists(fspath);
         if (!found) {
            logError("Hdfs path not exists: " + hdfsUriFullPath);
         }
         else {
            FileStatus[] status = fs.listStatus(fspath);
            for (int i = 0; i < status.length; i++) {
               String ppath = status[i].getPath().toString();

               String ppath_partname = ppath.substring(ppath.lastIndexOf('/') + 1);
               if (ppath_partname.startsWith("part-")) {
                  logInfo("Moving from: " + ppath);
                  logInfo("Moving to: " + lpath);
                  fs.copyToLocalFile(true, status[i].getPath(), new Path(lpath));
               }
               else {
                  logInfo("Skipping: " + ppath);
               }
            }
            fs.delete(fspath, true);
         }
      }
      catch (IOException e) {
         logError("Failed to perform hdfs operation", e);
      }
      catch (InterruptedException e) {
         logError("Interrupted", e);
      }

      if (!found) {
         return null;
      }

      return lpath;

   }


   public static void setHadoopUsername(String username) {
      System.setProperty("HADOOP_USER_NAME", username);
   }

   public static boolean deleteHdfsFile(HadoopProperties properties, String path) {


      Configuration conf = buildConfig();

      boolean found = false;
      try {
         FileSystem fs = getFileSystem(properties, conf);
         Path fspath = new Path(path);
         found = fs.exists(fspath);

         if (!found) {
            logInfo("Deletion is not carried out as file " + path + " does not exist in hdfs " + properties.getUri());
            return false;
         }
         else {
            logInfo("Deleting file " + path + " in hdfs " + properties.getUri());
            fs.delete(fspath, true);
         }
      }
      catch (IOException e) {
         logError("Failed to perform hdfs operation", e);
      }
      catch (InterruptedException e) {
         logError("Interrupted", e);
      }

      return found;
   }
}

