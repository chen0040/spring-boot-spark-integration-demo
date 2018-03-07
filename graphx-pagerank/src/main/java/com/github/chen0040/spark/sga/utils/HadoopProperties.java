package com.github.chen0040.spark.sga.utils;


import java.io.Serializable;


/**
 * Created by xschen on 12/2/2017.
 */
public class HadoopProperties implements Serializable {
   private String username = "chen0040";
   private String uri;


   public String getUsername() {
      return username;
   }


   public void setUsername(String username) {
      this.username = username;
   }


   public String getUri() {
      return uri;
   }


   public void setUri(String uri) {
      this.uri = uri;
   }
}
