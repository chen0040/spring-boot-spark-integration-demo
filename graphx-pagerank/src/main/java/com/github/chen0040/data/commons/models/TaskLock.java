package com.github.chen0040.data.commons.models;


import lombok.Getter;

import java.io.Serializable;


/**
 * Created by xschen on 23/12/2016.
 */
@Getter
public class TaskLock implements Serializable {
   private static final long serialVersionUID = 8693135656843561333L;
   private String ipAddress;
   private boolean acquired;
   private String name;

   public TaskLock(String name, String ipAddress, boolean acquired) {
      this.ipAddress = ipAddress;
      this.acquired = acquired;
      this.name = name;
   }

   public TaskLock() {

   }
}
