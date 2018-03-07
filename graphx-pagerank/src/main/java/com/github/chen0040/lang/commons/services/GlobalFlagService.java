package com.github.chen0040.lang.commons.services;


/**
 * Created by xschen on 18/1/2017.
 */
public interface GlobalFlagService {
   void setFlag(String name);
   void clearFlag(String name);

   boolean hasFlag(String name);
}
