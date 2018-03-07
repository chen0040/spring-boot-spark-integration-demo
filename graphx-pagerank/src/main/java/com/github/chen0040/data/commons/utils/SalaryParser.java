package com.github.chen0040.data.commons.utils;


import com.github.chen0040.lang.commons.utils.NumberUtils;
import com.github.chen0040.lang.commons.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 5/2/2017.
 */
public class SalaryParser {

   public static List<Double> parseSalary(String summary) {
      List<Double> salaries = parseSalary("Salary", summary);
      if(salaries.isEmpty()){
         return parseSalary("Remuneration", summary);
      } else {
         return salaries;
      }
   }
   public static List<Double> parseSalary(String keyword, String summary) {
      List<Double> salaries = new ArrayList<>();

      int index = summary.indexOf(keyword);
      if(index != -1) {

         int startIndex = index + 5;
         int endIndex = Math.min(summary.length(), startIndex + 80);

         int index2 = summary.indexOf('-', index+5);
         if(index2 != -1 && index2 - index < 100){
            startIndex = Math.max(index+5, index2 - 40);
            endIndex = Math.min(summary.length(), index2 + 40);
         }


         String content = summary.substring(startIndex, endIndex);

         String[] parts = content.replace("-", " ").replace("$", " ").replace("<", " ").replace(">", " ").replace(",", "").replaceAll(" (\\d+)[kK]", " $1000").split(" ");
         for(int i=0; i < parts.length; ++i) {
            String part = parts[i];
            if(NumberUtils.isNumber(part)){

               double value = StringUtils.parseDouble(part);
               if(value > 10) {
                  salaries.add(value);
               }
            }
         }
      }

      if(salaries.size() >= 2) {
         double value1 = salaries.get(0);
         double value2 = salaries.get(1);

         if(value2 < value1) {
            salaries.set(0, value2);
            salaries.set(1, value1);
         }
      }

      return salaries;
   }
}
