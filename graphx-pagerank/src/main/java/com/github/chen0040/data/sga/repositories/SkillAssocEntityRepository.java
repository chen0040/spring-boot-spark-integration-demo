package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.sga.models.SkillAssocEntity;
import com.github.chen0040.data.sga.viewmodels.SkillAssocAggregate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by xschen on 1/1/2017.
 */
@Repository
public interface SkillAssocEntityRepository extends CrudRepository<SkillAssocEntity, Long>, JpaSpecificationExecutor<SkillAssocEntity> {

   Page<SkillAssocEntity> findAll(Pageable pageable);


   Page<SkillAssocEntity> findByMonthAndJobTitleOrderByCountDescYearDesc(int month, String jobTitle, Pageable pageable);
   Page<SkillAssocEntity> findByMonthAndJobTitleAndSkillsContainingOrderByCountDescYearDesc(int month, String jobTitle, String skills, Pageable pageable);

   Page<SkillAssocEntity> findByMonthNotAndJobTitleOrderByCountDescYearDescMonthDesc(int monthNot, String jobTitle, Pageable pageable);
   Page<SkillAssocEntity> findByMonthNotAndJobTitleAndSkillsContainingOrderByCountDescYearDescMonthDesc(int monthNot, String jobTitle, String skills, Pageable pageable);

   Page<SkillAssocEntity> findAllByOrderByCountDescYearDescMonthDesc(Pageable pageable);

   SkillAssocEntity findFirstBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(String skills, int year, int month, int week, String country,
                                                                                 String jobTitle);

   @Query(value = "SELECT DISTINCT s.skills FROM SkillAssocEntity s WHERE s.skills like CONCAT(?1, '%') AND group_size = ?2")
   List<SkillAssocEntity> findDistinctBySkillsStartingWithIgnoreCaseAndGroupSize(String skills, int groupSize);

   @Query(value = "SELECT DISTINCT s.skills FROM SkillAssocEntity s WHERE group_size = ?1 ORDER BY s.skills")
   List<SkillAssocEntity> findDistinctSkillsByGroupSize(int groupSize);

   Slice<SkillAssocEntity> findBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(String skills, int year, int month, int week, String country,
                                                                                   String jobTitle, Pageable pageable);

   Page<SkillAssocEntity> findByMonthNotAndJobTitle(int monthNot, String jobTitle, Pageable pageable);
   Page<SkillAssocEntity> findByMonthNotAndJobTitleAndSkillsContaining(int monthNot, String jobTitle, String skills, Pageable pageable);

   Page<SkillAssocEntity> findByMonthAndJobTitle(int month, String jobTitle, Pageable pageable);
   Page<SkillAssocEntity> findByMonthAndJobTitleAndSkillsContaining(int month, String jobTitle, String skills, Pageable pageable);

   void deleteByYearAndMonthAndWeekAndGroupSizeAndCount(int year, int month, int week, int groupSize, long count);

   long countByYearAndMonthAndWeekAndGroupSizeAndCount(int year, int month, int week, int groupSize, long count);

   @Query(value = "SELECT s.skills FROM SkillAssocEntity s WHERE LOWER(s.skills) LIKE CONCAT('%', CONCAT(?1, '%')) GROUP BY s.skills ORDER BY count(s.skills) DESC")
   List<String> findSkillsByTopCount(String skills, Pageable pageable);

   Slice<SkillAssocEntity> findByJobTitleNot(String jobTitleNot, Pageable pageable);

   Slice<SkillAssocEntity> findByYearAndMonthAndWeekAndGroupSize(int year, int month, int week, int groupSize, Pageable pageable);
   Slice<SkillAssocEntity> findByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(int year, int month, int week, int groupSize, String skills, Pageable pageable);

   @Query(value = "SELECT new com.github.chen0040.data.sga.viewmodels.SkillAssocAggregate(skills, count(s.skills)) FROM SkillAssocEntity s WHERE s.groupSize = ?2 AND LOWER(s.skills) LIKE CONCAT('%', CONCAT(?1, '%')) GROUP BY s.skills ORDER BY count(s.skills) DESC")
   Slice<SkillAssocAggregate> findCountByMultipleSkills(String skilllike1, int groupSize, Pageable pageable);

   @Query(value = "SELECT new com.github.chen0040.data.sga.viewmodels.SkillAssocAggregate(skills, count(s.skills)) FROM SkillAssocEntity s WHERE s.groupSize = ?4 AND LOWER(s.skills) LIKE CONCAT('%', CONCAT(?1, '%')) AND LOWER(s.skills) LIKE CONCAT('%', CONCAT(?2, '%')) AND LOWER(s.skills) LIKE CONCAT('%', CONCAT(?3, '%')) GROUP BY s.skills ORDER BY count(s.skills) DESC")
   Slice<SkillAssocAggregate> findCountByMultipleSkills(String skilllike1, String skilllike2, String skilllike3, int groupSize, Pageable pageable);

   @Query(value = "SELECT new com.github.chen0040.data.sga.viewmodels.SkillAssocAggregate(skills, count(s.skills)) FROM SkillAssocEntity s WHERE s.groupSize = ?3 AND LOWER(s.skills) LIKE CONCAT('%', CONCAT(?1, '%')) AND LOWER(s.skills) LIKE CONCAT('%', CONCAT(?2, '%')) GROUP BY s.skills ORDER BY count(s.skills) DESC")
   Slice<SkillAssocAggregate> findCountByMultipleSkills(String skilllike1, String skilllike2, int groupSize, Pageable pageable);

   long countByYearAndMonthAndWeekAndGroupSizeAndCountAndSkillsContaining(int year, int month, int week, int groupSize, long count,
                                                                          String containedWord);

   void deleteByYearAndMonthAndWeekAndGroupSizeAndCountAndSkillsContaining(int year, int month, int week, int groupSize, long count,
                                                                           String containedWord);

   void deleteByYearAndMonthAndWeekAndGroupSize(int year, int month, int week, int groupSize);

   void deleteByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(int year, int month, int week, int groupSize, String containedWord);

   long countByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(int year, int month, int week, int groupSize, String containedWord);

   long countByGroupSizeAndSkillsIgnoreCase(int groupSize, String containedWord);

   long countByGroupSize(int groupSize);

   long countByYearAndMonthAndWeekAndGroupSize(int year, int month, int week, int groupSize);


}
