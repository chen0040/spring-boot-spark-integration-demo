package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum JavaTechnology {


   POI("POI"),
   Jasper("Jasper"),
   DynamicReport("Dynamic Report"),
   TIKA("TIKA"),

   AWT("AWT"),
   Swing("Swing"),

   Jackson("Jackson"),
   JsonSimple("json-simple"),
   FastJSON("FastJSON"),
   GSON("GSON"),
   JSON("JSON"),
   XML("XML"),

   jBPM5("jBPM5"),
   Hibernate("Hibernate"),
   JDBC("JDBC"),
   JPA("JPA"),
   Struts("Struts"),

   log4j("log4j"),
   logback("logback"),
   slf4j("slf4j"),

   Servlet("Servlet"),
   JSP("JSP"),
   EJB("EJB"),

   Lucene("Lucene"),
   Drools("Drools"),
   jboss("jboss"),

   Spring("Spring"),
   SpringBoot("Spring Boot"),
   SpringCloud("Spring Cloud"),
   SpringIntegration("Spring Integration"),

   Vaadin("Vaadin"),
   Play("Play Framework"),
   SparkJava("SparkJava"),
   GWT("GWT")
   ;

   private String text;
   JavaTechnology(String text){
      this.text = text;
   }

   @Override public String toString() {
      return text;
   }
}
