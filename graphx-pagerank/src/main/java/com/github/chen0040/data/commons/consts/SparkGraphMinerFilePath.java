package com.github.chen0040.data.commons.consts;


/**
 * Created by xschen on 13/2/2017.
 */
public class SparkGraphMinerFilePath {
   public final static String CompanyClusterFilePath = "/tmp/company-cluster.txt";
   public final static String CompanyEdgeFilePath = "/tmp/company-edge.txt";
   public final static String CompanyRankFilePath = "/tmp/company-rank.txt";
   public final static String CompanyVertexFilePath = "/tmp/company-vertex.txt";

   public final static String SkillClusterFilePath = "/tmp/skill-cluster.txt";
   public final static String SkillEdgeFilePath = "/tmp/skill-edge.txt";
   public final static String SkillRankFilePath = "/tmp/skill-rank.txt";
   public final static String SkillVertexFilePath = "/tmp/skill-vertex.txt";

   public static String getHdfsPath(String path) {
      return "/chen0040" + path;
   }

}
