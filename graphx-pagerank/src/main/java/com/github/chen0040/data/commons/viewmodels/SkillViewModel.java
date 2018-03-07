package com.github.chen0040.data.commons.viewmodels;


import com.github.chen0040.data.commons.enums.SkillType;
import com.github.chen0040.data.commons.models.SkillContract;
import com.github.chen0040.lang.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 11/1/2017.
 */
@Getter
@Setter
public class SkillViewModel implements SkillContract {
   private SkillType skillType;
   private String name;
   private String description;
   private Long id;

   public SkillViewModel(){

   }

   public SkillViewModel(SkillContract rhs){
      copy(rhs);
   }

   public boolean isValid(){
      return !StringUtils.isEmpty(name);
   }
}
