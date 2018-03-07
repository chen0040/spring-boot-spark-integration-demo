package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.sga.models.MyJob;
import com.github.chen0040.data.sga.models.JobInsight;
import com.github.chen0040.data.sga.models.SkillAssocEntity;
import com.github.chen0040.data.sga.repositories.JobInsightRepository;
import com.github.chen0040.data.sga.repositories.SkillAssocEntityRepository;
import com.github.chen0040.lang.commons.utils.CollectionUtil;
import com.github.chen0040.lang.commons.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by xschen on 16/10/2016.
 */
@Service
public class JobInsightServiceImpl implements JobInsightService {
   @Autowired
   private JobInsightRepository jobInsightRepository;

   @Autowired
   private SkillAssocEntityRepository skillAssocEntityRepository;

   private static final Logger logger = LoggerFactory.getLogger(JobInsightServiceImpl.class);

   @Override public JobInsight learn(MyJob job, String keywords) {
      String recordId = job.getRecordId();

      if(StringUtils.isEmpty(recordId)){
         return new JobInsight().makeInvalid();
      }

      JobInsight existingModel = jobInsightRepository.findFirstByRecordId(recordId);
      if(existingModel != null){
         return existingModel.makeInvalid();
      }

      List<String> list = job.getSkills().stream().map(jobSkill -> jobSkill.getSkill().getName()).collect(Collectors.toList());
      list.sort(String::compareTo);

      if(list.isEmpty()) {
         if (!StringUtils.isEmpty(keywords)) {
            list.add(keywords);
         } else {
            list.add("Fast Learner");
         }
      }

      list = CollectionUtil.truncate(list, 8);

      String skills = list.stream().collect(Collectors.joining(", "));

      String companyName = job.getCompanyName();
      String jobTitle = job.getJobTitle();

      LocalDateTime date = job.getDate();

      if(skills.length() > 255) {
         skills = skills.substring(255);
         int index = skills.lastIndexOf(",");
         if(index != -1) {
            skills = skills.substring(0, index);
         }
      }

      JobInsight model = new JobInsight();
      model.setSkills(skills);
      model.setCompanyName(companyName);
      model.setJobTitle(jobTitle);
      model.setRecordId(recordId);
      model.setCountry(job.getCountry());
      model.buildDate(date);


      logger.info("Generate skill combination ({}) ...", list.size());
      List<List<String>> listset = CollectionUtil.generateCombinations(list);

      logger.info("generate skill association ...");
      List<SkillAssocEntity> assocs = new ArrayList<>();
      for(int i=0; i < listset.size(); ++i){
         List<String> l = listset.get(i);
         l.sort(String::compareTo);

         String ls = l.stream().collect(Collectors.joining(", "));
         SkillAssocEntity assoc = new SkillAssocEntity();
         assoc.setSkills(ls);
         assoc.setCompanyName(companyName);
         assoc.setJobTitle(jobTitle);
         assoc.setCountry(job.getCountry());
         assoc.setGroupSize(l.size());
         assoc.setMonth(model.getMonth());
         assoc.setYear(model.getYear());
         assoc.setWeek(model.getWeek());

         assocs.add(assoc);
      }
      logger.info("skill association generated: {}", assocs.size());

      skillAssocEntityRepository.save(assocs);

      logger.info("save learning insight model");


      return jobInsightRepository.save(model);
   }


   @Override public List<List<String>> findAllAssociatedSkills() {
      List<List<String>> result = new ArrayList<>();

      for(JobInsight jobInsight : jobInsightRepository.findAll()){

         String[] skills = jobInsight.getSkills().split(",");
         List<String> associatedSkills = new ArrayList<>();
         for(int k=0; k < skills.length; ++k){
            associatedSkills.add(skills[k].trim());
         }
         result.add(associatedSkills);
      }

      return result;
   }


   @Override public List<JobInsight> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {

      return jobInsightRepository.findByCreateTimeBetween(startTime, endTime);
   }


   @Override public int countPagesByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime, int pageSize) {
      long count = jobInsightRepository.countByCreateTimeBetween(startTime, endTime);
      return (int)Math.ceil((double)count / pageSize);

   }


   @Override public Page<JobInsight> findPageByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime, int pageIndex, int pageSize) {
      return jobInsightRepository.findByCreateTimeBetween(startTime, endTime, new PageRequest(pageIndex, pageSize));
   }


   @Override public long countByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
      return jobInsightRepository.countByCreateTimeBetween(startTime, endTime);
   }


   @Override public long countJobInsights() {
      return jobInsightRepository.count();
   }


   @Override public List<JobInsight> findAllJobInsights() {
      List<JobInsight> result = new ArrayList<>();
      for(JobInsight insight : jobInsightRepository.findAll()){
         result.add(insight);
      }
      return result;
   }


   @Override public JobInsight save(JobInsight jobInsight) {
      return jobInsightRepository.save(jobInsight);
   }


   @Override public Optional<JobInsight> findJobInsightByRecordId(String recordId) {
      JobInsight insight = jobInsightRepository.findFirstByRecordId(recordId);
      if(insight == null) {
         return Optional.empty();
      } else {
         return Optional.of(insight);
      }
   }


   @Override
   public Page<JobInsight> findInsightsByAssociatedSkills(List<String> skills, int pageIndex, int pageSize, String sortingField, String sortingDir) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)){
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(skills.size() == 1) {
         return jobInsightRepository.findBySkillsContaining(skills.get(0), request);
      } else if(skills.size() == 2){
         return jobInsightRepository.findBySkillsContainingAndSkillsContaining(skills.get(0), skills.get(1), request);
      } else if(skills.size() == 3) {
         return jobInsightRepository.findBySkillsContainingAndSkillsContainingAndSkillsContaining(skills.get(0), skills.get(1), skills.get(2), request);
      } else {
         return jobInsightRepository.findBySkillsContainingAndSkillsContainingAndSkillsContainingAndSkillsContaining(skills.get(0), skills.get(1), skills.get(2), skills.get(3), request);
      }
   }


   @Override
   public Page<JobInsight> findInsightsByCompanyName(String companyName, int pageIndex, int pageSize, String sortingField, String sortingDir) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)){
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      return jobInsightRepository.findByCompanyName(companyName, request);
   }


   @Override public Page<JobInsight> findPagedJobInsights(int pageIndex, int pageSize) {
      return jobInsightRepository.findAll(new PageRequest(pageIndex, pageSize));
   }


   @Override public boolean deleteByRecordId(String recordId) {
      jobInsightRepository.deleteByRecordId(recordId);
      return true;
   }
}
