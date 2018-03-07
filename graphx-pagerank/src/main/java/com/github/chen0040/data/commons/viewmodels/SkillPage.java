package com.github.chen0040.data.commons.viewmodels;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 16/10/2016.
 */
@Getter
@Setter
public class SkillPage {
   private List<SkillViewModel> content = new ArrayList<>();
   private int totalPages = 0;
   private int pageNumber = 0;

   private long totalElements = 0L;

   public SkillPage(List<SkillViewModel> content, int pageNumber, int totalPages, long totalElement){

      this.content = content;
      this.totalElements = totalElement;
      this.totalPages = totalPages;
      this.pageNumber = pageNumber;
   }


   public SkillPage() {


   }


   public boolean isEmpty() {
      return content.isEmpty();
   }
}
