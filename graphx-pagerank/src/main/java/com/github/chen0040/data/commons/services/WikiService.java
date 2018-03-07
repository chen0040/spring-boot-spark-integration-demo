package com.github.chen0040.data.commons.services;


import com.github.chen0040.data.commons.models.WikiExtract;


/**
 * Created by xschen on 16/10/2016.
 */
public interface WikiService {
   WikiExtract crawl(String query);

   String getSummary(String skillName);
}
