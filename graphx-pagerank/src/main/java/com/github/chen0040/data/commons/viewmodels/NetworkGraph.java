package com.github.chen0040.data.commons.viewmodels;


import com.github.chen0040.data.commons.models.OneToManyToOneAssociation;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


/**
 * Created by xschen on 9/1/2017.
 */
@Getter
@Setter
public class NetworkGraph {
   private List<NetworkVertex> vertices = new ArrayList<>();
   private List<NetworkArc> arcs = new ArrayList<>();
   private int totalPages = 0;
   private int pageNumber = 0;

   private long totalElements = 0L;

   public NetworkGraph(Map<String, Double> ranks, Map<String, Integer> clusters, Map<String, List<OneToManyToOneAssociation>> content, int pageNumber, int totalPages, long totalElement){

      Map<String, Integer> vertexMapping = new HashMap<>();
      for(String vertexName : content.keySet()){
         vertexMapping.put(vertexName, vertexMapping.size());
         vertices.add(new NetworkVertex(vertexName, vertexMapping.get(vertexName), ranks.getOrDefault(vertexName, 0.0), clusters.getOrDefault(vertexName, -1)));
      }
      for(Map.Entry<String, List<OneToManyToOneAssociation>> entry : content.entrySet() ) {
         List<OneToManyToOneAssociation> links = entry.getValue();
         String from = entry.getKey();
         int fromId = vertexMapping.get(from);
         for(OneToManyToOneAssociation link : links) {
            String to = link.getEntity2();

            int toId;
            if(!vertexMapping.containsKey(to)){

               vertexMapping.put(to, vertexMapping.size());
               toId = vertexMapping.get(to);
               vertices.add(new NetworkVertex(to, toId, ranks.getOrDefault(to, 0.0), clusters.getOrDefault(to, -1)));
            } else {
               toId = vertexMapping.get(to);
            }


            Set<String> networkArcs = link.getLinks();
            if(networkArcs.isEmpty()){
               NetworkArc networkArc = new NetworkArc("" + link.getCount(), fromId, toId, link.getCount());
               arcs.add(networkArc);
            } else {
               String description = "";
               for (String arc : networkArcs) {
                  description = description.concat(arc).concat(", ");
               }
               NetworkArc networkArc = new NetworkArc(description, fromId, toId, 10.0 / arcs.size());
               arcs.add(networkArc);
            }
         }
      }

      this.totalElements = totalElement;
      this.totalPages = totalPages;
      this.pageNumber = pageNumber;
   }


   public NetworkGraph() {


   }


   public boolean isEmpty() {
      return vertices.isEmpty();
   }
}
