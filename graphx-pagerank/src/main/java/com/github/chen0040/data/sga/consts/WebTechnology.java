package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum WebTechnology {
   CSS("css"),
   Bootstrap("Bootstrap"),
   LESS("LESS"),
   SASS("SASS"),
   HTML5("HTML5"),
   Restful("Restful"),
   SVG("SVG"),
   WebSocket("Web Socket"),
   Flex("Flex")
   ;

   private String text;
   WebTechnology(String text){
      this.text = text;
   }
   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }
}
