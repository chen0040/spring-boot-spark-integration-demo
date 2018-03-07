package com.github.chen0040.data.commons.utils;


import com.github.chen0040.lang.commons.utils.StringUtils;


/**
 * Created by xschen on 4/2/2017.
 */
public class RecordIdUtil {


   public static String createRecordId(int year, int month, String country, String skills, String companyName) {

      String recordId2 = "";

      if(year != -1) {
         recordId2 = recordId2 + year;
      }

      if(month != -1) {
         recordId2 = recordId2 + "-" + month;
      }

      if(!StringUtils.isEmpty(skills)) {
         recordId2 = skills + "_" + recordId2;
      }

      if(!StringUtils.isEmpty(companyName)) {
         recordId2 = companyName + "+" + recordId2;
      }

      if(!StringUtils.isEmpty(country)){
         recordId2 = recordId2 + "@" + country;
      }

      if(StringUtils.isEmpty(recordId2)){
         recordId2 = "error-bin";
      }

      return recordId2;
   }
}
