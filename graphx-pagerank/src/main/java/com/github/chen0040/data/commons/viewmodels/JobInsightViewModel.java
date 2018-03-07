package com.github.chen0040.data.commons.viewmodels;


import com.github.chen0040.data.commons.models.JobInsightContract;
import com.github.chen0040.lang.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 9/1/2017.
 */
@Getter
@Setter
public class JobInsightViewModel implements JobInsightContract {
   private String skills = "";
   private String jobTitle = "";
   private String companyName = "";

   private String country = "";

   private int year;
   private int month;
   private int dayOfMonth;
   private int week;

   private String recordId;

   private Long id;

   public JobInsightViewModel(){
      id = -1L;
      recordId = "";
   }

   public JobInsightViewModel(JobInsightContract rhs){
      copy(rhs);
   }

   public boolean isValid() {
      return !StringUtils.isEmpty(recordId);
   }
}
