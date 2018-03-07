package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.commons.enums.TaskState;
import com.github.chen0040.data.sga.models.JobIndexerTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by xschen on 12/27/16.
 */
@Repository
public interface JobIndexerTaskRepository  extends JpaRepository<JobIndexerTask, Long> {
   List<JobIndexerTask> findFirstByLockedAndEndTimeOrderByCreatedTimeAsc(boolean locked, long endTime);

   Page<JobIndexerTask> findByUserIdAndLockedAndEndTimeOrderByCreatedTimeAsc(long userId, boolean locked, long endTime, Pageable pageable);

   Page<JobIndexerTask> findByUserIdAndEndTimeOrderByCreatedTimeAsc(long userId, long endTime, Pageable pageable);

   Page<JobIndexerTask> findByUserIdAndEndTimeNotOrderByCreatedTimeAsc(long userId, long endTime, Pageable pageable);

   List<JobIndexerTask> findFirstByTaskStateOrderByCreatedTimeAsc(TaskState state);

   Page<JobIndexerTask> findByUserIdAndTaskStateNotOrderByCreatedTimeAsc(long userId, TaskState stateNot, Pageable pageable);

   Page<JobIndexerTask> findByUserIdAndTaskStateOrderByCreatedTimeAsc(long userId, TaskState state, Pageable pageable);

   Page<JobIndexerTask> findByUserIdOrderByCreatedTimeAsc(long userId, Pageable pageable);

   Long countByPostedDateAndKeywordsAndCountry(String postedDay, String keywords, String country);

   List<JobIndexerTask> findByTaskState(TaskState state);

   void deleteByTaskStateAndKeywords(TaskState pending, String term);
   void deleteByUserIdAndTaskStateAndKeywords(long userId, TaskState pending, String term);

   long countByTaskStateAndKeywords(TaskState pending, String term);
   long countByUserIdAndTaskStateAndKeywords(long userId, TaskState pending, String term);
}
