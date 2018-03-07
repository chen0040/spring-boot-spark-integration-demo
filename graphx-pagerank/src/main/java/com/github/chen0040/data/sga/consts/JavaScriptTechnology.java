package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum  JavaScriptTechnology {

   React("React"),
   Redux("Redux"),
   Angular("Angular"),
   JQuery("JQuery"),
   d3("d3"),
   c3("c3"),
   elm("elm"),
   backbone("backbone"),
   prototype("prototype"),
   TypeScript("TypeScript"),
   CoffeeScript("CoffeeScript"),
   Gulp("Gulp"),
   Grunt("Grunt"),
   Webpack("Webpack"),
   NodeJS("NodeJS"),
   NPM("NPM"),
   Bower("Bower"),
   Meteor("Meteor"),
   Ajax("Ajax"),
   Knockout("Knockout"),
   Express("Express"),
   ExtJs("ExtJs"),
   Ember("Ember")
   ;

   private String text;
   JavaScriptTechnology(String text){
      this.text = text;
   }
   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }
}
