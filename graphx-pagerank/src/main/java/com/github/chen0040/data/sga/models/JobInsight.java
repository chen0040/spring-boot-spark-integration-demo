package com.github.chen0040.data.sga.models;


import com.github.chen0040.data.commons.models.JobInsightContract;
import com.github.chen0040.lang.commons.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;


/**
 * Created by xschen on 16/10/2016.
 */
@Entity
@Getter
@Setter
@Table(name = "job_insight", indexes = {
        @Index(name = "companyIndex", columnList = "companyName", unique = false),
        @Index(name = "jobTitleIndex", columnList = "jobTitle", unique = false),
        @Index(name = "jobYearIndex", columnList = "year", unique = false),
        @Index(name = "jobMonthIndex", columnList = "month", unique = false),
        @Index(name = "jobDayOfMonthIndex", columnList = "dayOfMonth", unique = false),
        @Index(name = "jobWeekIndex", columnList = "week", unique = false),
        @Index(name = "jobCountryIndex", columnList = "country", unique = false),
        @Index(name = "jobRecordIdIndex", columnList = "recordId", unique = true)
})
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class JobInsight extends BasicEntity implements Serializable, JobInsightContract {

   private static final long serialVersionUID = -1253431301427202774L;
   private String skills = "";
   private String jobTitle = "";
   private String companyName = "";

   private String country = "";

   private int year;
   private int month;
   private int dayOfMonth;
   private int week;

   private String recordId;


   public void buildDate(LocalDateTime date) {
      dayOfMonth = date.getDayOfMonth();
      year = date.getYear();
      month = date.getMonthValue();
      WeekFields weekFields = WeekFields.of(Locale.getDefault());
      week = date.get(weekFields.weekOfWeekBasedYear());
   }


   public JobInsight makeInvalid() {
      recordId = "";
      return this;
   }

   public boolean isValid() {
      return !StringUtils.isEmpty(recordId);
   }
}
