package com.github.chen0040.lang.commons.primitives;


/**
 * Created by xschen on 29/10/2016.
 */
public interface CopyCat<T> {

   T makeCopy();
   void copy(T rhs);
}
