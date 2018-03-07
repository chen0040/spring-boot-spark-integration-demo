package com.github.chen0040.data.sga.models;


import com.github.chen0040.data.commons.enums.JobStatus;
import com.github.chen0040.data.commons.models.IndexerQuery;
import com.github.chen0040.data.commons.models.JobContract;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.ToString;


/**
 * Created by xschen on 27/9/2016.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "jobs", indexes = {
        @Index(name = "jobRecordIdIndex", columnList = "recordId", unique = true),
        @Index(name = "jobCountryIndex", columnList = "country", unique = false),
        @Index(name = "jobTitleIndex", columnList = "jobTitle", unique = false),
        @Index(name = "jobProducerIndex", columnList = "producer", unique = false)
})
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class MyJob extends BasicEntity implements Serializable, JobContract {

   public static final int MAX_SNIPPET_SIZE = 6000;

   private static final long serialVersionUID = 1831172762994994600L;

   private String jobTitle;

   @ManyToOne
   @JoinColumn(name = "company")
   private Company company;


   private String city;
   private String state;
   private String country;
   private String formattedLocation;
   private String source;

   private LocalDateTime date;

   @Lob
   @Column(name="description", length = MAX_SNIPPET_SIZE)
   private String snippet;

   private String url;
   private double latitude;
   private double longitude;
   private String recordId;
   private boolean sponsored;
   private boolean expired;
   private String formattedLocationFull;
   private String formattedRelativeTime;
   private JobStatus status = JobStatus.Opened;
   private String producer;
   private String companyName;

   @OneToMany(mappedBy="job", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
   private List<JobSkill> skills = new ArrayList<>();

   public MyJob(){
      recordId = UUID.randomUUID().toString();
   }

   public MyJob(JobContract jobContract){
      copy(jobContract);
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



   public MyJob makeInvalid() {
      recordId = "";
      return this;
   }
}
