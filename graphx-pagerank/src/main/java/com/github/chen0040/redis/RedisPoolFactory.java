package com.github.chen0040.redis;


import com.github.chen0040.lang.commons.utils.StringUtils;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by xschen on 3/12/2016.
 */
public class RedisPoolFactory implements RedisProvider, AutoCloseable {
   private static RedisPoolFactory instance;
   private JedisPool pool;
   private String host;
   private int port;
   private final Set<String> subscribedChannels = new HashSet<>();
   private static final Logger logger = LoggerFactory.getLogger(RedisPoolFactory.class);
   ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
   private String jedisAuth = "chen0040";

   public static synchronized RedisProvider getInstance(){
      if(instance == null) {
         instance = new RedisPoolFactory();
      }
      return instance;
   }

   private RedisPoolFactory(){
      host = "redis-server-host-name";
      port = 6379;

   }

   public void addHostPort(String host, int port) {
      this.host = host;
      this.port = port;
   }


   @Override public void clearHostPort() {
      host = null;
      port = -1;
   }


   @Override public void set(String name, String value) {
      try(Jedis jedis = getPool().getResource()){
         jedis.set(name, value);
      }
   }

   @Override public void set(String name, String value, int expiryInSeconds) {
      try(Jedis jedis = getPool().getResource()){
         jedis.set(name, value);
         jedis.expire(name, expiryInSeconds);
      }
   }

   @Override public String get(String name) {
      String result;
      try(Jedis jedis = getPool().getResource()){
         result = jedis.get(name);
      }catch(Exception exception) {
         result = null;
      }
      return result;
   }

   @Override public void publish(String channel, String message) {
      service.submit(() -> publishWithRetry(channel, message, 5));
   }

   private void publishWithRetry(String channel, String message, int countDown) {
      if(countDown <= 0) {
         logger.info("Failed to publish due to repeated connection exception");
      }
      try(Jedis jedis = getPool().getResource()) {
         jedis.publish(channel, message);
      } catch(Exception JedisConnectionException) {
         Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
         publishWithRetry(channel, message, countDown-1);
      }
   }

   public synchronized JedisPool getPool(){
      if(pool == null) {
         String password = null;
         if(!StringUtils.isEmpty(jedisAuth)) {
            password = jedisAuth;
         }
         int timeout = 0; //2000;
         pool = new JedisPool(new GenericObjectPoolConfig(), host, port, timeout, password, 0, null);
      }
      return pool;
   }


   @Override public void subscribe(JedisPubSub jedisPubSub, String channel) {
      try(Jedis jedis = getPool().getResource()){

         jedis.subscribe(jedisPubSub, channel);
      }
   }


   @Override public void setAuth(String password) {
      jedisAuth = password;
   }


   @Override public String getAuth() {
      return jedisAuth;
   }


   @Override public boolean isCluster() {
      return false;
   }


   @Override public void monitor(JedisMonitor monitor) {
      try(Jedis jedis = getPool().getResource()){
         jedis.monitor(monitor);
      }
   }


   @Override public void flush() {
      try(Jedis jedis = getPool().getResource()) {
         jedis.flushAll();
      }
   }


   @Override public void remove(String key) {
      try(Jedis jedis = getPool().getResource()){
         jedis.del(key);
      }
   }


   @Override public void close() throws Exception {
      if(pool != null) {
         pool.destroy();
      }
   }
}
