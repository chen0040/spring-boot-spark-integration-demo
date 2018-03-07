package com.github.chen0040.data.commons.models;


/**
 * Created by xschen on 9/1/2017.
 */
public interface JobInsightContract {


   String getSkills();
   void setSkills(String val);

   String getJobTitle();
   void setJobTitle(String val);

   String getCompanyName();
   void setCompanyName(String val);

   String getCountry();
   void setCountry(String country);

   int getYear();
   void setYear(int year);

   int getMonth();
   void setMonth(int month);

   int getDayOfMonth();
   void setDayOfMonth(int val);

   int getWeek();
   void setWeek(int week);

   String getRecordId();
   void setRecordId(String val);

   Long getId();
   void setId(Long id);

   default void copy(JobInsightContract rhs){
      copyContent(rhs);
      setId(rhs.getId());
   }

   default void copyContent(JobInsightContract rhs){
      setCompanyName(rhs.getCompanyName());
      setCountry(rhs.getCountry());
      setDayOfMonth(rhs.getDayOfMonth());
      setJobTitle(rhs.getJobTitle());
      setMonth(rhs.getMonth());
      setRecordId(rhs.getRecordId());
      setSkills(rhs.getSkills());
      setWeek(rhs.getWeek());
      setYear(rhs.getYear());
   }

}
