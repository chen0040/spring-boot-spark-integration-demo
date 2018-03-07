package com.github.chen0040.lang.commons.data.tuples;


import java.util.HashMap;


/**
 * Created by xschen on 10/12/16.
 */
public interface DataTupleFactory {
   DataTuple newInstance();

   DataTuple newInstance(HashMap<Integer, Double> row, int columnCount);
}
