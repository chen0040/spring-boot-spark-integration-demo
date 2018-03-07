package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.OneToManyToOneAssociation;
import com.github.chen0040.data.sga.models.*;
import com.github.chen0040.data.sga.repositories.SkillAssocEntityRepository;
import com.github.chen0040.data.sga.repositories.SkillNetStatRepository;
import com.github.chen0040.data.sga.specs.SearchCriteria;
import com.github.chen0040.data.sga.specs.SingleSpecification;
import com.github.chen0040.data.sga.specs.SpecificationBuilder;
import com.github.chen0040.data.sga.viewmodels.SkillAssocAggregate;
import com.github.chen0040.lang.commons.utils.StringUtils;
import org.hibernate.mapping.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 2/1/2017.
 */
@Service
public class SkillAssocServiceImpl implements SkillAssocService {

   @Autowired
   private SkillAssocEntityRepository skillAssocEntityRepository;


   @Autowired
   private SkillNetStatRepository skillNetStatRepository;

   private static final Logger logger = LoggerFactory.getLogger(SkillAssocServiceImpl.class);


   @Transactional
   @Override public Optional<SkillAssocEntity> deleteSkillAssoc(long id) {
      SkillAssocEntity skillAssocEntity = skillAssocEntityRepository.findOne(id);
      if(skillAssocEntity == null) {
         return Optional.empty();
      } else {
         skillAssocEntityRepository.delete(id);
         return Optional.of(skillAssocEntity);
      }
   }


   @Override public Page<SkillAssocEntity> findAll(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike) {

      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(StringUtils.isEmpty(skillsLike)) {
         return skillAssocEntityRepository.findAll(request);
      } else {
         return skillAssocEntityRepository.findAll(new SpecificationBuilder<SkillAssocEntity>().with("skills", ":", skillsLike).build(), request);
      }
   }

   @Override public Optional<SkillAssocEntity> findById(long id) {
      SkillAssocEntity skillAssocEntity = skillAssocEntityRepository.findOne(id);
      if(skillAssocEntity == null) {
         return Optional.empty();
      } else {
         return Optional.of(skillAssocEntity);
      }
   }

   @Override public List<SkillAssocEntity> findBySkillsStartingWith(String skill) {
//      PageRequest request = new PageRequest(0,10, Sort.Direction.ASC, "skills");

/*      List<SkillAssocEntity> findBySkillsStartingWith = skillAssocEntityRepository.findBySkillsStartingWithIgnoreCaseAndGroupSize(skill,1);

      List<String> skills = new ArrayList<>();

      for(int i=0; i < findBySkillsStartingWith.size(); ++i){
         skills.add(findBySkillsStartingWith.get(i).getSkills());
      }

      for(String eachSkill : skills) {
         logger.info(eachSkill.toString());
      }*/

      return skillAssocEntityRepository.findDistinctBySkillsStartingWithIgnoreCaseAndGroupSize(skill,1);
   }

   @Override public List<SkillAssocEntity> findAllSkills() {
      PageRequest request = new PageRequest(0, Integer.MAX_VALUE);

      return skillAssocEntityRepository.findDistinctSkillsByGroupSize(1);
   }

   @Transactional
   @Override public void deleteSkillAssocs(List<SkillAssocEntity> content) {
      skillAssocEntityRepository.delete(content);
   }

   @Override public Slice<SkillAssocEntity> findLowLevelSkillAssoc(int pageIndex, int pageSize) {

      final String jobTitleNot = "";
      return skillAssocEntityRepository.findByJobTitleNot(jobTitleNot, new PageRequest(pageIndex, pageSize));
   }


   @Transactional
   @Override public int accumulateHighLevelAssoc(SkillAssocEntity sa) { // temporary fix: more costly but should fix the errors in the current assoc skills data-table
      final String skills = sa.getSkills();
      final int year = sa.getYear();
      final int month = sa.getMonth();
      final int week = sa.getWeek();
      final String country = sa.getCountry();
      if(sa.getGroupSize() > 3) {
         return 0;
      }

      int count = 0;


      SkillAssocEntity
              aggregator_by_month = skillAssocEntityRepository.findFirstBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(skills, year, month, -1, country, "");

      if(aggregator_by_month == null) {
         aggregator_by_month = new SkillAssocEntity();
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



      skillAssocEntityRepository.save(aggregator_by_month);

      SkillAssocEntity
              aggregator_by_year = skillAssocEntityRepository.findFirstBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(skills, year, -1, -1, country, "");

      if(aggregator_by_year == null) {
         aggregator_by_year = new SkillAssocEntity();
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

      skillAssocEntityRepository.save(aggregator_by_year);

      return count;
   }

   @Transactional
   @Override
   public int aggregateHighLevelAssoc(SkillAssocEntity sa){
      final String skills = sa.getSkills();
      final int year = sa.getYear();
      final int month = sa.getMonth();
      final int week = sa.getWeek();
      final String country = sa.getCountry();
      if(sa.getGroupSize() > 3) {
         return 0;
      }

      Slice<SkillAssocEntity>
              page = skillAssocEntityRepository.findBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(skills, year, month, -1, country, "", new PageRequest(0, 50));

      List<SkillAssocEntity> aggregators_by_month = page.getContent();

      boolean aggregated = false;


      SkillAssocEntity aggregator_by_month;
      if(aggregators_by_month.size() > 1){
         logger.info("aggregate {} results by month", aggregators_by_month.size());

         aggregator_by_month = new SkillAssocEntity();
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

         skillAssocEntityRepository.delete(aggregators_by_month);

         skillAssocEntityRepository.save(aggregator_by_month);

         aggregated = true;
      }

      page = skillAssocEntityRepository.findBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(skills, year, -1, -1, country, "", new PageRequest(0, 50));

      List<SkillAssocEntity> aggregators_by_year = page.getContent();

      SkillAssocEntity aggregator_by_year;
      if(aggregators_by_year.size() > 1) {
         logger.info("aggregate {} results by year", aggregators_by_year.size());

         aggregator_by_year = new SkillAssocEntity();
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

         skillAssocEntityRepository.delete(aggregators_by_year);

         skillAssocEntityRepository.save(aggregator_by_year);


         aggregated = true;


      }

      return aggregated ? 1 : 0;
   }

   @Override public Page<SkillAssocEntity> findYearlyHighLevelSkillAssocs(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike) {

      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(StringUtils.isEmpty(skillsLike)) {
         return skillAssocEntityRepository.findByMonthAndJobTitle(-1, "", request);
      } else {
         return skillAssocEntityRepository.findByMonthAndJobTitleAndSkillsContaining(-1, "", skillsLike, request);
      }

   }

   @Override public Page<SkillAssocEntity> findYearlyHighLevelSkillAssocsSorted(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(StringUtils.isEmpty(skillsLike)) {
         return skillAssocEntityRepository.findByMonthAndJobTitleOrderByCountDescYearDesc(-1, "", request);
      } else {
         return skillAssocEntityRepository.findByMonthAndJobTitleAndSkillsContainingOrderByCountDescYearDesc(-1, "", skillsLike, request);
      }
   }

   @Override public Page<SkillAssocEntity> findMonthlyHighLevelSkillAssocsSorted(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(StringUtils.isEmpty(skillsLike)) {
         return skillAssocEntityRepository.findByMonthNotAndJobTitleOrderByCountDescYearDescMonthDesc(-1, "", request);
      } else {
         return skillAssocEntityRepository.findByMonthNotAndJobTitleAndSkillsContainingOrderByCountDescYearDescMonthDesc(-1, "", skillsLike, request);
      }
   }

   @Override public Page<SkillAssocEntity> findMonthlyHighLevelSkillAssocs(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike) {

      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(StringUtils.isEmpty(skillsLike)) {
         return skillAssocEntityRepository.findByMonthNotAndJobTitle(-1, "", request);
      } else {
         return skillAssocEntityRepository.findByMonthNotAndJobTitleAndSkillsContaining(-1, "", skillsLike, request);
      }
   }

   @Transactional
   @Override public void deleteSkillAssocsBy(int year, int month, int week, int groupSize, long count) {
      skillAssocEntityRepository.deleteByYearAndMonthAndWeekAndGroupSizeAndCount(year, month, week, groupSize, count);
   }

   @Transactional
   @Override public void deleteSkillAssocsBy(int year, int month, int week, int groupSize, long count, String containedWord) {
      skillAssocEntityRepository.deleteByYearAndMonthAndWeekAndGroupSizeAndCountAndSkillsContaining(year, month, week, groupSize, count, containedWord);
   }

   @Transactional
   @Override public void deleteSkillAssocsBy(int year, int month, int week, int groupSize) {
      skillAssocEntityRepository.deleteByYearAndMonthAndWeekAndGroupSize(year, month, week, groupSize);
   }

   @Transactional
   @Override public void deleteSkillAssocsBy(int year, int month, int week, int groupSize, String containedWord) {
      skillAssocEntityRepository.deleteByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(year, month, week, groupSize, containedWord);
   }

   @Override
   public Slice<SkillAssocEntity> findHighLevelSkillAssocs(int year, int month, int groupSize, int pageIndex, int pageSize, String sortingField,
           String sortingDir, String skillsLike) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(StringUtils.isEmpty(skillsLike)) {
         return skillAssocEntityRepository.findByYearAndMonthAndWeekAndGroupSize(year, month, -1, groupSize, request);
      } else {
         return skillAssocEntityRepository.findByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(year, month, -1, groupSize, skillsLike, request);
      }
   }

   @Override
   public List<SkillAssocEntity> findCountBySkills(String skillsLike1, String skillsLike2, String skillsLike3, int groupSize) {

      long totalCount = skillAssocEntityRepository.countByGroupSize(groupSize);

      if(StringUtils.isEmpty(skillsLike2) && StringUtils.isEmpty(skillsLike3)){
          long countBySkill1 = skillAssocEntityRepository.countByGroupSizeAndSkillsIgnoreCase(groupSize, skillsLike1);

          totalCount = totalCount - countBySkill1;

          List<SkillAssocEntity> skillAssocEntities = new ArrayList<>(2);

          SkillAssocEntity skillAssocEntity1 = new SkillAssocEntity();
          skillAssocEntity1.setSkills(skillsLike1);
          skillAssocEntity1.setCount(countBySkill1);
          skillAssocEntities.add(skillAssocEntity1);

          SkillAssocEntity skillAssocEntity2 = new SkillAssocEntity();
          skillAssocEntity2.setSkills("others");
          skillAssocEntity2.setCount(totalCount);
          skillAssocEntities.add(skillAssocEntity2);

          return skillAssocEntities;
      }
      else if (StringUtils.isEmpty(skillsLike3)){

          Long countBySkill1 = skillAssocEntityRepository.countByGroupSizeAndSkillsIgnoreCase(groupSize, skillsLike1);
          Long countBySkill2 = skillAssocEntityRepository.countByGroupSizeAndSkillsIgnoreCase(groupSize, skillsLike2);

          totalCount = totalCount - countBySkill1 - countBySkill2;

          List<SkillAssocEntity> skillAssocEntities = new ArrayList<>(3);

          SkillAssocEntity skillAssocEntity1 = new SkillAssocEntity();
          skillAssocEntity1.setSkills(skillsLike1);
          skillAssocEntity1.setCount(countBySkill1);
          skillAssocEntities.add(skillAssocEntity1);

          SkillAssocEntity skillAssocEntity2 = new SkillAssocEntity();
          skillAssocEntity2.setSkills(skillsLike2);
          skillAssocEntity2.setCount(countBySkill2);
          skillAssocEntities.add(skillAssocEntity2);

          SkillAssocEntity skillAssocEntity3 = new SkillAssocEntity();
          skillAssocEntity3.setSkills("others");
          skillAssocEntity3.setCount(totalCount);
          skillAssocEntities.add(skillAssocEntity3);

          return skillAssocEntities;

      }
      else {
          Long countBySkill1 = skillAssocEntityRepository.countByGroupSizeAndSkillsIgnoreCase(groupSize, skillsLike1);
          Long countBySkill2 = skillAssocEntityRepository.countByGroupSizeAndSkillsIgnoreCase(groupSize, skillsLike2);
          Long countBySkill3 = skillAssocEntityRepository.countByGroupSizeAndSkillsIgnoreCase(groupSize, skillsLike3);

          totalCount = totalCount - countBySkill1 - countBySkill2 - countBySkill3;

          List<SkillAssocEntity> skillAssocEntities = new ArrayList<>(4);

          SkillAssocEntity skillAssocEntity1 = new SkillAssocEntity();
          skillAssocEntity1.setSkills(skillsLike1);
          skillAssocEntity1.setCount(countBySkill1);
          skillAssocEntities.add(skillAssocEntity1);

          SkillAssocEntity skillAssocEntity2 = new SkillAssocEntity();
          skillAssocEntity2.setSkills(skillsLike2);
          skillAssocEntity2.setCount(countBySkill2);
          skillAssocEntities.add(skillAssocEntity2);

          SkillAssocEntity skillAssocEntity3 = new SkillAssocEntity();
          skillAssocEntity3.setSkills(skillsLike3);
          skillAssocEntity3.setCount(countBySkill3);
          skillAssocEntities.add(skillAssocEntity3);

          SkillAssocEntity skillAssocEntity4 = new SkillAssocEntity();
          skillAssocEntity4.setSkills("others");
          skillAssocEntity4.setCount(totalCount);
          skillAssocEntities.add(skillAssocEntity4);

          return skillAssocEntities;
      }
   }

    @Override
    public List<SkillAssocAggregate> findCountByMultipleSkills(String skillsLike1, String skillsLike2, String skillsLike3, int groupSize) {

       PageRequest request = new PageRequest(0,3);

       long totalCount = skillAssocEntityRepository.countByGroupSize(groupSize);

       if(StringUtils.isEmpty(skillsLike2) && StringUtils.isEmpty(skillsLike3)){
           List<SkillAssocAggregate> top3Count = skillAssocEntityRepository.findCountByMultipleSkills(skillsLike1, groupSize, request).getContent();

           List<SkillAssocAggregate> entities = new ArrayList<>(top3Count.size()+1);

           for(SkillAssocAggregate entity : top3Count){
               entities.add(entity);
               totalCount = totalCount - entity.getCount();
           }

           entities.add(new SkillAssocAggregate("others", totalCount));

/*           Map<String,Long> resultMap = new HashMap<>(entities.size());

           for(SkillAssocAggregate entity : entities){
               resultMap.put(entity.getSkills(), entity.getCount());
               logger.info("Skills = " + entity.getSkills() + ", Count = " + entity.getCount());
           }*/

           return entities;
       }
       else if (StringUtils.isEmpty(skillsLike3)){
           List<SkillAssocAggregate> top3Count = skillAssocEntityRepository.findCountByMultipleSkills(skillsLike1, skillsLike2,  groupSize, request).getContent();

           List<SkillAssocAggregate> entities = new ArrayList<>(top3Count.size()+1);

           for(SkillAssocAggregate entity : top3Count){
               entities.add(entity);
               totalCount = totalCount - entity.getCount();
           }

           //select skills, count(*) from skill_assoc where group_size = 1 and skills like "%Ant%" and job_title like "%vp%" group by skills;

           entities.add(new SkillAssocAggregate("others", totalCount));

           return entities;
       }
       else{
           if(groupSize == 2) {
               PageRequest newRequest = new PageRequest(0, 1);

               List<SkillAssocAggregate> top1Count_1 = skillAssocEntityRepository.findCountByMultipleSkills(skillsLike1, skillsLike2, groupSize, newRequest).getContent();
               List<SkillAssocAggregate> top1Count_2 = skillAssocEntityRepository.findCountByMultipleSkills(skillsLike1, skillsLike3, groupSize, newRequest).getContent();
               List<SkillAssocAggregate> top1Count_3 = skillAssocEntityRepository.findCountByMultipleSkills(skillsLike2, skillsLike3, groupSize, newRequest).getContent();

               List<SkillAssocAggregate> entities = new ArrayList<>(4);

               entities.add(top1Count_1.get(0));
               entities.add(top1Count_2.get(0));
               entities.add(top1Count_3.get(0));

               totalCount = totalCount - top1Count_1.get(0).getCount() - top1Count_2.get(0).getCount() - top1Count_3.get(0).getCount();

               entities.add(new SkillAssocAggregate("others", totalCount));

               Map<String,Long> resultMap = new HashMap<>(entities.size());

               for(SkillAssocAggregate entity : entities){
                   resultMap.put(entity.getSkills(), entity.getCount());
                   logger.info("Skills = " + entity.getSkills() + ", Count = " + entity.getCount());
               }

               return entities;
           }else{
               List<SkillAssocAggregate> top3Count = skillAssocEntityRepository.findCountByMultipleSkills(skillsLike1, skillsLike2, skillsLike3, groupSize, request).getContent();

               List<SkillAssocAggregate> entities = new ArrayList<>(top3Count.size()+1);

               for(SkillAssocAggregate entity : top3Count){
                   entities.add(entity);
                   totalCount = totalCount - entity.getCount();
               }

               entities.add(new SkillAssocAggregate("others", totalCount));

               return entities;
           }
       }
    }

    @Override
    public SkillNet findSkillsByTopCount(String skills) {
       PageRequest request = new PageRequest(0, 10);

       List<String> skillsList = skillAssocEntityRepository.findSkillsByTopCount(skills, request);

       Map<String, String> network = new HashMap<>();

       List<String> testing = new ArrayList<>();

       List<List<String>> temporary = new ArrayList<>();

       for(String skillSet : skillsList){

           skillSet = skillSet.replaceAll("\\s", "");
           String[] skillList = skillSet.split(",");
           for(int i = 0; i < skillList.length; ++i){
               if(!testing.contains(skillList[i])){
                   testing.add(skillList[i]);
               }
           }
       }

       if(!testing.isEmpty()){
           Collections.sort(testing);
       }

       for(String skillSet : skillsList){

           skillSet = skillSet.replaceAll("\\s", "");
           String[] data = skillSet.split(",");
           for(int i = 0; i < data.length; i++){
               List<String> holder = new ArrayList<>();

               if(i != data.length-1) {
                   holder.add(data[i]);
                   holder.add(data[i + 1]);
                   temporary.add(holder);
               }
               else if(data.length <= 1){
                   holder.add(data[i]);
                   holder.add(data[i]);
                   temporary.add(holder);
               }
           }
       }

       List<SkillAssocAggregate> nodes = new ArrayList<>();

       for(String skill : testing){
           SkillAssocAggregate node = new SkillAssocAggregate(skill, skillAssocEntityRepository.countByGroupSizeAndSkillsIgnoreCase(1, skill));
           nodes.add(node);
       }

       List<SkillLink> links = new ArrayList<>();

       for(List<String> pair : temporary){
           for(int i = 0; i < pair.size(); ++i) {
               if (i != pair.size() - 1) {
                   links.add(new SkillLink(testing.indexOf(pair.get(i)), testing.indexOf(pair.get(i + 1))));
               }
           }
       }

       SkillNet skillNet = new SkillNet(nodes, links);

//       logger.info(String.valueOf(holder));
       logger.info("==============");
       logger.info(String.valueOf(temporary));
       logger.info("==============");
       logger.info(String.valueOf(testing));

       return skillNet;
    }


    @Transactional
   @Override public void saveSimilarSkills(String skill, List<OneToManyToOneAssociation> assocs) {
      for(OneToManyToOneAssociation csa : assocs) {
         csa.getLinks().clear();
      }

      SkillNetStat stat = skillNetStatRepository.findOne(skill);

      if(stat == null){
         stat = new SkillNetStat();
         stat.setSkill(skill);
         stat.makeAssociations(assocs);
      } else {
         stat.makeAssociations(assocs);
      }

      skillNetStatRepository.save(stat);

   }


   @Override public SkillNetStat findNetStat(String skill) {
      return skillNetStatRepository.findOne(skill);
   }

   @Transactional
   @Override public void saveRank(String skill, double rank) {
      SkillNetStat stat = skillNetStatRepository.findOne(skill);
      if(stat == null) {
         SkillNetStat skillNetStat = new SkillNetStat();
         skillNetStat.setRank(rank);
         skillNetStat.setSkill(skill);
         stat = skillNetStat;
      } else {
         stat.setRank(rank);
      }

      skillNetStatRepository.save(stat);
   }


   @Override public long countNetStats() {
      return skillNetStatRepository.count();
   }


   @Transactional
   @Override public void saveClusterId(String skill, long clusterId) {
      SkillNetStat stat = skillNetStatRepository.findOne(skill);
      if(stat == null) {
         SkillNetStat skillNetStat = new SkillNetStat();
         skillNetStat.setClusterId(clusterId);
         skillNetStat.setSkill(skill);
         stat = skillNetStat;
      } else {
         stat.setClusterId(clusterId);
      }

      skillNetStatRepository.save(stat);
   }


   @Override public long countSkillAssocsBy(int year, int month, int week, int groupSize, long count) {
      return skillAssocEntityRepository.countByYearAndMonthAndWeekAndGroupSizeAndCount(year, month, week, groupSize, count);
   }

   @Override public long countSkillAssocsBy(int year, int month, int week, int groupSize, long count, String containedWord) {
      return skillAssocEntityRepository.countByYearAndMonthAndWeekAndGroupSizeAndCountAndSkillsContaining(year, month, week, groupSize, count, containedWord);
   }

   @Override public long countSkillAssocsBy(int year, int month, int week, int groupSize) {
      return skillAssocEntityRepository.countByYearAndMonthAndWeekAndGroupSize(year, month, week, groupSize);
   }

   @Override public long countSkillAssocsBy(int year, int month, int week, int groupSize, String containedWord) {
      return skillAssocEntityRepository.countByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(year, month, week, groupSize, containedWord);
   }

   @Override public Page<SkillAssocEntity> findAllOrderByCountDesc(int pageIndex, int pageSize, String sortingField, String sortingDir) {

      PageRequest request;
      if(StringUtils.isEmpty(sortingField)) {
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      return skillAssocEntityRepository.findAllByOrderByCountDescYearDescMonthDesc(request);

   }

   @Override public Page<SkillNetStat> findAllNetStats(Pageable pageable) {
      return skillNetStatRepository.findAll(pageable);
   }


   @Override public Page<SkillNetStat> findAllNetStats(int pageIndex, int pageSize, String sortingField, String sortingDir, String companyNameLike) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)){
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(StringUtils.isEmpty(companyNameLike)) {
         return findAllNetStats(request);
      } else {
         return skillNetStatRepository.findAll(new SingleSpecification<>(new SearchCriteria("snippet", ":", companyNameLike)), request);
      }
   }


   @Transactional
   @Override public void clearNetStats() {
      skillNetStatRepository.deleteAll();

   }
}
