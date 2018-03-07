package com.github.chen0040.data.commons.services;


import java.util.Map;


/**
 * Created by xschen on 22/10/2016.
 */
public interface WebClient {
   String get(String url) throws Exception;

   String get(String url, Map<String, String> headers);
}
