package com.github.chen0040.lang.commons.utils.jvm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by xschen on 10/3/16.
 * Implement token bucket scheduling algorithm
 */
public class JvmRuntimeAdvisor implements AutoCloseable {

   private static JvmRuntimeAdvisor instance;
   private static Logger logger = LoggerFactory.getLogger(JvmRuntimeAdvisor.class);

   private Map<String, Integer> cards = new HashMap<>();
   private Map<String, Integer> maxBursts = new HashMap<>();
   private Lock lock;
   private final ScheduledExecutorService scheduler;

   private JvmRuntimeAdvisor(){
      lock = new ReentrantLock();
      scheduler =
              Executors.newScheduledThreadPool(1);

   }





   public synchronized int getToken(final String operationName){
      lock.lock();
      try {
         if (!maxBursts.containsKey(operationName)) {
            setTokenBucket4Operation(operationName, 10, 20000L);
         }
      } finally {
         lock.unlock();
      }

      int card = 0;
      lock.lock();
      try {
         card = cards.getOrDefault(operationName, 0);
         if (card > 0) {
            cards.put(operationName, card - 1);
         }
      } finally {
         lock.unlock();
      }

      return card;
   }

   public boolean ask4Token(final String operationName){
      return getToken(operationName) > 0;
   }

   public static synchronized JvmRuntimeAdvisor getInstance(){
      if(instance == null) {
         instance = new JvmRuntimeAdvisor();
      }
      return instance;
   }

   public JvmMemory getMemory(){
      Runtime runtime = Runtime.getRuntime();

      double allocatedMemoryInMB = runtime.totalMemory() / 1048576.0;
      double freeMemoryInMB = runtime.freeMemory() / 1048576.0;
      double maxMemoryInMB = runtime.maxMemory() / 1048576.0;
      double usedMemoryInMB = allocatedMemoryInMB - freeMemoryInMB;

      return new JvmMemory(allocatedMemoryInMB, freeMemoryInMB, maxMemoryInMB, usedMemoryInMB);
   }


   public double getRunProbability(double weight){
      return Math.exp(- getMemory().getUsedMemoryInMB() / JvmStartupConfig.getXms());
   }


   public void setTokenBucket4Operation(final String operationName, int maxBurst, long intervalInMilliseconds) {
      maxBursts.put(operationName, maxBurst);

      long initialDelay = 0L;

      scheduler.scheduleAtFixedRate(() -> {
         lock.lock();
         if(cards.containsKey(operationName)){
            if(cards.get(operationName) < maxBursts.get(operationName)){
               cards.put(operationName, cards.get(operationName) + 1);
            }
         } else {
            cards.put(operationName, 1);
         }
         lock.unlock();
      }, initialDelay, intervalInMilliseconds, TimeUnit.MILLISECONDS);

   }


   @Override public void close() throws Exception {
      if(!scheduler.isShutdown()) {
         scheduler.shutdown();
      }


   }


}
