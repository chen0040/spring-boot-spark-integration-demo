package com.github.chen0040.redis;


import com.github.chen0040.lang.commons.utils.StringUtils;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisMonitor;
import redis.clients.jedis.JedisPubSub;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by xschen on 3/12/2016.
 */
public class RedisClusterFactory implements RedisProvider, AutoCloseable {
   private static RedisClusterFactory instance;
   private JedisCluster cluster;
   private final Set<HostAndPort> hostAndPorts = new HashSet<>();
   private final Set<String> subscribedChannels = new HashSet<>();
   private static final Logger logger = LoggerFactory.getLogger(RedisClusterFactory.class);
   ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
   private String jedisAuth = "chen0040";

   public static synchronized RedisProvider getInstance(){
      if(instance == null) {
         instance = new RedisClusterFactory();
      }
      return instance;
   }

   private RedisClusterFactory(){
      hostAndPorts.add(new HostAndPort("redis-server-host-name", 6379));
      hostAndPorts.add(new HostAndPort("redis-server-host-name", 6380));
      hostAndPorts.add(new HostAndPort("redis-server-host-name", 6381));

   }

   public void addHostPort(String host, int port) {
      hostAndPorts.add(new HostAndPort(host, port));
   }

   public void clearHostPort(){
      hostAndPorts.clear();
   }

   @Override public void set(String name, String value) {
      getCluster().set(name, value);
   }

   @Override public void set(String name, String value, int expiryInSeconds) {
      JedisCluster cluster = getCluster();
      cluster.set(name, value);
      cluster.expire(name, expiryInSeconds);
   }

   @Override public String get(String name) {
      return getCluster().get(name);
   }

   @Override public void publish(String channel, String message) {
      service.submit(() -> publishWithRetry(channel, message, 5));
   }

   private void publishWithRetry(String channel, String message, int countDown) {
      if(countDown <= 0) {
         logger.info("Failed to publish due to repeated connection exception");
      }
      try {
         cluster = null;
         getCluster().publish(channel, message);
      } catch(Exception JedisConnectionException) {
         Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
         publishWithRetry(channel, message, countDown-1);
      }
   }

   public synchronized JedisCluster getCluster(){
      if(cluster == null) {
         int connectionTimeout = 2000;
         int soTimeout = 2000;
         int maxAttempts = 5;
         String password = jedisAuth;
         final GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();


         if(!StringUtils.isEmpty(jedisAuth)) {
            cluster = new JedisCluster(hostAndPorts, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
         } else {
            cluster = new JedisCluster(hostAndPorts);
         }
      }
      return cluster;
   }


   @Override public void subscribe(JedisPubSub jedisPubSub, String channel) {
      getCluster().subscribe(jedisPubSub, channel);
   }


   @Override public void setAuth(String password) {
      jedisAuth = password;
   }


   @Override public String getAuth() {
      return jedisAuth;
   }


   @Override public boolean isCluster() {
      return true;
   }


   @Override public void monitor(JedisMonitor monitor) {

   }


   @Override public void flush() {
      //getCluster().flushAll();
   }


   @Override public void remove(String s) {
      getCluster().del(s);
   }


   @Override public void close() throws Exception {
      if(cluster != null) {
         cluster.close();
      }
   }
}
