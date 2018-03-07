package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.sga.models.MySalaryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by xschen on 1/1/2017.
 */
@Repository
public interface MySalaryEntityRepository extends CrudRepository<MySalaryEntity, String> {

   Page<MySalaryEntity> findAll(Pageable pageable);


   Page<MySalaryEntity> findByMonthAndJobTitleOrderByCountDescYearDesc(int month, String jobTitle, Pageable pageable);

   Page<MySalaryEntity> findByMonthNotAndJobTitleOrderByCountDescYearDescMonthDesc(int monthNot, String jobTitle, Pageable pageable);

   Page<MySalaryEntity> findAllByOrderByCountDescYearDescMonthDesc(Pageable pageable);

   MySalaryEntity findFirstBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(String skills, int year, int month, int week, String country,
                                                                               String jobTitle);

   Slice<MySalaryEntity> findBySkillsAndYearAndMonthAndWeekAndCountryAndJobTitle(String skills, int year, int month, int week, String country,
                                                                                 String jobTitle, Pageable pageable);

   Page<MySalaryEntity> findByMonthNotAndJobTitle(int monthNot, String jobTitle, Pageable pageable);

   Page<MySalaryEntity> findByMonthAndJobTitle(int month, String jobTitle, Pageable pageable);

   void deleteByYearAndMonthAndWeekAndGroupSizeAndCount(int year, int month, int week, int groupSize, long count);

   long countByYearAndMonthAndWeekAndGroupSizeAndCount(int year, int month, int week, int groupSize, long count);

   Slice<MySalaryEntity> findByJobTitleNot(String jobTitleNot, Pageable pageable);

   Slice<MySalaryEntity> findByYearAndMonthAndWeekAndGroupSize(int year, int month, int week, int groupSize, Pageable pageable);

   long countByYearAndMonthAndWeekAndGroupSizeAndCountAndSkillsContaining(int year, int month, int week, int groupSize, long count,
                                                                          String containedWord);

   void deleteByYearAndMonthAndWeekAndGroupSizeAndCountAndSkillsContaining(int year, int month, int week, int groupSize, long count,
                                                                           String containedWord);

   void deleteByYearAndMonthAndWeekAndGroupSize(int year, int month, int week, int groupSize);

   void deleteByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(int year, int month, int week, int groupSize, String containedWord);

   long countByYearAndMonthAndWeekAndGroupSizeAndSkillsContaining(int year, int month, int week, int groupSize, String containedWord);

   long countByYearAndMonthAndWeekAndGroupSize(int year, int month, int week, int groupSize);

   Slice<MySalaryEntity> findByCompanyNameAndYearAndMonthAndWeekAndGroupSize(String companyName, int year, int month, int week, int groupSize, Pageable request);

   Slice<MySalaryEntity> findBySkillsAndYearAndMonthAndWeek(String skills, int year, int month, int week, Pageable request);
}
