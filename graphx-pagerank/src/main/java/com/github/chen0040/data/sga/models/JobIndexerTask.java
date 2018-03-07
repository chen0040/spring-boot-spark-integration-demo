package com.github.chen0040.data.sga.models;


import com.github.chen0040.data.commons.enums.TaskState;
import com.github.chen0040.lang.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by xschen on 12/27/16.
 */
@Entity
@Getter
@Setter
@Table(name = "job_indexer_tasks", indexes = {
        @Index(name = "userIdIndex", columnList = "userId", unique = false),
        @Index(name = "taskStateIndex", columnList = "taskState", unique = false),
        @Index(name = "lockIndex", columnList = "locked", unique = false),
        @Index(name = "keywordsIndex", columnList = "keywords", unique = false),
        @Index(name = "postedDateIndex", columnList = "postedDate", unique = false),
        @Index(name = "createdTimeIndex", columnList = "createdTime", unique = false),
        @Index(name = "countryIndex", columnList = "country", unique = false),
        @Index(name = "endTimeIndex", columnList = "endTime", unique = false)
})
public class JobIndexerTask implements Serializable {
   private static final long serialVersionUID = -3228465148350319494L;
   @Id @GeneratedValue protected Long id;


   private String taskUUID = "";

   private boolean locked = false;

   private long userId = 0L;

   private long createdTime = 0L;

   private long startTime = 0L;

   private long endTime = 0L;

   private String keywords = "";

   private String postedDate = "";

   private String country = "";

   private TaskState taskState = TaskState.Pending;


   public JobIndexerTask makeInvalid() {
      keywords = "";
      return this;
   }

   public boolean isValid() {
      return !StringUtils.isEmpty(keywords);
   }
}
