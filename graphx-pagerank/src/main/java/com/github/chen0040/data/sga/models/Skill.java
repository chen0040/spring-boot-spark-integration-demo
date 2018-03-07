package com.github.chen0040.data.sga.models;


import com.github.chen0040.data.commons.enums.SkillType;
import com.github.chen0040.data.commons.models.SkillContract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * Created by xschen on 30/9/2016.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "skills")
public class Skill extends BasicEntity implements Serializable, SkillContract {

   private static final long serialVersionUID = 2005950805773441570L;


   private SkillType skillType;

   private String name;

   @Lob
   @Column(name="description", length = 1208)
   private String description;

   public Skill(){

   }

   public Skill(SkillContract rhs) {
      copy(rhs);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Skill skill = (Skill) o;

      return name.equals(skill.name);
   }

   @Override
   public int hashCode() {
      return name.hashCode();
   }

   @Override
   public String toString(){
      return name;
   }

}
