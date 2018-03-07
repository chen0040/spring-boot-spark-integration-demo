package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.sga.models.CompanyNetStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by xschen on 8/2/2017.
 */
@Repository
public interface CompanyNetStatRepository extends CrudRepository<CompanyNetStat, String>, JpaSpecificationExecutor<CompanyNetStat> {
   Page<CompanyNetStat> findAll(Pageable pageable);

}
