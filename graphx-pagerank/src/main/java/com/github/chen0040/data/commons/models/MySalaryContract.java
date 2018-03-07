package com.github.chen0040.data.commons.models;


import com.github.chen0040.lang.commons.utils.StringUtils;


/**
 * Created by xschen on 3/2/2017.
 */
public interface MySalaryContract {
   String getJobTitle();
   void setJobTitle(String jobTitle);

   String getCompanyName();
   void setCompanyName(String companyName);

   String getSkills();
   void setSkills(String skills);

   String getRecordId();
   void setRecordId(String jobId);

   String getCountry();
   void setCountry(String country);

   double getSalaryLowerBound();
   void setSalaryLowerBound(double salary);

   double getSalaryUpperBound();
   void setSalaryUpperBound(double salary);

   int getYear();
   void setYear(int year);

   int getMonth();
   void setMonth(int month);

   int getWeek();
   void setWeek(int week);

   int getGroupSize();
   void setGroupSize(int groupSize);

   long getCount();
   void setCount(long count);


   int getYearWeek();
   void setYearWeek(int yearWeek);

   default MySalaryContract makeInvalid() {
      setRecordId("");
      return this;
   }

   default boolean isValid() {
      return !StringUtils.isEmpty(getRecordId());
   }

}
