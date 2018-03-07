package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum JavaTestTechnology {
   EasyMock("EasyMock"),
   PowerMock("PowerMock"),
   Mockito("Mockito"),

   AssertJ("AssertJ"),
   Hamcrest("Hamcrest"),
   JUnit("JUnit"),
   TestNG("TestNG"),
   DBUnit("DBUnit"),

   jMeter("jMeter")
   ;

   private String text;
   JavaTestTechnology(String text){
      this.text = text;
   }

   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }
}
