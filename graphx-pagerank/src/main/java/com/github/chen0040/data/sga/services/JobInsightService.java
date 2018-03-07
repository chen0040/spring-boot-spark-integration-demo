package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.sga.models.MyJob;
import com.github.chen0040.data.sga.models.JobInsight;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 16/10/2016.
 */
public interface JobInsightService {
   JobInsight learn(MyJob job, String keywords);
   List<List<String>> findAllAssociatedSkills();

   List<JobInsight> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
   int countPagesByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime, int pageSize);
   Page<JobInsight> findPageByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime, int startIndex, int pageSize);

   long countByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

   long countJobInsights();

   List<JobInsight> findAllJobInsights();

   JobInsight save(JobInsight jobInsight);

   Optional<JobInsight> findJobInsightByRecordId(String recordId);

   Page<JobInsight> findInsightsByAssociatedSkills(List<String> skills, int pageIndex, int pageSize, String sortingField, String sortingDir);

   Page<JobInsight> findInsightsByCompanyName(String companyName, int pageIndex, int pageSize, String sortingField, String sortingDir);

   Page<JobInsight> findPagedJobInsights(int pageIndex, int pageSize);

   boolean deleteByRecordId(String recordId);
}
