package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.sga.models.SkillAssocRule;
import com.github.chen0040.data.sga.repositories.SkillAssocRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 25/10/2016.
 */
@Service
public class SkillAssocRuleServiceImpl implements SkillAssocRuleService {

   @Autowired
   private SkillAssocRuleRepository skillAssocRuleRepository;



   @Override public List<SkillAssocRule> findByHardTimeWindow(LocalDateTime startTime, LocalDateTime endTime) {
      return skillAssocRuleRepository.findByWindowStartTimeGreaterThanEqualAndWindowEndTimeLessThanEqual(startTime, endTime);
   }


   @Override public List<SkillAssocRule> findBySoftTimeWindow(LocalDateTime startTime, LocalDateTime endTime) {
      return skillAssocRuleRepository.findByWindowStartTimeBetweenOrWindowEndTimeBetween(startTime, endTime, startTime, endTime);
   }


   @Override public Page<SkillAssocRule> findByHardTimeWindow(LocalDateTime startTime, LocalDateTime endTime, int pageIndex, int pageSize) {
      return skillAssocRuleRepository.findByWindowStartTimeGreaterThanEqualAndWindowEndTimeLessThanEqual(startTime, endTime, new PageRequest(pageIndex, pageSize));
   }


   @Override public Page<SkillAssocRule> findBySoftTimeWindow(LocalDateTime startTime, LocalDateTime endTime, int pageIndex, int pageSize) {
      return skillAssocRuleRepository.findByWindowStartTimeBetweenOrWindowEndTimeBetween(startTime, endTime, startTime, endTime, new PageRequest(pageIndex, pageSize));
   }


   @Override public List<SkillAssocRule> findAll() {
      List<SkillAssocRule> result = new ArrayList<>();
      for(SkillAssocRule rule : skillAssocRuleRepository.findAll()){
         result.add(rule);
      }
      return result;
   }


   @Override public Page<SkillAssocRule> findAll(int pageIndex, int pageSize) {
      return skillAssocRuleRepository.findAll(new PageRequest(pageIndex, pageSize));
   }


   @Override public SkillAssocRule save(String rule, LocalDateTime startTime, LocalDateTime endTime, String memo) {
      SkillAssocRule skillAssocRule = new SkillAssocRule();
      skillAssocRule.setAssocRules(rule);
      skillAssocRule.setWindowStartTime(startTime);
      skillAssocRule.setWindowEndTime(endTime);
      skillAssocRule.setMemo(memo);

      return skillAssocRuleRepository.save(skillAssocRule);
   }


   @Override public SkillAssocRule save(SkillAssocRule rule) {
      return skillAssocRuleRepository.save(rule);
   }


   @Override public long countRules() {
      return skillAssocRuleRepository.count();
   }


   @Override public int countPages(int pageSize) {
      long count = skillAssocRuleRepository.count();
      return (int)Math.ceil((double)count / pageSize);
   }
}
