package com.github.chen0040.redis;


import redis.clients.jedis.*;

import java.util.Set;


/**
 * Created by xschen on 3/12/2016.
 */
public interface RedisProvider {
   void addHostPort(String host, int port);
   void clearHostPort();

   void set(String name, String value);

   String get(String name);

   void publish(String channel, String message);

   void subscribe(JedisPubSub jedisPubSub, String channel);

   void setAuth(String password);
   String getAuth();

   boolean isCluster();

   void monitor(JedisMonitor monitor);

   void flush();

   void remove(String s);

   void set(String key, String value, int expiryInSeconds);
}
