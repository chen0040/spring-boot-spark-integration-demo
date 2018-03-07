package com.github.chen0040.data.commons.models;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Created by xschen on 16/10/2016.
 */
@Getter
@Setter
public class WikiExtractQueryNormalization implements Serializable {
   private static final long serialVersionUID = -7044146553463890775L;

   private String from;
   private String to;
}
