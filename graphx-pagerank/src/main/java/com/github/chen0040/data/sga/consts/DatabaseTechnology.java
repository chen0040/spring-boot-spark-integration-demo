package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum DatabaseTechnology {
   Presto("Presto"),
   Couchbase("Couchbase"),
   DB2("DB2"),
   DocumentDB("DocumentDB"),
   Hsqldb("Hsqldb"),
   IMSDB("IMS DB"),
   Memcached("Memcached"),
   MariaDB("MaraDB"),
   MongoDB("MongoDB"),
   MySQL("MySQL"),
   Neo4j("Neo4j"),
   OBIEE("OBIEE"),
   Orientdb("Orient db"),
   PouchDB("PouchDB"),
   PostgreSQL("Postgre"),
   Redis("Redis"),
   SQLite("SQLite"),
   MSSQLServver("MS SQL Server")

   ;
   private String text;
   DatabaseTechnology(String text){
      this.text = text;
   }

   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }
}
