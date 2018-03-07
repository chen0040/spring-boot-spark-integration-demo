package com.github.chen0040.data.commons.messages;


import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 30/1/2017.
 */
@Getter
@Setter
public class MyWebSocketMessage {
   private String body;
   private String topic;
   private long userId;
   private boolean valid;
}
