package com.github.chen0040.data.commons.viewmodels;


import com.github.chen0040.data.commons.enums.JobStatus;
import com.github.chen0040.data.commons.models.IndexerQuery;
import com.github.chen0040.data.commons.models.JobContract;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * Created by xschen on 30/9/2016.
 */
@Getter
@Setter
public class JobViewModel implements JobContract {

   private static final long serialVersionUID = -6480806618529844016L;

   private String snippet;

   private String url;
   private double latitude;
   private double longitude;
   private String recordId;
   private String formattedLocationFull;
   private String formattedRelativeTime;
   private JobStatus status = JobStatus.Opened;
   private String producer;
   private String companyName;
   private String jobTitle;

   private String city;
   private String state;
   private String country;
   private String formattedLocation;
   private String source;
   private LocalDateTime date;

   private boolean sponsored;
   private boolean expired;

   private long id;

   public JobViewModel() {

   }

   public JobViewModel(JobContract job, long id){
      copy(job);
      this.id = id;
   }


   @Override public boolean getSponsored() {
      return sponsored;
   }


   @Override public boolean getExpired() {
      return expired;
   }


   @Override public IndexerQuery indexerQuery() {
      return null;
   }


   @Override public JobContract indexerQuery(IndexerQuery indexerQuery) {
      return this;
   }



}
