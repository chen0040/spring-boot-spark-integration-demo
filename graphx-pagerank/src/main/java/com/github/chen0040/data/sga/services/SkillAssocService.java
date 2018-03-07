package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.OneToManyToOneAssociation;
import com.github.chen0040.data.sga.models.SkillAssocEntity;
import com.github.chen0040.data.sga.models.SkillNet;
import com.github.chen0040.data.sga.models.SkillNetStat;
import com.github.chen0040.data.sga.viewmodels.SkillAssocAggregate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 2/1/2017.
 */
public interface SkillAssocService {
   Optional<SkillAssocEntity> deleteSkillAssoc(long id);

   Page<SkillAssocEntity> findAll(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike);

   Optional<SkillAssocEntity> findById(long id);

   List<SkillAssocEntity> findBySkillsStartingWith(String skill);

   List<SkillAssocEntity> findAllSkills();

   void deleteSkillAssocs(List<SkillAssocEntity> content);

   Slice<SkillAssocEntity> findLowLevelSkillAssoc(int pageIndex, int pageSize);

   int accumulateHighLevelAssoc(SkillAssocEntity sa);

   int aggregateHighLevelAssoc(SkillAssocEntity sa);

   Page<SkillAssocEntity> findYearlyHighLevelSkillAssocsSorted(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike);

   Page<SkillAssocEntity> findYearlyHighLevelSkillAssocs(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike);

   Page<SkillAssocEntity> findMonthlyHighLevelSkillAssocsSorted(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike);

   long countSkillAssocsBy(int year, int month, int week, int groupSize);

   void saveClusterId(String skill, long clusterId);

   long countSkillAssocsBy(int year, int month, int week, int groupSize, long count);
   long countSkillAssocsBy(int year, int month, int week, int groupSize, String containedWord);
   long countSkillAssocsBy(int year, int month, int week, int groupSize, long count, String containedWord);

   Page<SkillAssocEntity> findAllOrderByCountDesc(int pageIndex, int pageSize, String sortingField, String sortingDir);

   Page<SkillAssocEntity> findMonthlyHighLevelSkillAssocs(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike);

   void deleteSkillAssocsBy(int year, int month, int week, int groupSize, long count);
   void deleteSkillAssocsBy(int year, int month, int week, int groupSize, long count, String containedWord);
   void deleteSkillAssocsBy(int year, int month, int week, int groupSize);
   void deleteSkillAssocsBy(int year, int month, int week, int groupSize, String containedWord);

   Slice<SkillAssocEntity> findHighLevelSkillAssocs(int year, int month, int groupSize, int pageIndex, int pageSize, String sortingField, String sortingDir, String skillsLike);

   List<SkillAssocEntity> findCountBySkills(String skillsLike1, String skillsLike2, String skillsLike3, int groupSize);
   List<SkillAssocAggregate> findCountByMultipleSkills(String skillsLike1, String skillsLike2, String skillsLike3, int groupSize);

   SkillNet findSkillsByTopCount(String skills);

   void saveSimilarSkills(String skill, List<OneToManyToOneAssociation> assocs);

   SkillNetStat findNetStat(String skill);
   void saveRank(String skill, double rank);
   long countNetStats();

   Page<SkillNetStat> findAllNetStats(Pageable pageable);
   Page<SkillNetStat> findAllNetStats(int pageIndex, int pageSize, String sortingField, String sortingDir, String companyNameLike);

   void clearNetStats();
}
