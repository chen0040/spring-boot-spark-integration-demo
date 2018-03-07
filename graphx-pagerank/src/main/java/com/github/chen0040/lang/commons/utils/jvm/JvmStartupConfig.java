package com.github.chen0040.lang.commons.utils.jvm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 10/3/16.
 */
public class JvmStartupConfig {

   private static List<String> startup;
   private static long xmx = -1;
   private static long xms = -1;

   private static final Logger logger = LoggerFactory.getLogger(JvmStartupConfig.class);

   public static synchronized List<String> getStartupParameters(){
      if(startup == null) {
         RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
         startup = bean.getInputArguments();
      }
      return startup;
   }



   public static synchronized long getXmx(){
      if(xmx == -1){
         xmx = getXmx(2048);
      }
      return xmx;
   }

   public static synchronized long getXms(){
      if(xms == -1) {
         xms = getXms(2048);
      }
      return xms;
   }

   public static long getXms(long defaultValue){
      return getLongWithPrefex("-Xms", defaultValue);
   }

   public static long getXmx(long defaultValue) {
      return getLongWithPrefex("-Xmx", defaultValue);
   }

   private static long getLongWithPrefex(String prefix, long defaultValue){
      List<String> params = getStartupParameters();
      Optional<String> xmsStringOptional = params.stream().filter(p -> p.startsWith(prefix)).findFirst();
      if(xmsStringOptional.isPresent()) {
         String xmsString = xmsStringOptional.get().replace(prefix, "");
         long xmsValue;
         try{
            xmsValue = Long.parseLong(xmsString);
         } catch (NumberFormatException nfe){
            xmsValue = defaultValue;
            logger.error("number format exception when trying to get xms from " + xmsString, nfe);
         }

         return xmsValue;
      }
      return defaultValue;
   }

}
