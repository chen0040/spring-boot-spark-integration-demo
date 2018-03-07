package com.github.chen0040.lang.commons.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Random;


/**
 * Created by xschen on 1/27/16.
 */
public class IpTools {
   private static final Logger logger = LoggerFactory.getLogger(IpTools.class);


   public static String getIpAddress() {
      String result = null;
      Enumeration<NetworkInterface> interfaces = null;
      try {
         interfaces = NetworkInterface.getNetworkInterfaces();
      }
      catch (SocketException e) {
         // handle error
      }

      if (interfaces != null) {
         while (interfaces.hasMoreElements() && (result == null || result.equals(""))) {
            NetworkInterface i = interfaces.nextElement();
            Enumeration<InetAddress> addresses = i.getInetAddresses();
            while (addresses.hasMoreElements() && (result == null || result.isEmpty())) {
               InetAddress address = addresses.nextElement();
               if (!address.isLoopbackAddress() && address.isSiteLocalAddress()) {
                  result = address.getHostAddress();
               }
            }
         }
      }

      return result;
   }

   public static int findAvailablePort() {
      ServerSocket socket = null;
      try {
         socket = new ServerSocket(0);
         socket.setReuseAddress(true);
         int port = socket.getLocalPort();
         logger.info("{} is available", port);

         try {
            socket.close();
         } catch (IOException e) {
            logger.error("Check port exception occurred: ", e);
         }
         return port;
      } catch (IOException e) {
      } finally {
         if (socket != null) {
            try {
               socket.close();
            } catch (IOException e) {
               logger.error("Check port exception occurred on close ", e);
            }
         }
      }
      return -1;
   }

   public static boolean isPortAvailable(int port){

      ServerSocket ss = null;
      DatagramSocket ds = null;
      try {

         ss = new ServerSocket();

         InetSocketAddress endPoint = new InetSocketAddress(getIpAddress(), port);
         ss.bind(endPoint);

         ss.setReuseAddress(true);
         ds = new DatagramSocket(port);
         ds.setReuseAddress(true);

         logger.info("port available: {}", port);

         return true;
      } catch (IOException e) {
         logger.error("Failed to bind port: " + port, e);
      } finally {
         if (ds != null) {
            ds.close();

         }


         if (ss != null) {
            try {
               ss.close();
            } catch (IOException e) {
                /* should not be thrown */
            }
         }
      }



      return false;

   }

   public static void main(String[] args) {
      logger.info(getIpAddress());
   }


   public static int getNextAvailablePort(int port) {
      int nextPort = port + 1;
      if(isPortAvailable(nextPort)){
         return nextPort;
      } else {
         return getNextAvailablePort(nextPort);
      }
   }


   public static int getNextAvailablePortWithRandomDelay(int port, Random random) {
      int nextPort = port + 1;

      if(isPortAvailable(nextPort)){
         return nextPort;
      } else {
         try {
            Thread.sleep(Math.abs(random.nextLong() % 10000L));
         }
         catch (InterruptedException e) {
            logger.error("sleep interrupted");
         }

         return getNextAvailablePortWithRandomDelay(nextPort, random);
      }


   }
}
