package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.sga.models.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 3/10/2016.
 */
public interface SkillService {
   Skill save(Skill skill);

   Optional<Skill> findByName(String skillName);

   List<Skill> findAll();

   void saveIfNotExists(List<String> terms);

   Page<Skill> findAll(PageRequest pageRequest);

   Optional<Skill> updateSkillNameById(long id, String name);

   Optional<Skill> deleteSkillById(long id);

   Optional<Skill> updateSkillNameAndDescriptionById(long id, String name, String description);

   Optional<Skill> createSkill(String name, String description);

    void removeAll();
}
