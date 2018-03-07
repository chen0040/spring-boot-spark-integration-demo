package com.github.chen0040.data.sga.models;


import com.github.chen0040.data.commons.models.SkillAssocContract;
import com.github.chen0040.lang.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by xschen on 1/1/2017.
 */
@Entity
@Getter
@Setter
@Table(name = "skill_assoc", indexes = {
        @Index(name = "skillSkillsIndex", columnList = "skills", unique = false),
        @Index(name = "skillCompanyNameIndex", columnList = "companyName", unique = false),
        @Index(name = "skillJobTitleIndex", columnList = "jobTitle", unique = false),
        @Index(name = "skillYearIndex", columnList = "year", unique = false),
        @Index(name = "skillMonthIndex", columnList = "month", unique = false),
        @Index(name = "skillWeekIndex", columnList = "week", unique = false),
        @Index(name = "skillGroupSizeIndex", columnList = "groupSize", unique = false),
        @Index(name = "skillCountryIndex", columnList = "country", unique = false),
        @Index(name = "skillCountIndex", columnList = "count", unique = false)
})
public class SkillAssocEntity implements SkillAssocContract, Serializable {

   private static final long serialVersionUID = -4884567098344994193L;
   @Id @GeneratedValue protected Long id;

   private int year;
   private int week;
   private int month;
   private String companyName;
   private String jobTitle;
   private String skills;
   private int groupSize;
   private String country;
   private long count = 0L;

   public boolean isValid() {
      return !StringUtils.isEmpty(skills);
   }
}
