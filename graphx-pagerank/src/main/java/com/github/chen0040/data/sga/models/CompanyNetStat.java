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
@Table(name = "company_net_stats", indexes = {
        @Index(name = "companyClusterIdIndex", columnList = "clusterId", unique = false),
        @Index(name = "companyRankIndex", columnList = "rank", unique = false)
})
public class CompanyNetStat implements Serializable {

   public static final int MAX_SNIPPET_SIZE = 6000;
   private static final long serialVersionUID = -8457666123372501961L;

   @Id
   private String companyName;

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
