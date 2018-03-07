package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum  SoftwareQualityTool {
   FindBugs("FindBugs"),
   PMD("PMD"),
   JaCoCo("JaCoCo")
   ;

   private String text;
   SoftwareQualityTool(String text){
      this.text = text;
   }

   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }
}
