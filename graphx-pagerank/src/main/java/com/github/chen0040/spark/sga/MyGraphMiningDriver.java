package com.github.chen0040.spark.sga;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Created by xschen on 29/1/2017.
 */
@EnableTransactionManagement
@EnableJpaRepositories({"com.github.chen0040.data.sga.repositories"})
@EnableJpaAuditing
@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy
@ComponentScan({
        "com.github.chen0040.spark.sga",
        "com.github.chen0040.data.sga"})
@EntityScan({
        "com.github.chen0040.data.sga.models"})
@SpringBootApplication
public class MyGraphMiningDriver implements AutoCloseable {

   private static final Logger logger = LoggerFactory.getLogger(MyGraphMiningDriver.class);

   public static void main(String[] args) {
      SpringApplication.run(MyGraphMiningDriver.class, args);
   }

   @Override public void close() throws Exception {

   }
}
