package com.github.chen0040.data.commons.messages;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 19/12/2016.
 */
@Getter
@Setter
public class SystemEventMessage {
   private long userId;
   private String name;
   private Map<String, String> attributes = new HashMap<>();


   public void setAttribute(String name, String value) {
      attributes.put(name, value);
   }
}
