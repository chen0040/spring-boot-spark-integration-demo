package com.github.chen0040.data.commons.services;



import com.github.chen0040.data.commons.messages.MyWebSocketMessage;

import java.util.Optional;


/**
 * Created by xschen on 30/1/2017.
 */
public interface WebSocketMessageCacheService {
   void enqueue(long userId, String topic, String message);

   Optional<MyWebSocketMessage> dequeue(long companyId);
}
