package com.github.chen0040.spark.sga.configs;


import com.github.chen0040.lang.commons.services.GlobalFlagService;
import com.github.chen0040.redis.GlobalFlagServiceRedis;
import com.github.chen0040.redis.RedisPoolFactory;
import com.github.chen0040.redis.RedisProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by xschen on 4/12/2016.
 */
@Configuration
@EnableConfigurationProperties(RedisConfiguration.RedisPubSubProperties.class)
public class RedisConfiguration {

   private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

   @Autowired
   private RedisPubSubProperties redisPubSubProperties;

   @ConfigurationProperties("mine.redis")
   public static class RedisPubSubProperties {

      private String hostports = "";
      private String auth = "";

      public String getHostports() {
         return hostports;
      }


      public void setHostports(String hostports) {
         this.hostports = hostports;
      }


      public String getAuth() {
         return auth;
      }


      public void setAuth(String auth) {
         this.auth = auth;
      }
   }

   @Bean
   public RedisProvider redisProvider(){
      RedisProvider provider = RedisPoolFactory.getInstance();
      provider.clearHostPort();

      String hostPorts = redisPubSubProperties.getHostports();
      logger.info("redis host and ports: {}", hostPorts);


      String[] hostPortArray = hostPorts.split(",");
      for(int i=0; i < hostPortArray.length; ++i) {
         String hostPort = hostPortArray[i];
         logger.info("host and port: {}", hostPort);

         String[] hostPortParts = hostPort.split(":");
         String host = hostPortParts[0];
         int port = Integer.parseInt(hostPortParts[1]);

         logger.info("host: {}", host);
         logger.info("port: {}", port);

         provider.addHostPort(host, port);
      }
      provider.setAuth(redisPubSubProperties.getAuth());

      return provider;

   }

   @Bean
   public GlobalFlagService globalFlagService(RedisProvider redisProvider) {
      return new GlobalFlagServiceRedis(redisProvider);
   }
}
