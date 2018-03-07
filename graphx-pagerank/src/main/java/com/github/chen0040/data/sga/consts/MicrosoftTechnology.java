package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum MicrosoftTechnology {
   ASPNet("ASP.Net"),
   ASPNetMVC("ASP.NET MVC"),
   MFC("MFC"),
   EntityFramework("Entity Framework"),
   NHibernate("NHibernate"),
   MicrosftAzure("Azure"),
   VSTO("VSTO"),
   WCF("WCF"),
   WPF("WPF"),
   XAML("XAML"),
   SharePoint("SharePoint"),
   Silverlight("Silverlight"),
   LinQ("LinQ"),
   MSProject("MS Project"),
   BatchScript("Batch Script"),
   MSAccess("MS Access"),
   VBNet("VB.Net"),
   ASPNetWP("ASP.Net WP"),
   Workflow("Workflow")
   ;

   private String text;
   public String getText(){
      return text;
   }
   MicrosoftTechnology(String text){
      this.text = text;
   }

   @Override public String toString() {
      return text;
   }
}
