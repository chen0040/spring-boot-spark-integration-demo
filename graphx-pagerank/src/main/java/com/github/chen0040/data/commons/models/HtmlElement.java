package com.github.chen0040.data.commons.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Created by xschen on 22/10/2016.
 */
@Getter
@Setter
public class HtmlElement implements Serializable {
   private static final long serialVersionUID = 2609811589354933828L;
   private String text;
   private String html;


   public HtmlElement(String html, String text) {
      this.html = html;
      this.text = text;
   }

   public HtmlElement(){

   }
}
