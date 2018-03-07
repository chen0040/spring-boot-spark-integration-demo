package com.github.chen0040.spark.sga.services;


import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;


/**
 * Created by xschen on 9/2/2017.
 */
public interface SkillRuleMiner {
   String getVertexFilePath();

   String getEdgeFilePath();

   String getRankFilePath();

   String getClusterFilePath();

   void run(JavaSparkContext context, JavaPairRDD<String, String> skillCompanyRdd, int partitionCount) throws IOException;

   void cleanUp();
   void setUp();
}
