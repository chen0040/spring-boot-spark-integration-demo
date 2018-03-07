package com.github.chen0040.lang.commons.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;


/**
 * Created by xschen on 12/10/2016.
 */
public class UrlTools {
   private static final Logger logger = LoggerFactory.getLogger(UrlTools.class);

   public static String getHost(String url){
      URI uri = null;
      try {
         uri = new URI(url);
      }
      catch (URISyntaxException e) {
         e.printStackTrace();
      }
      if(uri != null) {
         String domain = uri.getHost();
         return domain;
      }
      return null;
   }

   public static String getIpFromHost(String host) {
      InetAddress address = null;
      try {
         address = InetAddress.getByName(host);
         return address.getHostAddress();
      } catch (UnknownHostException e) {
         logger.error("unknown host: " + host, e);
      }
      return null;

   }
}
