package com.github.chen0040.lang.commons.data.sql;


import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by xschen on 29/10/2016.
 */
public class SQLColumn {
   private String name;
   private boolean autoIncrement;
   private String typeName;
   private int typeCode;
   private int type;
   private String tableName;
   private int index;


   public void setName(String name) {
      this.name = name;
   }


   public String getName() {
      return name;
   }


   public void setAutoIncrement(boolean autoIncrement) {
      this.autoIncrement = autoIncrement;
   }


   public boolean isAutoIncrement() {
      return autoIncrement;
   }


   public void setTypeName(String typeName) {
      this.typeName = typeName;
   }


   public String getTypeName() {
      return typeName;
   }


   public void setTypeCode(int typeCode) {
      this.typeCode = typeCode;
   }


   public int getTypeCode() {
      return typeCode;
   }


   public void setType(int type) {
      this.type = type;
   }


   public int getType() {
      return type;
   }


   public void setTableName(String tableName) {
      this.tableName = tableName;
   }


   public String getTableName() {
      return tableName;
   }


   public void setIndex(int index) {
      this.index = index;
   }


   public int getIndex() {
      return index;
   }

   public boolean isInt(){
      return getName().toLowerCase().contains("int");
   }

   public boolean isLong(){
      return getName().toLowerCase().contains("long");
   }


   public double getAsDouble(ResultSet rs) throws SQLException {
      if(isInt()) {
         return rs.getInt(index);
      } else if(isLong()) {
         return rs.getLong(index);
      } else {
         return rs.getDouble(index);
      }
   }


   public String getAsString(ResultSet rs) throws SQLException {
      return rs.getString(index);
   }


   public SQLColumn makeCopy() {
      SQLColumn clone = new SQLColumn();
      clone.name = name;
      clone.autoIncrement = autoIncrement;
      clone.typeName = typeName;
      clone.typeCode = typeCode;
      clone.type = type;
      clone.tableName = tableName;
      clone.index = index;
      return clone;
   }
}
