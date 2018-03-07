package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.sga.models.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by xschen on 3/10/2016.
 */
@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {
   List<Skill> findByName(String name);

   Page<Skill> findAll(Pageable pageable);
}
