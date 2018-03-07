package com.github.chen0040.data.commons.models;


/**
 * Created by xschen on 9/1/2017.
 */
public interface CompanyContract {

   String getName();
   void setName(String val);

   String getDescription();
   void setDescription(String val);

   String getLink();
   void setLink(String val);

   String getAddress();
   void setAddress(String val);

   String getEmail();
   void setEmail(String val);

   Long getId();
   void setId(Long val);

   default void copy(CompanyContract rhs) {
      copyContent(rhs);
      setId(rhs.getId());
   }

   default void copyContent(CompanyContract rhs) {
      setName(rhs.getName());
      setDescription(rhs.getDescription());
      setLink(rhs.getLink());
      setAddress(rhs.getAddress());
      setEmail(rhs.getEmail());
   }
}
