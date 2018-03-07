package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.MySalaryContract;
import com.github.chen0040.data.commons.utils.RecordIdUtil;
import com.github.chen0040.data.commons.utils.SalaryParser;
import com.github.chen0040.data.sga.models.MyJob;
import com.github.chen0040.data.sga.models.MySalaryEntity;
import com.github.chen0040.data.sga.models.JobInsight;
import com.github.chen0040.data.sga.repositories.MySalaryEntityRepository;
import com.github.chen0040.lang.commons.utils.CollectionUtil;
import com.github.chen0040.lang.commons.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by xschen on 2/1/2017.
 */
@Service
public class MySalaryServiceImpl implements MySalaryService {

   @Autowired
   private MySalaryEntityRepository mySalaryEntityRepository;

   private static final Logger logger = LoggerFactory.getLogger(MySalaryServiceImpl.class);


   @Transactional
   @Override public Optional<MySalaryEntity> deleteMySalary(String recordId) {
      MySalaryEntity skillAssocEntity = mySalaryEntityRepository.findOne(recordId);
      if(skillAssocEntity == null) {
         return Optional.empty();
      } else {
         mySalaryEntityRepository.delete(recordId);
         return Optional.of(skillAssocEntity);
      }
   }

   @Transactional
   @Override
   public Optional<MySalaryEntity> scanJob(MyJob job, JobInsight jobInsight) {

      String recordId = job.getRecordId();

      MySalaryEntity existing = mySalaryEntityRepository.findOne(recordId);

      if(existing != null) {
         return Optional.empty();
      }

      String skills = jobInsight.getSkills();
      List<String> list = CollectionUtil.toList(skills.split(",")).stream().map(String::trim).collect(Collectors.toList());

      String companyName = job.getCompanyName();
      String jobTitle = job.getJobTitle();

      MySalaryEntity salary = new MySalaryEntity();
      salary.setSkills(skills);
      salary.setCompanyName(companyName);
      salary.setJobTitle(jobTitle);
      salary.setCountry(job.getCountry());
      salary.setGroupSize(list.size());
      salary.setMonth(jobInsight.getMonth());
      salary.setYear(jobInsight.getYear());
      salary.setWeek(jobInsight.getWeek());
      salary.setYearWeek(jobInsight.getYear() * 100 + jobInsight.getWeek());
      salary.setRecordId(recordId);
      salary.setCount(1);

      boolean validSalary = false;
      List<Double> salaryAmounts = SalaryParser.parseSalary(job.getSnippet());
      if(salaryAmounts.size()==1) {
         validSalary =true;
         double salaryAmount = salaryAmounts.get(0);
         salary.setSalaryLowerBound(salaryAmount);
         salary.setSalaryUpperBound(salaryAmount);
      } else if(!salaryAmounts.isEmpty()) {
         validSalary =true;
         salary.setSalaryLowerBound(salaryAmounts.get(0));
         salary.setSalaryUpperBound(salaryAmounts.get(1));
      }


      if(validSalary) {
         logger.info("Saving salary: {} ({} - {}) (Company: {})", recordId, salary.getSalaryLowerBound(), salary.getSalaryUpperBound(), companyName);
         //logger.info("salary: {} - {}", salary.getSalaryLowerBound(), salary.getSalaryUpperBound());
         mySalaryEntityRepository.save(salary);

         try {
            Thread.sleep(10L);
         }catch(InterruptedException exception) {
            logger.error("sleep interrupted");
         }

         List<List<String>> listset = CollectionUtil.generateCombinations(list);

         for(int i=0; i < listset.size(); ++i){
            List<String> l = listset.get(i);
            l.sort(String::compareTo);

            String ls = l.stream().collect(Collectors.joining(", "));

            getOrCreate(jobInsight.getYear(), jobInsight.getMonth(), salary.getCountry(), ls, l.size(), "", salary);
           
            getOrCreate(jobInsight.getYear(), jobInsight.getMonth(), "", ls, l.size(), "", salary);
           
            getOrCreate(jobInsight.getYear(), jobInsight.getMonth(), salary.getCountry(), "", 0, salary.getCompanyName(), salary);
           
            getOrCreate(jobInsight.getYear(), jobInsight.getMonth(), "", "", 0, salary.getCompanyName(), salary);
           

            getOrCreate(jobInsight.getYear(), -1, salary.getCountry(), ls, l.size(), "", salary);
           
            getOrCreate(jobInsight.getYear(), -1, "", ls, l.size(), "", salary);
           
            getOrCreate(jobInsight.getYear(), -1, salary.getCountry(), "", 0, salary.getCompanyName(), salary);
           
            getOrCreate(jobInsight.getYear(), -1, "", "", 0, salary.getCompanyName(), salary);


            getOrCreate(-1, -1, "", ls, l.size(), "", salary);
            getOrCreate(-1, -1, "", "", 0, salary.getCompanyName(), salary);

            getOrCreate(-1, -1, salary.getCountry(), ls, l.size(), "", salary);
            getOrCreate(-1, -1, salary.getCountry(), "", 0, salary.getCompanyName(), salary);
         }
         
         return Optional.of(salary);

      } else {
         return Optional.empty();
      }
   }



   private MySalaryEntity getOrCreate(int year, int month, String country, String skills, int skillCount, String companyName, MySalaryEntity salary) {

      if(StringUtils.isEmpty(skills) && StringUtils.isEmpty(companyName)) {
         return null;
      }

      String recordId2 = RecordIdUtil.createRecordId(year, month, country, skills, companyName);

      MySalaryEntity entity2 = mySalaryEntityRepository.findOne(recordId2);

      if(entity2 != null) {
         long count = Math.min(100000, entity2.getCount());
         double salaryLowerBound = (entity2.getSalaryLowerBound() * count + salary.getSalaryLowerBound()) / (count+1);
         double salaryUpperBound = (entity2.getSalaryUpperBound() * count + salary.getSalaryUpperBound()) / (count+1);
         count = count+1;

         entity2.setCount(count);
         entity2.setSalaryLowerBound(salaryLowerBound);
         entity2.setSalaryUpperBound(salaryUpperBound);


      } else {
         entity2 = new MySalaryEntity();
         entity2.setSkills(skills);
         entity2.setRecordId(recordId2);

         entity2.setCompanyName(companyName);
         entity2.setJobTitle("");
         entity2.setCountry(country);
         entity2.setGroupSize(skillCount);
         entity2.setMonth(month);
         entity2.setYear(year);
         entity2.setWeek(-1);
         entity2.setSalaryLowerBound(salary.getSalaryLowerBound());
         entity2.setSalaryUpperBound(salary.getSalaryUpperBound());
         entity2.setCount(1);
      }

      mySalaryEntityRepository.save(entity2);

      try {
         Thread.sleep(10L);
      }catch(InterruptedException exception) {
         logger.error("sleep interrupted");
      }

      return entity2;
   }



   @Override public Page<MySalaryEntity> findAll(int pageIndex, int pageSize, String sortingField, String sortingDir) {

      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }
      return mySalaryEntityRepository.findAll(request);
   }


   @Transactional
   @Override public void deleteMySalarys(List<MySalaryEntity> content) {
      mySalaryEntityRepository.delete(content);
   }


   @Override public Slice<MySalaryEntity> findLowLevelMySalary(int pageIndex, int pageSize) {

      final String jobTitleNot = "";
      return mySalaryEntityRepository.findByJobTitleNot(jobTitleNot, new PageRequest(pageIndex, pageSize));
   }


   @Transactional
   @Override public int accumulateHighLevelAssoc(MySalaryEntity sa) { // temporary fix: more costly but should fix the errors in the current assoc skills data-table
      final String skills = sa.getSkills();
      final int year = sa.getYear();
      final int month = sa.getMonth();
      final int week = sa.getWeek();
      final String country = sa.getCountry();
      if(sa.getGroupSize() > 3) {
         return 0;
      }

      int count = 0;


      MySalaryEntity
              aggregator_by_month = mySalaryEntityRepository.findFirstBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(skills, year, month, -1, country, "");

      if(aggregator_by_month == null) {
         aggregator_by_month = new MySalaryEntity();
         aggregator_by_month.setCompanyName("");
         aggregator_by_month.setJobTitle("");
         aggregator_by_month.setCountry(country);
         aggregator_by_month.setCount(1L);
         aggregator_by_month.setSkills(skills);
         aggregator_by_month.setYear(year);
         aggregator_by_month.setGroupSize(sa.getGroupSize());
         aggregator_by_month.setMonth(month);
         aggregator_by_month.setWeek(-1);
         count++;
      } else {
         aggregator_by_month.setCount(aggregator_by_month.getCount()+1);
      }



      mySalaryEntityRepository.save(aggregator_by_month);

      MySalaryEntity
              aggregator_by_year = mySalaryEntityRepository.findFirstBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(skills, year, -1, -1, country, "");

      if(aggregator_by_year == null) {
         aggregator_by_year = new MySalaryEntity();
         aggregator_by_year.setCompanyName("");
         aggregator_by_year.setJobTitle("");
         aggregator_by_year.setCountry(country);
         aggregator_by_year.setCount(1L);
         aggregator_by_year.setSkills(skills);
         aggregator_by_year.setYear(year);
         aggregator_by_year.setGroupSize(sa.getGroupSize());
         aggregator_by_year.setMonth(-1);
         aggregator_by_year.setWeek(-1);
         count++;
      } else {
         aggregator_by_year.setCount(aggregator_by_year.getCount()+1);
      }

      mySalaryEntityRepository.save(aggregator_by_year);

      return count;
   }

   @Transactional
   @Override
   public int aggregateHighLevelAssoc(MySalaryEntity sa){
      final String skills = sa.getSkills();
      final int year = sa.getYear();
      final int month = sa.getMonth();
      final int week = sa.getWeek();
      final String country = sa.getCountry();
      if(sa.getGroupSize() > 3) {
         return 0;
      }

      Slice<MySalaryEntity>
              page = mySalaryEntityRepository.findBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(skills, year, month, -1, country, "", new PageRequest(0, 50));

      List<MySalaryEntity> aggregators_by_month = page.getContent();

      boolean aggregated = false;


      MySalaryEntity aggregator_by_month;
      if(aggregators_by_month.size() > 1){
         logger.info("aggregate {} results by month", aggregators_by_month.size());

         aggregator_by_month = new MySalaryEntity();
         aggregator_by_month.setCompanyName("");
         aggregator_by_month.setJobTitle("");
         aggregator_by_month.setCountry(country);
         aggregator_by_month.setSkills(skills);
         aggregator_by_month.setYear(year);
         aggregator_by_month.setGroupSize(sa.getGroupSize());
         aggregator_by_month.setMonth(month);
         aggregator_by_month.setWeek(-1);

         long count = aggregators_by_month.stream().map(sae -> sae.getCount() == 0L ? 1 : sae.getCount()).reduce((a, b) -> a + b).orElse(0L);
         aggregator_by_month.setCount(count);

         mySalaryEntityRepository.delete(aggregators_by_month);

         mySalaryEntityRepository.save(aggregator_by_month);

         aggregated = true;
      }

      page = mySalaryEntityRepository.findBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(skills, year, -1, -1, country, "", new PageRequest(0, 50));

      List<MySalaryEntity> aggregators_by_year = page.getContent();

      MySalaryEntity aggregator_by_year;
      if(aggregators_by_year.size() > 1) {
         logger.info("aggregate {} results by year", aggregators_by_year.size());

         aggregator_by_year = new MySalaryEntity();
         aggregator_by_year.setCompanyName("");
         aggregator_by_year.setJobTitle("");
         aggregator_by_year.setCountry(country);
         aggregator_by_year.setSkills(skills);
         aggregator_by_year.setYear(year);
         aggregator_by_year.setGroupSize(sa.getGroupSize());
         aggregator_by_year.setMonth(-1);
         aggregator_by_year.setWeek(-1);

         long count = aggregators_by_year.stream().map(sae -> sae.getCount() == 0L ? 1 : sae.getCount()).reduce((a, b) -> a + b).orElse(0L);
         aggregator_by_year.setCount(count);

         mySalaryEntityRepository.delete(aggregators_by_year);

         mySalaryEntityRepository.save(aggregator_by_year);


         aggregated = true;


      }

      return aggregated ? 1 : 0;
   }

   @Override public Page<MySalaryEntity> findYearlyHighLevelMySalarys(int pageIndex, int pageSize, String sortingField, String sortingDir) {

      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      return mySalaryEntityRepository.findByMonthAndJobTitle(-1, "", request);

   }

   @Override public Page<MySalaryEntity> findYearlyHighLevelMySalarysSorted(int pageIndex, int pageSize, String sortingField, String sortingDir) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      return mySalaryEntityRepository.findByMonthAndJobTitleOrderByCountDescYearDesc(-1, "", request);
   }

   @Override public Page<MySalaryEntity> findMonthlyHighLevelMySalarysSorted(int pageIndex, int pageSize, String sortingField, String sortingDir) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      return mySalaryEntityRepository.findByMonthNotAndJobTitleOrderByCountDescYearDescMonthDesc(-1, "", request);
   }

   @Override public Page<MySalaryEntity> findMonthlyHighLevelMySalarys(int pageIndex, int pageSize, String sortingField, String sortingDir) {

      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      Page<MySalaryEntity> page = mySalaryEntityRepository.findByMonthNotAndJobTitle(-1, "", request);

      return page;
   }

   @Transactional
   @Override public void deleteMySalarysBy(int year, int month, int week, int groupSize, long count) {
      mySalaryEntityRepository.deleteByYearAndMonthAndWeekAndGroupSizeAndCount(year, month, week, groupSize, count);
   }

   @Transactional
   @Override public void deleteMySalarysBy(int year, int month, int week, int groupSize, long count, String containedWord) {
      mySalaryEntityRepository.deleteByYearAndMonthAndWeekAndGroupSizeAndCountAndSkillsContaining(year, month, week, groupSize, count, containedWord);
   }

   @Transactional
   @Override public void deleteMySalarysBy(int year, int month, int week, int groupSize) {
      mySalaryEntityRepository.deleteByYearAndMonthAndWeekAndGroupSize(year, month, week, groupSize);
   }

   @Transactional
   @Override public void deleteMySalarysBy(int year, int month, int week, int groupSize, String containedWord) {
      mySalaryEntityRepository.deleteByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(year, month, week, groupSize, containedWord);
   }

   @Override
   public Slice<MySalaryEntity> findSalariessAggregatedBySkills(int year, int month, int groupSize, int pageIndex, int pageSize, String sortingField,
                                                                String sortingDir) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      Slice<MySalaryEntity> page = mySalaryEntityRepository.findByCompanyNameAndYearAndMonthAndWeekAndGroupSize("", year, month, -1, groupSize, request);
      return page;
   }


   @Override
   public Slice<MySalaryEntity> findSalariessAggregatedByCompanies(int year, int month, int pageIndex, int pageSize, String sortingField,
                                                                   String sortingDir) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      Slice<MySalaryEntity> page = mySalaryEntityRepository.findBySkillsAndYearAndMonthAndWeek("", year, month, -1, request);
      return page;
   }

   @Override public Optional<MySalaryContract> findByRecordId(String recordId) {
      MySalaryEntity entity = mySalaryEntityRepository.findOne(recordId);
      if(entity != null) {
         return Optional.of(entity);
      } else {
         return Optional.empty();
      }
   }


   @Override public long countMySalarysBy(int year, int month, int week, int groupSize, long count) {
      return mySalaryEntityRepository.countByYearAndMonthAndWeekAndGroupSizeAndCount(year, month, week, groupSize, count);
   }

   @Override public long countMySalarysBy(int year, int month, int week, int groupSize, long count, String containedWord) {
      return mySalaryEntityRepository.countByYearAndMonthAndWeekAndGroupSizeAndCountAndSkillsContaining(year, month, week, groupSize, count, containedWord);
   }

   @Override public long countMySalarysBy(int year, int month, int week, int groupSize) {
      return mySalaryEntityRepository.countByYearAndMonthAndWeekAndGroupSize(year, month, week, groupSize);
   }

   @Override public long countMySalarysBy(int year, int month, int week, int groupSize, String containedWord) {
      return mySalaryEntityRepository.countByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(year, month, week, groupSize, containedWord);
   }

   @Override public Page<MySalaryEntity> findAllOrderByCountDesc(int pageIndex, int pageSize, String sortingField, String sortingDir) {

      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      return mySalaryEntityRepository.findAllByOrderByCountDescYearDescMonthDesc(request);

   }
}
