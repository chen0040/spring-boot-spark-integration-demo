package com.github.chen0040.lang.commons.data.complex;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 3/10/2016.
 */
public class IntelliContext {
   private List<IntelliTuple> tuples = new ArrayList<>();


   public int tupleCount() {
      return tuples.size();
   }


   public IntelliTuple tupleAtIndex(int i) {
      return tuples.get(i);
   }


   public void addTransaction(String ... items) {
      IntelliTuple tuple = new IntelliTuple();
      for(String item : items){
         tuple.add(item);
      }
      tuples.add(tuple);
   }
}
