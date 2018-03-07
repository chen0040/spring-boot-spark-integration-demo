package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum JavaScriptTestTechnology {
   Karma("Karma"),
   Jasmine("Jasmine")
   ;

   private String text;
   public String getText(){
      return text;
   }
   JavaScriptTestTechnology(String text){
      this.text = text;
   }

   @Override public String toString() {
      return text;
   }
}
