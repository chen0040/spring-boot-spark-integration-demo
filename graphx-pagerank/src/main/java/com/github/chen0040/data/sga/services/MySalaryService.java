package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.MySalaryContract;
import com.github.chen0040.data.sga.models.MyJob;
import com.github.chen0040.data.sga.models.MySalaryEntity;
import com.github.chen0040.data.sga.models.JobInsight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 2/1/2017.
 */
public interface MySalaryService {
   Optional<MySalaryEntity> deleteMySalary(String recordId);

   Optional<MySalaryEntity> scanJob(MyJob job, JobInsight jobInsight);

   Page<MySalaryEntity> findAll(int pageIndex, int pageSize, String sortingField, String sortingDir);


   void deleteMySalarys(List<MySalaryEntity> content);

   Slice<MySalaryEntity> findLowLevelMySalary(int pageIndex, int pageSize);

   int accumulateHighLevelAssoc(MySalaryEntity sa);

   int aggregateHighLevelAssoc(MySalaryEntity sa);

   Page<MySalaryEntity> findYearlyHighLevelMySalarysSorted(int pageIndex, int pageSize, String sortingField, String sortingDir);

   Page<MySalaryEntity> findYearlyHighLevelMySalarys(int pageIndex, int pageSize, String sortingField, String sortingDir);

   Page<MySalaryEntity> findMonthlyHighLevelMySalarysSorted(int pageIndex, int pageSize, String sortingField, String sortingDir);

   long countMySalarysBy(int year, int month, int week, int groupSize);
   long countMySalarysBy(int year, int month, int week, int groupSize, long count);
   long countMySalarysBy(int year, int month, int week, int groupSize, String containedWord);
   long countMySalarysBy(int year, int month, int week, int groupSize, long count, String containedWord);

   Page<MySalaryEntity> findAllOrderByCountDesc(int pageIndex, int pageSize, String sortingField, String sortingDir);

   Page<MySalaryEntity> findMonthlyHighLevelMySalarys(int pageIndex, int pageSize, String sortingField, String sortingDir);

   void deleteMySalarysBy(int year, int month, int week, int groupSize, long count);
   void deleteMySalarysBy(int year, int month, int week, int groupSize, long count, String containedWord);
   void deleteMySalarysBy(int year, int month, int week, int groupSize);
   void deleteMySalarysBy(int year, int month, int week, int groupSize, String containedWord);

   Slice<MySalaryEntity> findSalariessAggregatedBySkills(int year, int month, int groupSize, int pageIndex, int pageSize, String sortingField,
                                                         String sortingDir);

   Slice<MySalaryEntity> findSalariessAggregatedByCompanies(int year, int month, int pageIndex, int pageSize, String sortingField,
                                                            String sortingDir);

   Optional<MySalaryContract> findByRecordId(String recordId);
}
