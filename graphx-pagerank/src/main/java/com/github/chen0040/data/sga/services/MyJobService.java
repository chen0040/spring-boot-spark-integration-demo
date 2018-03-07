package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.JobContract;
import com.github.chen0040.data.sga.models.MyJob;
import com.github.chen0040.data.sga.models.Company;
import com.github.chen0040.data.sga.models.JobSkill;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Created by xschen on 28/9/2016.
 */
public interface MyJobService {
   MyJob saveJob(MyJob job);

   MyJob saveCrawledJob(JobContract jobContract);

   JobSkill saveJobSkills(JobSkill line);

   Optional<MyJob> findByJobId(Long jobId);
   Long countByRecordId(String recordId);
   MyJob findByRecordId(String recordId);
   JobSkill findJobSkillById(Long jobLineId);

   Page<MyJob> findAllByCompany(Company company, int pageIndex, int pageSize);

   List<MyJob> findAllBetweenDate(Company company, LocalDateTime startDate, LocalDateTime endDate);

   Page<MyJob> findAllJobs(int pageIndex, int pageSize, String sortingField, String sortingDir, Map<String, String> search);

   Page<MyJob> findAllResumes(int pageIndex, int pageSize, String sortingField, String sortingDir, Map<String, String> search);

   Page<MyJob> findByProducerContaining(String tag, int pageIndex, int pageSize);


   void removeJobSkillById(Long jobLineId);

   void deleteSkills(MyJob job);
   Optional<MyJob> deleteJob(long jobId);

   Optional<MyJob> findJobByRecordId(String recordId);

   List<MyJob> findAllDistinctJobTitle();

   List<MyJob> findAllJobs();

   boolean processJob(JobContract jobContract);

   boolean processJob(JobContract jobContract, String keywords);

   boolean processResume(JobContract jobContract);

}
