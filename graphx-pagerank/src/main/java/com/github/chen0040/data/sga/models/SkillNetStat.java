package com.github.chen0040.data.sga.models;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.data.commons.models.OneToManyToOneAssociation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * Created by xschen on 9/2/2017.
 */
@Getter
@Setter
@Entity
@Table(name = "skill_net_stats", indexes = {
        @Index(name = "skillClusterIdIndex", columnList = "clusterId", unique = false),
        @Index(name = "skillRankIndex", columnList = "rank", unique = false)
})
public class SkillNetStat implements Serializable {

   public static final int MAX_SNIPPET_SIZE = 600;
   private static final long serialVersionUID = 7123172776568893332L;

   @Id
   private String skill;

   private double rank;

   private long clusterId;

   @Lob
   @Column(name="description", length = MAX_SNIPPET_SIZE)
   private String snippet;

   public void makeAssociations(List<OneToManyToOneAssociation> collection) {
      snippet = JSON.toJSONString(collection, SerializerFeature.BrowserCompatible);
   }

   public List<OneToManyToOneAssociation> findAssociations(){
      return JSON.parseArray(snippet, OneToManyToOneAssociation.class);
   }
}
