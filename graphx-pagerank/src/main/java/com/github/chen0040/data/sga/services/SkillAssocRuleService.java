package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.sga.models.SkillAssocRule;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Created by xschen on 25/10/2016.
 */
public interface SkillAssocRuleService {
   List<SkillAssocRule> findByHardTimeWindow(LocalDateTime startTime, LocalDateTime endTime);
   List<SkillAssocRule> findBySoftTimeWindow(LocalDateTime startTime, LocalDateTime endTime);

   Page<SkillAssocRule> findByHardTimeWindow(LocalDateTime startTime, LocalDateTime endTime, int pageIndex, int pageSize);
   Page<SkillAssocRule> findBySoftTimeWindow(LocalDateTime startTime, LocalDateTime endTime, int pageIndex, int pageSize);

   List<SkillAssocRule> findAll();
   Page<SkillAssocRule> findAll(int pageIndex, int pageSize);

   SkillAssocRule save(String rule, LocalDateTime startTime, LocalDateTime endTime, String memo);
   SkillAssocRule save(SkillAssocRule rule);

   long countRules();
   int countPages(int pageSize);
}
