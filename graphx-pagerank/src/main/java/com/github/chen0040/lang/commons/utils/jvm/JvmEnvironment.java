package com.github.chen0040.lang.commons.utils.jvm;


import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Created by xschen on 6/10/2016.
 */
public class JvmEnvironment {
   public static boolean isOSWindow(){
      return getOSName().toLowerCase().contains("window");
   }

   public static String getOSName(){
      return System.getProperty("os.name");
   }

   public static String getRelativeFilePath(String dataPath) throws FileNotFoundException {

      String dataPath2 = dataPath;

      if(Files.exists(Paths.get(dataPath))) {
         return dataPath;
      } else {
         for(int i=0; i < 10; ++i) {
            dataPath2 = "../" + dataPath2;
            if (Files.exists(Paths.get(dataPath2))) {
               return dataPath2;
            }
         }
         throw new FileNotFoundException("Failed to find " + dataPath);
      }

   }
}
