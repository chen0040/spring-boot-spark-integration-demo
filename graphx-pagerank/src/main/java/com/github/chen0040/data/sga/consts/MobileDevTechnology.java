package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum MobileDevTechnology {
   Android("Android"),
   iOS("iOS"),
   ionic("ionic"),
   PhoneGap("PhoneGap"),
   ReactNative("ReactNative"),
   SL4A("SL4A")
   ;

   private String text;
   MobileDevTechnology(String text){
      this.text = text;
   }
   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }
}
