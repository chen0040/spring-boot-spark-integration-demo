package com.github.chen0040.data.commons.viewmodels;


import com.github.chen0040.data.commons.models.SkillAssocContract;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 1/6/17.
 */
@Getter
@Setter
public class SkillAssocViewModel implements SkillAssocContract {
   private Long id;
   private int year;
   private int week;
   private int month;
   private String companyName;
   private String jobTitle;
   private String skills;
   private int groupSize;
   private String country;
   private long count = 0L;
}
