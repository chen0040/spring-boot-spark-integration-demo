package com.github.chen0040.data.sga.models;


import com.github.chen0040.data.commons.models.MySalaryContract;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by xschen on 3/2/2017.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "salaries", indexes = {
        @Index(name = "salaryCountryIndex", columnList = "country", unique = false),
        @Index(name = "salaryJobTitleIndex", columnList = "jobTitle", unique = false),
        @Index(name = "salarySkillsIndex", columnList = "skills", unique = false),
        @Index(name = "salaryCompanyNameIndex",columnList = "companyName", unique = false),
        @Index(name = "salaryYearIndex", columnList = "year", unique = false),
        @Index(name = "salaryMonthIndex", columnList = "month", unique = false),
        @Index(name = "salaryWeekIndex", columnList = "week", unique = false),
        @Index(name = "salaryGroupSizeIndex", columnList = "groupSize", unique = false),
        @Index(name = "salaryYearWeekIndex", columnList = "yearWeek", unique = false),
        @Index(name = "salaryLowerBoundIndex", columnList = "salaryLowerBound", unique = false),
        @Index(name = "salaryUpperBoundIndex", columnList = "salaryUpperBound", unique = false)
})
public class MySalaryEntity implements MySalaryContract, Serializable {

   private static final long serialVersionUID = -3416620858938614295L;
   @Id
   private String recordId = "";
   private String country = "";
   private String jobTitle = "";
   private String skills = "";
   private double salaryLowerBound = -1;
   private double salaryUpperBound = -1;
   private String companyName = "";
   private int year = -1;
   private int month = -1;
   private int week = -1;
   private int yearWeek = -1;
   private int groupSize = 1;
   private long count = 1L;

}
