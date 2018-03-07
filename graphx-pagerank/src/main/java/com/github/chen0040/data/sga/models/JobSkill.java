package com.github.chen0040.data.sga.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by xschen on 30/9/2016.
 */
@Entity
@Getter
@AllArgsConstructor
@Table(name = "job_skills")
public class JobSkill extends BasicEntity implements Serializable {
   private static final long serialVersionUID = -6732410490437662744L;


   @ManyToOne
   @JoinColumn(name = "job")
   private MyJob job;

   @OneToOne
   @JoinColumn(name = "skill")
   private Skill skill;

   private String jskey;

   public JobSkill(){

   }

}
