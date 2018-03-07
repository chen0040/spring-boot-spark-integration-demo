package com.github.chen0040.data.commons.models;


import java.io.Serializable;


/**
 * Created by xschen on 7/2/2017.
 */
public class CoPair implements Serializable {
   private static final long serialVersionUID = 3414159397676748327L;
   private final String item1;
   private final String item2;
   private final String coPairId;

   public CoPair(String item1, String item2){
      this.item1 = item1;
      this.item2 = item2;
      this.coPairId = item1 + " and " + item2;
   }

   public String getItem1() {
      return item1;
   }

   public String getItem2() {
      return item2;
   }

   public String getCoPairId() {
      return coPairId;
   }

   public String toString(){
      return coPairId;
   }


   @Override public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      CoPair coPair = (CoPair) o;

      return this.coPairId.equals(coPair.coPairId);

   }


   @Override public int hashCode() {
      return coPairId.hashCode();
   }


}
