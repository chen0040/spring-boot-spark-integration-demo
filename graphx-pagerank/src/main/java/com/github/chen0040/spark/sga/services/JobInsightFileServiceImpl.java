package com.github.chen0040.spark.sga.services;


import com.github.chen0040.data.commons.consts.SparkGraphMinerCommand;
import com.github.chen0040.data.commons.models.MyWorkerCompleted;
import com.github.chen0040.data.commons.models.MyWorkerProgress;
import com.github.chen0040.data.sga.models.JobInsight;
import com.github.chen0040.data.sga.services.JobInsightService;
import com.github.chen0040.lang.commons.utils.CollectionUtil;
import com.github.chen0040.lang.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * Created by xschen on 9/2/2017.
 */
@Service
public class JobInsightFileServiceImpl implements JobInsightFileService {

   @Autowired
   private JobInsightService jobInsightService;

   @Override
   public MyWorkerCompleted writeLargeFile(String filename, Consumer<MyWorkerProgress> progressHandler) throws IOException {
      int totalPages = 20;
      int pageIndex = 0;
      int pageSize = 20;
      long totalElements = 0L;

      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));

      int prevPercentage = -1;
      int remainingTime = 0;

      long startTime = new Date().getTime();


      while(pageIndex < totalPages) {
         Page<JobInsight> page = jobInsightService.findPagedJobInsights(pageIndex, pageSize);
         totalElements = page.getTotalElements();
         totalPages = page.getTotalPages();

         List<JobInsight> data = page.getContent();

         if(data.isEmpty()){
            break;
         }

         pageSize = Math.min(1000, (int)(totalElements / 100));
         pageSize = Math.max(20, pageSize);

         if(totalPages > 0){
            int percentage = (pageIndex+1) * 100 / totalPages;
            if(prevPercentage != percentage) {
               MyWorkerProgress progress = new MyWorkerProgress();
               progress.setPercentage(percentage);

               long duration = new Date().getTime() - startTime;

               int durationInSeconds = (int)(duration / 1000);

               if(percentage > 0){
                  int totalDurationInSeconds = durationInSeconds * 100 / percentage;
                  remainingTime = Math.max(0, totalDurationInSeconds - durationInSeconds);
               }

               progress.setRemainingTime(remainingTime);
               progress.setMessage("Scanning job insights at " + percentage + "%");
               progress.setError("");
               prevPercentage = percentage;

               if(progressHandler != null){
                  progressHandler.accept(progress);
               }
            }
         }

         for(int i=0; i < data.size(); ++i){
            JobInsight jobInsight = data.get(i);
            String skills = jobInsight.getSkills();
            List<String> skillList = CollectionUtil.toList(skills.split(",")).stream()
                    .map(String::trim)
                    .filter(entry -> !StringUtils.isEmpty(entry))
                    .collect(Collectors.toList());
            String companyName = StringUtils.replace(jobInsight.getCompanyName(), "\t", " ");
            String jobTitle = StringUtils.replace(jobInsight.getJobTitle(), "\t", " ");

            if(StringUtils.isEmpty(companyName) || StringUtils.isEmpty(jobTitle)){
               continue;
            }

            for(int j=0; j < skillList.size(); ++j){
               String skill = skillList.get(j).replace("\t", " ");

               writer.write(skill+"\t"+companyName+"\t"+jobTitle+"\r\n");
            }
         }

         pageIndex++;
      }

      writer.close();

      MyWorkerProgress progress = new MyWorkerProgress();
      progress.setPercentage(100);
      progress.setRemainingTime(0);
      progress.setMessage("Scanning job insights at 100%");
      progress.setError("");
      if(progressHandler != null){
         progressHandler.accept(progress);
      }

      MyWorkerCompleted completed = new MyWorkerCompleted();
      completed.setChangedRows(totalPages);
      completed.setMessage(SparkGraphMinerCommand.COMMAND_GRAPH_MINING + ": Successfully extracted job insights from database");

      return completed;
   }
}
