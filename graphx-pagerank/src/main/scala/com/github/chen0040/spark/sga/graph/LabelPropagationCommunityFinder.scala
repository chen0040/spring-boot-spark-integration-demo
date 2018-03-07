package com.github.chen0040.spark.sga.graph

import java.io.{File, PrintWriter}

import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.rdd.RDD

/**
  * Created by xschen on 26/2/2016.
  */
object LabelPropagationCommunityFinder {
  def mergeMsg(count1: Map[String, Int], count2: Map[String, Int])  : Map[String, Int] = {
    (count1.keySet ++ count2.keySet).map {
      i =>
        val count1Val = count1.getOrElse(i, 0)
        val count2Val = count2.getOrElse(i, 0)
        i -> (count1Val + count2Val)
    }.toMap
  }

  def run(context: SparkContext, vertexFileName: String, edgeFileName: String, clusterFileName: String) : Unit = {

    val vertexRdd : RDD[(VertexId, String)] = context.textFile(vertexFileName)
      .map(line => {
        val parts = line.split("\t")
        (parts(1).toLong, parts(0))
      })
    val edgeRdd : RDD[Edge[Double]] = context.textFile(edgeFileName)
      .map(line => {
        val parts = line.split("\t")
        new Edge(parts(0).toLong, parts(1).toLong, 10.0 / (parts(2).toDouble+1))
      })

    val graph : Graph[String, Double]  =  Graph(vertexRdd, edgeRdd)

    println("Graph: " + graph.vertices.count())

    val labelRdd: RDD[(VertexId, Node)] = graph.vertices.map {
      case (id, _) =>
        (id, Node(id.toString))
    }

    val labeldGraph = graph.outerJoinVertices(labelRdd){
      case (_, node, b) =>
        b match {
          case Some(_) => b.get
          case None => Node("")
        }
    }

    //labeldGraph.vertices.take(10).foreach(println)

    //println("initially")
    //labeldGraph.vertices.map(vertex => (vertex._2.label, 1)).reduceByKey((x, y) => x + y).sortBy(_._2, false).take(10).foreach(println)

    val finalGraph = labeldGraph.pregel(Map[String, Int](), 50)(
      vprog = (_, node, A) => if(A.isEmpty) node else Node(A.maxBy(_._2)._1),
      sendMsg = (triplet) =>
        Iterator((triplet.dstId, Map(triplet.srcAttr.label -> 1)), (triplet.srcId, Map(triplet.dstAttr.label -> 1))),
      mergeMsg)

    //println("afterwards")
    //finalGraph.vertices.map(vertex => (vertex._2.label, 1)).reduceByKey((x, y) => x + y).sortBy(_._2, false).take(10).foreach(println)

    val cluster = finalGraph.vertices

    val vertexNameRank = cluster.leftOuterJoin(vertexRdd)
      .map(entry =>
        (entry._2._2, entry._2._1.label)
      )
      .filter(entry => entry._1.isDefined)

    val lines = vertexNameRank.map(vertex => vertex._1.get + "\t" + vertex._2).collect()


    val writer = new PrintWriter(new File(clusterFileName))
    lines.foreach(line => {
      writer.write(line + "\r\n")
    })

    writer.close()

  }
}
