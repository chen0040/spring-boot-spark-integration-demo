package com.github.chen0040.data.commons.models;


import java.io.Serializable;


/**
 * Created by xschen on 7/2/2017.
 */
public class CompanyCount implements Serializable {
   private final String company;
   private final long count;

   public CompanyCount(String company, long count) {
      this.company =company;
      this.count =count;
   }


   public String getCompany() {
      return company;
   }


   public long getCount() {
      return count;
   }
}
