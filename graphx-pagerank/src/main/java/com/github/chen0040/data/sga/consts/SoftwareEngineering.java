package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum SoftwareEngineering {
   DesignPattern("Design Pattern"),
   Rally("Rally"),
   CMMI("CMMI"),
   MVVM("MVVM"),
   MVP("MVP"),
   MVC("MVC"),
   UML("UML")
   ;

   private String text;
   SoftwareEngineering(String text){
      this.text = text;
   }

   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }
}
