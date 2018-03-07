package com.github.chen0040.spark.sga.components;


import com.github.chen0040.data.commons.messages.SystemEventMessage;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 11/1/2017.
 */
@Getter
@Setter
public class GlobalObjects {
   private SystemEventMessage message;

   private static GlobalObjects instance;

   public static synchronized GlobalObjects getInstance(){
      if(instance==null){
         instance = new GlobalObjects();
      }
      return instance;
   }
}
