package com.github.chen0040.spark.sga.graph

import java.io.{File, PrintWriter}

import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.rdd.RDD

/**
  * Created by xschen on 9/2/2017.
  */
object GraphPageRanker {
  def run(context : SparkContext, vertexFileName: String, edgeFileName: String, rankFileName: String): Unit = {
    val vertexRdd : RDD[(VertexId, String)] = context.textFile(vertexFileName)
      .map(line => {
        val parts = line.split("\t")
        (parts(1).toLong, parts(0))
      })
    val edgeRdd : RDD[Edge[Long]] = context.textFile(edgeFileName)
      .map(line => {
        val parts = line.split("\t")
        new Edge(parts(0).toLong, parts(1).toLong, parts(2).toLong)
      })

    val graph : Graph[String, Long]  =  Graph(vertexRdd, edgeRdd)

    val rank = graph.pageRank(0.001).vertices

    val vertexNameRank = rank.leftOuterJoin(vertexRdd)
        .map(entry =>
          (entry._2._2, entry._2._1)
        )
      .filter(entry => entry._1.isDefined)

    val lines = vertexNameRank.map(vertex => vertex._1.get + "\t" + vertex._2).collect()


    val writer = new PrintWriter(new File(rankFileName))
    lines.foreach(line => {
      writer.write(line + "\r\n")
    })

    writer.close()


  }


}
