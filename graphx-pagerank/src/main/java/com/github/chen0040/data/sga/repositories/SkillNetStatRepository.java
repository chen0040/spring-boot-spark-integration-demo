package com.github.chen0040.data.sga.repositories;

import com.github.chen0040.data.sga.models.SkillNetStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by xschen on 8/2/2017.
 */
@Repository
public interface SkillNetStatRepository extends CrudRepository<SkillNetStat, String>, JpaSpecificationExecutor<SkillNetStat> {

   Page<SkillNetStat> findAll(Pageable pageable);
}
