package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.sga.models.JobInsight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Created by xschen on 16/10/2016.
 */
@Repository
public interface JobInsightRepository extends CrudRepository<JobInsight, Long> {
   JobInsight findFirstByRecordId(String recordId);

   List<JobInsight> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
   Page<JobInsight> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
   long countByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

   Page<JobInsight> findBySkillsContaining(String skill, Pageable pageable);

   Page<JobInsight> findBySkillsContainingAndSkillsContaining(String skill1, String skill2, Pageable pageable);

   Page<JobInsight> findBySkillsContainingAndSkillsContainingAndSkillsContaining(String skill1, String skill2, String skill3, Pageable pageable);

   Page<JobInsight> findBySkillsContainingAndSkillsContainingAndSkillsContainingAndSkillsContaining(String skill1, String skill2, String skill3, String skill4, Pageable pageable);

   Page<JobInsight> findByCompanyName(String companyName, Pageable pageable);

   void deleteByRecordId(String recordId);

   Page<JobInsight> findAll(Pageable pageable);

   @Transactional
   @Modifying
   @Query("UPDATE JobInsight line SET line.skills = :newSkills WHERE line.id = :id")
   void updateSkillsById(@Param("newSkills") String newSkills, @Param("id") Long id);
}
