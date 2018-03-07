package com.github.chen0040.data.commons.viewmodels;


import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 23/2/2017.
 */
@Getter
@Setter
public class NetworkArc {
   private int from;
   private int to;
   private double weight;
   private String name;

   public NetworkArc() {

   }

   public NetworkArc(String name, int from, int to, double weight) {
      this.name = name;
      this.from = from;
      this.to = to;
      this.weight = weight;
   }
}
