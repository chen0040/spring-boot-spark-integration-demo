package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.commons.enums.JobStatus;
import com.github.chen0040.data.sga.models.MyJob;
import com.github.chen0040.data.sga.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Created by xschen on 27/9/2016.
 */
@Repository
public interface MyJobRepository extends CrudRepository<MyJob, Long>, JpaSpecificationExecutor<MyJob> {
   Page<MyJob> findByCompany(Company company, Pageable pageable);
   Long countByCompany(Company company);

   List<MyJob> findByCompanyAndDateBetween(Company company, LocalDateTime startTime, LocalDateTime endTime);

   List<MyJob> findByCompanyAndStatus(Company company, JobStatus status);

   Page<MyJob> findAll(Pageable pageable);

   @Query(value = "SELECT DISTINCT c.jobTitle FROM MyJob c")
   List<MyJob> findDistinctByJobTitle();

   Long countByCompanyAndStatus(Company company, JobStatus status);

   Long countByStatus(JobStatus status);

   MyJob findFirstByRecordId(String recordId);
   Long countByRecordId(String recordId);

   Page<MyJob> findByCompanyAndStatus(Company company, JobStatus status, Pageable pageable);

   Page<MyJob> findByProducerContaining(String tag, Pageable pageable);
}
