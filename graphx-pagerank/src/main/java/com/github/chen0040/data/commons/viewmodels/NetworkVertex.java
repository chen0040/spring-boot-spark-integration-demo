package com.github.chen0040.data.commons.viewmodels;


import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 23/2/2017.
 */
@Getter
@Setter
public class NetworkVertex {
   private String name;
   private int id;
   private double rank;
   private int clusterId;

   public NetworkVertex(String name, int id, double rank, int clusterId) {
      this.name = name;
      this.id = id;
      this.rank = rank;
      this.clusterId = clusterId;
   }

   public NetworkVertex(){

   }
}
