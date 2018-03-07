package com.github.chen0040.spark.sga.components;

import com.github.chen0040.lang.commons.utils.StringUtils;
import com.github.chen0040.spark.sga.services.RuleMiner;
import com.github.chen0040.mesos.client.chronos.ChronosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Created by chen0 on 2/6/2016.
 */
@Component
public class GraphMinerLoader implements ApplicationListener<ApplicationReadyEvent> {
   private static final Logger logger = LoggerFactory.getLogger(GraphMinerLoader.class);

   @Autowired
   private RuleMiner ruleMiner;

   @Value("${mine.bigdata.chronos.url}")
   private String chronosUrl;

   @Override public void onApplicationEvent(ApplicationReadyEvent e) {
      logger.info("Loader triggered at {}", e.getTimestamp());

      ApplicationContext context = e.getApplicationContext();
      String[] beanNames = context.getBeanDefinitionNames();
      Arrays.sort(beanNames);
      for (String beanName : beanNames) {
         logger.info("bean: {}", beanName);
      }
      logger.info("Run loader...");

      ruleMiner.run(command->{
         logger.info("Run command [{}] completed", command);

         String chronosJobName = "chen0040-" + command;



         if(!StringUtils.isEmpty(chronosJobName) && !StringUtils.isEmpty(chronosUrl)){
            logger.info("killing job {} at {}", chronosJobName, chronosUrl);
            try {
               ChronosUtil.killJob(chronosUrl, chronosJobName);
            }catch(Exception exception) {
               logger.error("Failed to remove chronos job " + chronosJobName, exception);
            }
         } else {
            logger.info("Not killing job as name {} is not at {}", chronosJobName, chronosUrl);
         }
         System.exit(0);
      });



   }


}
