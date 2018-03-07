package com.github.chen0040.data.sga.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Created by xschen on 2/10/2016.
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BasicEntity implements Serializable {
   private static final long serialVersionUID = -3780783234550410754L;

   @Id @GeneratedValue protected Long id;

   @CreatedDate
   protected LocalDateTime createTime;
   @LastModifiedDate protected LocalDateTime updateTime;
}
