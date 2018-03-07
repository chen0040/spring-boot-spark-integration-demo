package com.github.chen0040.data.commons.services;


import com.github.chen0040.data.commons.models.HtmlElement;

import java.util.Map;


/**
 * Created by xschen on 22/10/2016.
 */
public interface HtmlParser {
   Map<String, HtmlElement> findByElementIdsInTable(String url, String[] elementIds);
}
