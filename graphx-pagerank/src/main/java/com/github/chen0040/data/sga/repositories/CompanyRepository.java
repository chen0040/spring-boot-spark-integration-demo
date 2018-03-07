package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.sga.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by xschen on 3/10/2016.
 */
@Repository
public interface CompanyRepository extends CrudRepository<Company, Long>, JpaSpecificationExecutor<Company> {
   Company findByName(String name);
   Page<Company> findAll(Pageable pageable);
}
