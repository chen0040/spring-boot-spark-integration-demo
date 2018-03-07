package com.github.chen0040.lang.commons.data.tuples;


import java.util.HashMap;


/**
 * Created by xschen on 10/12/16.
 */
public class DataTupleFactoryImpl implements DataTupleFactory {

   @Override public DataTuple newInstance() {
      return new DataTuple();
   }


   @Override public DataTuple newInstance(HashMap<Integer, Double> row, int columnCount) {
      return new DataTuple(row, columnCount);
   }
}
