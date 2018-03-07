package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum PythonTechnology {
   django("django"),
   Flask("Flask")
   ;

   private String text;
   PythonTechnology(String text){
      this.text = text;
   }
   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }

}
