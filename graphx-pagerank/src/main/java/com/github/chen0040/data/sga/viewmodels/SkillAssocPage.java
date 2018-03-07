package com.github.chen0040.data.sga.viewmodels;


import com.github.chen0040.data.sga.models.SkillAssocEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 2/1/2017.
 */
@Getter
@Setter
public class SkillAssocPage {
   private List<SkillAssocEntity> content = new ArrayList<>();
   private int totalPages = 0;
   private int pageNumber = 0;

   private long totalElements = 0L;

   public SkillAssocPage(List<SkillAssocEntity> content, int pageNumber, int totalPages, long totalElement){

      this.content = content;
      this.totalElements = totalElement;
      this.totalPages = totalPages;
      this.pageNumber = pageNumber;
   }


   public SkillAssocPage() {


   }


   public boolean isEmpty() {
      return content.isEmpty();
   }
}
