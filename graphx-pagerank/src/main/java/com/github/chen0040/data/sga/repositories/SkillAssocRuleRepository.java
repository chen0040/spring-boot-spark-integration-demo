package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.sga.models.SkillAssocRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Created by xschen on 3/10/2016.
 */
@Repository
public interface SkillAssocRuleRepository extends CrudRepository<SkillAssocRule, Long> {
   List<SkillAssocRule> findByWindowStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
   List<SkillAssocRule> findByWindowEndTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

   List<SkillAssocRule> findByWindowStartTimeLessThanEqual(LocalDateTime startTime);
   List<SkillAssocRule> findByWindowStartTimeGreaterThanEqual(LocalDateTime startTime);

   List<SkillAssocRule> findByWindowEndTimeLessThanEqual(LocalDateTime endTime);
   List<SkillAssocRule> findByWindowEndTimeGreaterThanEqual(LocalDateTime endTime);

   List<SkillAssocRule> findByWindowStartTimeGreaterThanEqualAndWindowEndTimeLessThanEqual(LocalDateTime startTime, LocalDateTime endTime);
   List<SkillAssocRule> findByWindowStartTimeBetweenOrWindowEndTimeBetween(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime startTime2, LocalDateTime endTime2);

   Page<SkillAssocRule> findByWindowStartTimeGreaterThanEqualAndWindowEndTimeLessThanEqual(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
   Page<SkillAssocRule> findByWindowStartTimeBetweenOrWindowEndTimeBetween(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime startTime2, LocalDateTime endTime2, Pageable pageable);

   Page<SkillAssocRule> findAll(Pageable pageable);
}
