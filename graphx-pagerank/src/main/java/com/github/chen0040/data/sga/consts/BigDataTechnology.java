package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum BigDataTechnology {
   Flume("Flume"),
   Impala("Impala"),
   Pig("Pig"),
   Tajo("Tajo"),
   Spark("Spark"),
   Storm("Storm"),
   AVRO("AVRO"),
   Couchdb("Couchdb"),
   Cassandra("Cassandra"),
   Cognos("Cognos"),
   GoogleCharts("Google Charts"),
   Hadoop("Hadoop"),
   HCatalog("HCatalog"),
   Hive("Hive"),
   Highcharts("Highcharts"),
   Hbase("Hbase"),
   JFreeChart("JFree Chart"),
   Kafka("Kafka"),
   MapR("MapR"),
   Mahout("Mahout"),
   Pentaho("Pentaho"),
   Qlikview("Qlikview"),
   SQOOP("SQOOP"),
   Teradata("Teradata"),
   Tableau("Tableau"),
   Zookeeper("Zookeeper"),
   Akka("Akka"),
   Phoenix("Phoenix"),
   ElasticSearch("ElasticSearch")
   ;

   private String text;
   BigDataTechnology(String text){
      this.text = text;
   }

   public String getText(){
      return text;
   }


   @Override public String toString() {
      return text;
   }
}
