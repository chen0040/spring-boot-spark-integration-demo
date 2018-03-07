package com.github.chen0040.redis;


import com.github.chen0040.lang.commons.services.GlobalFlagService;
import com.github.chen0040.lang.commons.utils.StringUtils;


/**
 * Created by xschen on 18/1/2017.
 */
public class GlobalFlagServiceRedis implements GlobalFlagService {

   private final RedisProvider redisProvider;

   public GlobalFlagServiceRedis(RedisProvider redisProvider) {
      this.redisProvider = redisProvider;
   }

   @Override public void setFlag(String name) {
      redisProvider.set(name, "1");
   }


   @Override public void clearFlag(String name) {
      redisProvider.remove(name);
   }

   @Override public boolean hasFlag(String name) {
      return !StringUtils.isEmpty(redisProvider.get(name));
   }
}
