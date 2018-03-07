package com.github.chen0040.spark.sga.services;


import java.util.function.Consumer;


/**
 * Created by xschen on 6/2/2017.
 */
public interface RuleMiner {
   void run(Consumer<String> terminationHandler);
}
