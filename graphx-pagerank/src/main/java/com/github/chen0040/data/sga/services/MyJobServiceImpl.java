package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.JobContract;
import com.github.chen0040.data.sga.models.*;
import com.github.chen0040.data.sga.repositories.MyJobRepository;
import com.github.chen0040.data.sga.repositories.MySalaryEntityRepository;
import com.github.chen0040.data.sga.repositories.JobSkillRepository;
import com.github.chen0040.data.sga.specs.SearchCriteria;
import com.github.chen0040.data.sga.specs.SpecificationBuilder;
import com.github.chen0040.lang.commons.utils.StringUtils;
import com.github.chen0040.lang.commons.utils.UrlTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Created by xschen on 28/9/2016.
 */
@Service
public class MyJobServiceImpl implements MyJobService {

   private static final Logger logger = LoggerFactory.getLogger(MyJobServiceImpl.class);

   @Autowired
   private MyJobRepository myJobRepository;

   @Autowired
   private CompanyService companyService;

   @Autowired
   private JobSkillRepository jobSkillRepository;

   @Autowired
   private JobInsightService jobInsightService;

   @Autowired
   private MySalaryEntityRepository salaryEntityRepository;

   @Autowired
   private SkillService skillService;


   @Transactional
   public MyJob saveJob(MyJob job)
   {
      final String recordId = job.getRecordId();

      if(StringUtils.isEmpty(recordId)){
         return job.makeInvalid();
      }

      Optional<MyJob> jobOptional = findJobByRecordId(recordId);
      if(jobOptional.isPresent()){
         MyJob existingJob = jobOptional.get();
         return existingJob;
      }

      job = myJobRepository.save(job);
      return job;
   }


   public Optional<MyJob> findJobByRecordId(String recordId) {
      MyJob job = myJobRepository.findFirstByRecordId(recordId);
      if(job == null) {
         return Optional.empty();
      }
      return Optional.of(job);
   }

   @Override
   public List<MyJob> findAllDistinctJobTitle() {
      return myJobRepository.findDistinctByJobTitle();
   }


   @Transactional
   public JobSkill saveJobSkills(JobSkill line)
   {
      return jobSkillRepository.save(line);
   }

   public Page<MyJob> findAllByCompany(Company company, int pageIndex, int pageSize)
   {
      Sort sort = new Sort(Sort.Direction.DESC, "createTime");
      Pageable pageable = new PageRequest(pageIndex, pageSize, sort);

      return myJobRepository.findByCompany(company, pageable);
   }

   public List<MyJob> findAllBetweenDate(Company company, LocalDateTime startDate, LocalDateTime endDate)
   {
      return myJobRepository.findByCompanyAndDateBetween(company, startDate, endDate);
   }




   public Page<MyJob> findAll(int pageIndex, int pageSize, String sortingField, String sortingDir, Map<String, String> search) {
      Pageable pageable = new PageRequest(pageIndex, pageSize);
      if(!StringUtils.isEmpty(sortingField)){
         pageable = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(search.isEmpty()) {
         return myJobRepository.findAll(pageable);
      } else {
         List<SearchCriteria> criteria = new ArrayList<>();
         for(Map.Entry<String, String> entry : search.entrySet()){
            criteria.add(new SearchCriteria(entry.getKey(), ":", entry.getValue()));
         }

         return myJobRepository.findAll(new SpecificationBuilder<MyJob>(criteria).build(), pageable);
      }
   }

   @Override public Page<MyJob> findAllJobs(int pageIndex, int pageSize, String sortingField, String sortingDir, Map<String, String> search) {
      Pageable pageable = new PageRequest(pageIndex, pageSize);
      if(!StringUtils.isEmpty(sortingField)){
         pageable = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      List<SearchCriteria> criteria = new ArrayList<>();
      for(Map.Entry<String, String> entry : search.entrySet()){
         criteria.add(new SearchCriteria(entry.getKey(), ":", entry.getValue()));
      }

      criteria.add(new SearchCriteria("producer", "!:", "RESUME"));

      return myJobRepository.findAll(new SpecificationBuilder<MyJob>(criteria).build(), pageable);
   }

   @Override public Page<MyJob> findAllResumes(int pageIndex, int pageSize, String sortingField, String sortingDir, Map<String, String> search) {
      Pageable pageable = new PageRequest(pageIndex, pageSize);
      if(!StringUtils.isEmpty(sortingField)){
         pageable = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      List<SearchCriteria> criteria = new ArrayList<>();
      for(Map.Entry<String, String> entry : search.entrySet()){
         criteria.add(new SearchCriteria(entry.getKey(), ":", entry.getValue()));
      }
      criteria.add(new SearchCriteria("producer", ":", "RESUME"));

      return myJobRepository.findAll(new SpecificationBuilder<MyJob>(criteria).build(), pageable);
   }


   @Override public Page<MyJob> findByProducerContaining(String tag, int pageIndex, int pageSize) {
      return myJobRepository.findByProducerContaining(tag, new PageRequest(pageIndex, pageSize));
   }


   public Optional<MyJob> findByJobId(Long jobId) {
      MyJob job = myJobRepository.findOne(jobId);
      if(job == null){
         return Optional.empty();
      }
      return Optional.of(job);
   }

   public JobSkill findJobSkillById(Long jobSkillId)
   {
      return jobSkillRepository.findOne(jobSkillId);
   }

   @Transactional
   public void removeJobSkillById(Long jobSkillId)
   {
      jobSkillRepository.delete(jobSkillId);
   }

   @Transactional
   public void deleteSkills(MyJob job){
      jobSkillRepository.deleteByJobId(job.getId());
   }

   @Transactional
   public Optional<MyJob> deleteJob(long jobId) {
      Optional<MyJob> optional = findByJobId(jobId);
      if(optional.isPresent()) {
         myJobRepository.delete(jobId);
         String recordId = optional.get().getRecordId();
         jobInsightService.deleteByRecordId(recordId);
         salaryEntityRepository.delete(recordId);
      }
      return optional;
   }

   public MyJob findByRecordId(String recordId) {
      return myJobRepository.findFirstByRecordId(recordId);
   }

   public Long countByRecordId(String recordId){
      return myJobRepository.countByRecordId(recordId);
   }



   @Transactional
   public MyJob saveCrawledJob(JobContract jobContract) {
      MyJob job = new MyJob(jobContract);
      String recordId = job.getRecordId();

      if(StringUtils.isEmpty(recordId)){
         return job.makeInvalid();
      }

      MyJob existingJob = myJobRepository.findFirstByRecordId(recordId);

      if(existingJob != null){
         return existingJob.makeInvalid();
      } else {
         if(!job.hasTag("RESUME")) {
            String companyName = job.getCompanyName();

            Optional<Company> companyOptional = companyService.findByCompanyName(companyName);

            if (companyOptional.isPresent()) {
               job.setCompany(companyOptional.get());
            }
            else {
               String link = UrlTools.getHost(job.getUrl());
               job.setCompany(companyService.saveCompanyByName(companyName, link));
            }
         }

         Optional<Skill> skillOptional = skillService.findByName(jobContract.indexerQuery().getKeywords());

         if(skillOptional.isPresent()) {
            Skill skill = skillOptional.get();
            JobSkill jobSkill = new JobSkill(job, skill, job.getRecordId() + "::" + skill.getName());
            job.getSkills().add(jobSkill);
         }

         List<Skill> skills = skillService.findAll();
         String snippet = StringUtils.truncate(job.getSnippet().toLowerCase(), MyJob.MAX_SNIPPET_SIZE);
         for(Skill skill2 : skills) {
            if(skillOptional.isPresent() && skillOptional.get().getName().equals(skill2.getName())) continue;

            String keywords = skill2.getName().toLowerCase();
            if(keywords.length() > 1) {
               if(StringUtils.containsWholeWord(snippet, keywords)) {
                  JobSkill jobSkill2 = new JobSkill(job, skill2, job.getRecordId() + "::" + skill2.getName());
                  job.getSkills().add(jobSkill2);
               }
            }
         }
      }

      job = myJobRepository.save(job);
      return job;
   }



   @Override public List<MyJob> findAllJobs() {
      List<MyJob> jobs = new ArrayList<>();
      for(MyJob job: myJobRepository.findAll()){
         jobs.add(job);
      }
      return jobs;
   }

   @Override public boolean processJob(JobContract jobContract){
      return processJob(jobContract, null);
   }

   @Override public boolean processJob(JobContract jobContract, String keywords) {
      MyJob job = saveCrawledJob(jobContract);

      if (job.isValid()) {
         if(!job.hasTag("RESUME")) { // skip resume
            logger.info("saved job {}({}, {}, {})", jobContract.getJobTitle(), jobContract.getCompanyName(), jobContract.getRecordId(), jobContract.getDate());

            JobInsight insight = jobInsightService.learn(job, keywords);

            if (insight.isValid()) {
               logger.info("job.insight {} is valid", insight.getSkills());
            }
            else {
               logger.info("job.insight {} is not valid", insight.getSkills());
            }
         }
         return true;
      }
      else {
         logger.info("duplicated job {}({}, {}) is not saved", jobContract.getJobTitle(), jobContract.getRecordId(), jobContract.getDate());
         return false;
      }
   }


   @Override public boolean processResume(JobContract jobContract) {
      jobContract.addTag("RESUME");
      return processJob(jobContract);
   }

}
