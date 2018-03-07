package com.github.chen0040.data.commons.services;


import com.github.chen0040.data.commons.messages.MyWebSocketMessage;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by xschen on 30/1/2017.
 */
public class WebSocketMessageCacheServiceImpl implements WebSocketMessageCacheService {
   private ConcurrentMap<Long, Queue<MyWebSocketMessage>> queues = new ConcurrentHashMap<>();
   private Lock lock = new ReentrantLock();

   @Override public void enqueue(long userId, String topic, String messageBody) {
      Queue<MyWebSocketMessage> q;
      if(queues.containsKey(userId)){
         q = queues.get(userId);
      } else {
         q = new LinkedList<>();
         queues.put(userId, q);
      }

      MyWebSocketMessage message = new MyWebSocketMessage();
      message.setBody(messageBody);
      message.setUserId(userId);
      message.setTopic(topic);
      message.setValid(true);

      lock.lock();
      try{
         q.add(message);
         if(q.size() > 15) {
            q.remove();
         }
      } finally {
         lock.unlock();
      }
   }

   @Override public Optional<MyWebSocketMessage> dequeue(long companyId) {
      if(queues.containsKey(companyId)) {
         Queue<MyWebSocketMessage> q = queues.get(companyId);

         MyWebSocketMessage message = null;
         lock.lock();
         try{
            if(!q.isEmpty()) {
               message = q.remove();
            }
         } finally {
            lock.unlock();
         }

         if(message ==null){
            return Optional.empty();
         }
         return Optional.of(message);
      } else {
         return Optional.empty();
      }

   }
}
