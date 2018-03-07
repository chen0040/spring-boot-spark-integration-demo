package com.github.chen0040.data.sga.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Created by xschen on 25/10/2016.
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "skills_assoc_rules")
public class SkillAssocRule extends BasicEntity implements Serializable {

   private static final long serialVersionUID = -7585162074371807581L;

   private String assocRules;
   private LocalDateTime windowStartTime;
   private LocalDateTime windowEndTime;
   private String memo;

   public SkillAssocRule(){

   }
}
