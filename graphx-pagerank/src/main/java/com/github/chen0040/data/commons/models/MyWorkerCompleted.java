package com.github.chen0040.data.commons.models;


import com.github.chen0040.lang.commons.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Created by xschen on 20/1/2017.
 */
@Getter
@Setter
public class MyWorkerCompleted implements Serializable {
   private static final long serialVersionUID = 901625308176478988L;
   private String error = "";
   private int errorRows = 0;
   private int changedRows = -1;
   private String message = "";

   public boolean isSuccessful() {
      return StringUtils.isEmpty(error);
   }

   public MyWorkerCompleted() {

   }

   public MyWorkerCompleted(String error, int errorRows, int changedRows) {
      if(error != null) {
         this.error = error.trim();
      }
      this.errorRows = errorRows;
      this.changedRows = changedRows;
   }
}
