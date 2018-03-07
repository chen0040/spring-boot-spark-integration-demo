package com.github.chen0040.data.commons.models;


import com.github.chen0040.data.commons.enums.JobSite;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Created by xschen on 23/10/2016.
 */
@Getter
@Setter
public class IndexerQuery implements Serializable {
   private static final long serialVersionUID = -4882328647918309741L;
   private String keywords;
   private String country;
   private int fromage;
   private int startIndex;
   private int pageSize;
   private JobSite source;
}
