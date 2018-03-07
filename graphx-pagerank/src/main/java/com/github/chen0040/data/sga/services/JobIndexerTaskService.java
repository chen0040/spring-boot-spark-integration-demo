package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.sga.models.JobIndexerTask;
import com.github.chen0040.data.sga.viewmodels.JobIndexerTaskPage;

import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 12/27/16.
 */
public interface JobIndexerTaskService {

   Optional<JobIndexerTask> getPendingTask();

   JobIndexerTask markAsStarted(JobIndexerTask task);

   JobIndexerTask markAsCompleted(JobIndexerTask task);

   List<JobIndexerTask> newTasks(List<String> terms, long startTime, long endTime, String country, long userId);

   JobIndexerTaskPage findWIPTasks(long userId, int pageIndex, int pageSize);

   JobIndexerTaskPage findPendingTasks(long userId, int pageIndex, int pageSize);

   JobIndexerTaskPage findUnresolvedTasks(long userId, int pageIndex, int pageSize);

   Optional<JobIndexerTask> markAsCompleted(long taskId);

   JobIndexerTaskPage findResolvedTasks(long userId, int pageIndex, int pageSize);

   JobIndexerTaskPage findAllTasks(long userId, int pageIndex, int pageSize);

   Optional<JobIndexerTask> findTaskById(long taskId);

   Optional<JobIndexerTask> updatePendingTask(long taskId, String keyword, String country);

   Optional<JobIndexerTask> deleteTaskById(long taskId);

   Optional<JobIndexerTask> markAsPending(long taskId);

   List<JobIndexerTask> findWIPTasks();

   void markAsPending(List<JobIndexerTask> rescheduled);

   long deletePendingTasksByKeywords(long userId, String term);
}
