package com.github.chen0040.lang.commons.data.complex;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 3/10/2016.
 */
public class IntelliTuple {

   private List<IntelliObject> tuple = new ArrayList<>();

   public int tupleLength() {
      return tuple.size();
   }


   public String getString(int j) {
      return tuple.get(j).asString();
   }


   public void add(String item) {
      tuple.add(new IntelliObject(item));
   }
}
