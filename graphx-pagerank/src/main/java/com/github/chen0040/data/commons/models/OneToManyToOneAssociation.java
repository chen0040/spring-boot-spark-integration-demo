package com.github.chen0040.data.commons.models;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by xschen on 7/2/2017.
 */
public class OneToManyToOneAssociation implements Serializable {
   private static final long serialVersionUID = 3302027701152934776L;
   private String entity2;
   private Set<String> links;
   private int count;

   public OneToManyToOneAssociation(String entity2, Set<String> links){
      this.entity2 = entity2;
      this.links = links;
      this.count = links.size();
   }

   public OneToManyToOneAssociation(){
      entity2 = "";
      links = new HashSet<>();
      count = 0;
   }


   public String getEntity2() {
      return entity2;
   }


   public Set<String> getLinks() {
      return links;
   }


   public void setEntity2(String entity2) {
      this.entity2 = entity2;
   }


   public void setLinks(Set<String> links) {
      this.links = links;
   }


   public int getCount() {
      return count;
   }


   public void setCount(int count) {
      this.count = count;
   }


   public void addLinks(Set<String> links) {
      this.links.addAll(links);
      this.count = this.links.size();
   }


   public void truncateLinks(int maxMinCount) {
      if(getCount() < maxMinCount){
         links.clear();
         count =0;
      }
   }
}
