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
public class MyWorkerProgress implements Serializable {
   private static final long serialVersionUID = 2147773411692465563L;
   private int percentage = 0;
   private int remainingTime = 0;
   private String error = "";
   private int errorRows = 0;
   private String message = "";

   public MyWorkerProgress(String message){
      this.message = message;
   }

   public MyWorkerProgress(int percentage, int remainingTime, String message){
      this.percentage = percentage;
      this.remainingTime = remainingTime;
      this.message = message;
   }

   public MyWorkerProgress(int percentage, int remainingTime, String error, int errorRows, String message) {
      this.percentage = percentage;
      this.remainingTime = remainingTime;
      this.error = error;
      this.errorRows = errorRows;
      this.message = message;
   }

   public MyWorkerProgress(){
      
   }


   public boolean isSuccessful() {
      return StringUtils.isEmpty(error);
   }
}
