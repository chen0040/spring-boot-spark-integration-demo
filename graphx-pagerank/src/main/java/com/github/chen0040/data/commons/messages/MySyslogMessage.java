package com.github.chen0040.data.commons.messages;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by xschen on 31/12/2016.
 */
@Getter
@Setter
public class MySyslogMessage implements Serializable {
   private static final long serialVersionUID = -8925990229120234613L;

   private int facility;
   private Date date;
   private int level;
   private String host;
   private String message;
   private String hostName;
   private String logLevel;
   private String process;
   private String messageId;

}
