package com.github.chen0040.data.commons.viewmodels;


import com.github.chen0040.data.commons.models.CompanyContract;
import com.github.chen0040.lang.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 9/1/2017.
 */
@Getter
@Setter
public class CompanyViewModel implements CompanyContract {

   private String name;


   private String description;

   private String link;

   private String address;

   private String email;

   private Long id;

   public CompanyViewModel(){

   }

   public CompanyViewModel(CompanyContract rhs){
      copy(rhs);
   }

   public boolean isValid(){
      return !StringUtils.isEmpty(name);
   }
}
