package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.enums.TaskState;
import com.github.chen0040.data.sga.models.JobIndexerTask;
import com.github.chen0040.data.sga.viewmodels.JobIndexerTaskPage;
import com.github.chen0040.data.sga.repositories.JobIndexerTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by xschen on 12/27/16.
 */
@Service
public class JobIndexerTaskServiceImpl implements JobIndexerTaskService {

   private static final Logger logger = LoggerFactory.getLogger(JobIndexerTaskServiceImpl.class);

   @Autowired
   private JobIndexerTaskRepository jobIndexerTaskRepository;

   @Override
   public Optional<JobIndexerTask> getPendingTask() {
      final TaskState state = TaskState.Pending;
      List<JobIndexerTask> tasks = jobIndexerTaskRepository.findFirstByTaskStateOrderByCreatedTimeAsc(state);
      if(tasks.isEmpty()) {
         return Optional.empty();
      } else {
         return Optional.of(tasks.get(0));
      }
   }


   @Transactional
   @Override public JobIndexerTask markAsStarted(JobIndexerTask task) {
      task.setLocked(true);
      task.setStartTime(new Date().getTime());
      task.setTaskState(TaskState.WIP);

      return jobIndexerTaskRepository.save(task);
   }


   @Transactional
   @Override public JobIndexerTask markAsCompleted(JobIndexerTask task) {
      task.setLocked(false);
      task.setEndTime(new Date().getTime());
      task.setTaskState(TaskState.Resolved);

      return jobIndexerTaskRepository.save(task);
   }

   @Transactional
   @Override public Optional<JobIndexerTask> markAsCompleted(long taskId) {

      JobIndexerTask task = jobIndexerTaskRepository.findOne(taskId);

      if(task != null) {
         task.setLocked(false);
         task.setEndTime(new Date().getTime());
         task.setTaskState(TaskState.Resolved);

         return Optional.of(jobIndexerTaskRepository.save(task));
      } else {
         return Optional.empty();
      }
   }

   @Transactional
   @Override public Optional<JobIndexerTask> markAsPending(long taskId) {

      JobIndexerTask task = jobIndexerTaskRepository.findOne(taskId);

      if(task != null) {
         task.setLocked(false);
         task.setEndTime(0L);
         task.setTaskState(TaskState.Pending);

         return Optional.of(jobIndexerTaskRepository.save(task));
      } else {
         return Optional.empty();
      }
   }

   @Transactional
   @Override public void markAsPending(List<JobIndexerTask> rescheduled) {

      for(int i=0; i < rescheduled.size(); ++i) {
         JobIndexerTask task = rescheduled.get(i);
         task.setLocked(false);
         task.setEndTime(0L);
         task.setTaskState(TaskState.Pending);
      }

      jobIndexerTaskRepository.save(rescheduled);
   }

   @Transactional
   @Override public long deletePendingTasksByKeywords(long userId, String term) {
      long count = jobIndexerTaskRepository.countByUserIdAndTaskStateAndKeywords(userId, TaskState.Pending, term);

      jobIndexerTaskRepository.deleteByUserIdAndTaskStateAndKeywords(userId, TaskState.Pending, term);

      return count;
   }

   @Override public List<JobIndexerTask> newTasks(List<String> terms, long startTime, long endTime, String country, long userId) {
      final long oneDayInMilliseconds = 24 * 3600000L;
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

      long now = new Date().getTime();

      List<JobIndexerTask> tasks = new ArrayList<>();
      for(long time = startTime; time < endTime; time+=oneDayInMilliseconds) {
         String postedDay = dateFormat.format(new Date(time));

         for(String term : terms) {

            if(jobIndexerTaskRepository.countByPostedDateAndKeywordsAndCountry(postedDay, term, country) > 0) {
               continue;
            }

            JobIndexerTask task = new JobIndexerTask();
            task.setCountry(country);
            task.setCreatedTime(now);
            task.setStartTime(0L);
            task.setEndTime(0L);
            task.setLocked(false);
            task.setUserId(userId);
            task.setTaskState(TaskState.Pending);

            task.setKeywords(term);
            task.setPostedDate(postedDay);
            task.setTaskUUID(UUID.randomUUID().toString());
            tasks.add(task);
         }
      }

      logger.info("inserted tasks: {}", tasks.size());

      return jobIndexerTaskRepository.save(tasks);
   }


   @Override public JobIndexerTaskPage findWIPTasks(long userId, int pageIndex, int pageSize) {
      final TaskState state = TaskState.WIP;
      Page<JobIndexerTask> page = jobIndexerTaskRepository.findByUserIdAndTaskStateOrderByCreatedTimeAsc(userId, state, new PageRequest(pageIndex, pageSize));

      return new JobIndexerTaskPage(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
   }

   @Override public List<JobIndexerTask> findWIPTasks() {
      final TaskState state = TaskState.WIP;
      return jobIndexerTaskRepository.findByTaskState(state);
   }

   @Override public JobIndexerTaskPage findPendingTasks(long userId, int pageIndex, int pageSize) {
      final TaskState state = TaskState.Pending;
      Page<JobIndexerTask> page = jobIndexerTaskRepository.findByUserIdAndTaskStateOrderByCreatedTimeAsc(userId, state, new PageRequest(pageIndex, pageSize));

      return new JobIndexerTaskPage(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
   }


   @Override public JobIndexerTaskPage findUnresolvedTasks(long userId, int pageIndex, int pageSize) {
      final TaskState stateNot = TaskState.Resolved;
      Page<JobIndexerTask> page = jobIndexerTaskRepository.findByUserIdAndTaskStateNotOrderByCreatedTimeAsc(userId, stateNot, new PageRequest(pageIndex, pageSize));

      return new JobIndexerTaskPage(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
   }

   @Override public JobIndexerTaskPage findResolvedTasks(long userId, int pageIndex, int pageSize) {
      final TaskState state = TaskState.Resolved;
      Page<JobIndexerTask> page = jobIndexerTaskRepository.findByUserIdAndTaskStateOrderByCreatedTimeAsc(userId, state, new PageRequest(pageIndex, pageSize));

      return new JobIndexerTaskPage(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
   }

   @Override public JobIndexerTaskPage findAllTasks(long userId, int pageIndex, int pageSize) {

      Page<JobIndexerTask> page = jobIndexerTaskRepository.findByUserIdOrderByCreatedTimeAsc(userId, new PageRequest(pageIndex, pageSize));

      return new JobIndexerTaskPage(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
   }


   @Override public Optional<JobIndexerTask> findTaskById(long taskId) {
      JobIndexerTask task = jobIndexerTaskRepository.findOne(taskId);
      if(task == null) {
         return Optional.empty();
      } else {
         return Optional.of(task);
      }
   }


   @Override public Optional<JobIndexerTask> updatePendingTask(long taskId, String keyword, String country) {
      JobIndexerTask task = jobIndexerTaskRepository.findOne(taskId);

      if(task == null) {
         return Optional.empty();
      } else {
         task.setCountry(country);
         task.setKeywords(keyword);
         task = jobIndexerTaskRepository.save(task);
         return Optional.of(task);
      }
   }


   @Override public Optional<JobIndexerTask> deleteTaskById(long taskId) {
      JobIndexerTask task = jobIndexerTaskRepository.findOne(taskId);

      if(task == null) {
         return Optional.empty();
      } else {
         jobIndexerTaskRepository.delete(taskId);
         return Optional.of(task);
      }
   }
}
