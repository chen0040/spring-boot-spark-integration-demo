package com.github.chen0040.data.commons.models;


/**
 * Created by xschen on 1/6/17.
 */
public interface SkillAssocContract {

   Long getId();
   void setId(Long id);

   int getYear();
   void setYear(int year);

   int getWeek();
   void setWeek(int week);

   int getMonth();
   void setMonth(int month);

   String getCompanyName();
   void setCompanyName(String companyName);

   String getJobTitle();
   void setJobTitle(String jobTitle);

   String getSkills();
   void setSkills(String skills);

   int getGroupSize();
   void setGroupSize(int groupSize);

   String getCountry();
   void setCountry(String country);

   long getCount();
   void setCount(long count);


}
