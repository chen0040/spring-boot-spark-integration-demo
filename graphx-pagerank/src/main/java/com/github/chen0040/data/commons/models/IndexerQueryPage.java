package com.github.chen0040.data.commons.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 16/10/2016.
 */
@Getter
@Setter
public class IndexerQueryPage<T> {
   private List<T> content = new ArrayList<>();
   private int totalPages;
   private int pageNumber;
   private IndexerQuery query;

   private long totalElements;

   public IndexerQueryPage(IndexerQuery query, List<T> content, int pageNumber, int totalPages, long totalElement){
      this.query = query;
      this.content = content;
      this.totalElements = totalElement;
      this.totalPages = totalPages;
      this.pageNumber = pageNumber;
   }


   public boolean isEmpty() {
      return content.isEmpty();
   }
}
