package com.github.chen0040.data.commons.models;


import com.github.chen0040.data.commons.enums.SkillType;


/**
 * Created by xschen on 11/1/2017.
 */
public interface SkillContract {

   SkillType getSkillType();
   void setSkillType(SkillType skillType);

   String getName();
   void setName(String name);


   String getDescription();
   void setDescription(String description);

   Long getId();
   void setId(Long id);

   default void copy(SkillContract rhs) {
      copyContent(rhs);
      setId(rhs.getId());
   }

   default void copyContent(SkillContract rhs) {
      setSkillType(rhs.getSkillType());
      setName(rhs.getName());
      setDescription(rhs.getDescription());
   }
}
