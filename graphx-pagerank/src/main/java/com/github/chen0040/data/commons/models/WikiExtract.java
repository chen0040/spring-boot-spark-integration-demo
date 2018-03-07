package com.github.chen0040.data.commons.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Optional;


/**
 * Created by xschen on 16/10/2016.
 */
@Getter
@Setter
public class WikiExtract implements Serializable {
   private static final long serialVersionUID = 5991120631839964717L;

   private String batchcomplete = "";
   private WikiExtractQuery query = new WikiExtractQuery();


   public String summary() {
      Optional<String> summaryOptional = query.getPages().entrySet().stream().map(entry -> entry.getValue().getExtract()).filter(extract -> !StringUtils.isEmpty(extract)).findFirst();
      if(summaryOptional.isPresent()){
         return summaryOptional.get();
      }
      return "";
   }
}
