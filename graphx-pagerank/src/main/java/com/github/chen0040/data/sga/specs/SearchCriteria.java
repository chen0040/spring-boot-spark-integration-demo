package com.github.chen0040.data.sga.specs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 5/2/2017.
 */
@Getter
@Setter
public class SearchCriteria {
   private String key;
   private String operation;
   private Object value;

   public SearchCriteria(String key, String operation, Object value) {
      this.key = key;
      this.operation = operation;
      this.value = value;
   }
}
