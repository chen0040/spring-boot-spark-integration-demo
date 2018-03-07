package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.enums.SkillType;
import com.github.chen0040.data.sga.models.Skill;
import com.github.chen0040.data.sga.repositories.SkillNetStatRepository;
import com.github.chen0040.data.sga.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 3/10/2016.
 */
@Service
public class SkillServiceImpl implements SkillService {
   @Autowired
   private SkillRepository skillRepository;


   @Autowired
   private SkillNetStatRepository skillNetStatRepository;

   @Transactional
   @Override public Skill save(Skill skill) {
      Optional<Skill> existingSkillOptional = findByName(skill.getName());
      if(!existingSkillOptional.isPresent()) {
         return skillRepository.save(skill);
      }
      return existingSkillOptional.get();
   }

   //@Cacheable(cacheNames = "skillByName", key ="#skillName.concat('-skills')")
   @Override public Optional<Skill> findByName(String skillName) {
      List<Skill> skills = skillRepository.findByName(skillName);
      if(skills.isEmpty()){
         return Optional.empty();
      }
      return Optional.of(skills.get(0));
   }

   @Cacheable(cacheNames = "allSkills")
   @Override public List<Skill> findAll() {
      List<Skill> result = new ArrayList<>();
      for(Skill skill : skillRepository.findAll()){
         result.add(skill);
      }
      return result;
   }

   @Transactional
   @Override public void saveIfNotExists(List<String> terms) {
      for(int i=0; i < terms.size(); ++i){
         String skillName = terms.get(i);
         if(findByName(skillName).isPresent()){
            continue;
         }
         Skill skill = new Skill(SkillType.Others, skillName, skillName);
         skillRepository.save(skill);
      }
   }

   @Transactional
   @Override public Optional<Skill> updateSkillNameById(long id, String name) {
      Skill skill = skillRepository.findOne(id);
      if(skill == null){
         return Optional.empty();
      } else {
         skill.setName(name);
         skill = skillRepository.save(skill);
         return Optional.of(skill);
      }
   }

   @Transactional
   @Override public Optional<Skill> updateSkillNameAndDescriptionById(long id, String name, String description) {
      Skill skill = skillRepository.findOne(id);
      if(skill == null){
         return Optional.empty();
      } else {
         skill.setName(name);
         skill.setDescription(description);
         skill = skillRepository.save(skill);
         return Optional.of(skill);
      }
   }


   @Transactional
   @Override public Optional<Skill> createSkill(String name, String description) {
      Optional<Skill> existingSkillOptional = findByName(name);
      if(!existingSkillOptional.isPresent()) {
         Skill skill = new Skill();
         skill.setName(name);
         skill.setDescription(description);
         skill.setSkillType(SkillType.Others);
         return Optional.of(skillRepository.save(skill));
      } else {
         Skill skill = existingSkillOptional.get();
         skill.setDescription(description);
         return Optional.of(skillRepository.save(skill));
      }
   }

   @Transactional
   @Override
   public void removeAll() {
      skillRepository.deleteAll();
      skillNetStatRepository.deleteAll();
   }


   @Transactional
   @Override public Optional<Skill> deleteSkillById(long id) {
      Skill skill = skillRepository.findOne(id);
      if(skill == null){
         return Optional.empty();
      } else {
         try {
            skillRepository.delete(id);
         } catch (Exception e){

         }
         if (skillNetStatRepository.exists(skill.getName())) {
            skillNetStatRepository.delete(skill.getName());
         }
         return Optional.of(skill);
      }
   }


   @Override public Page<Skill> findAll(PageRequest pageRequest) {
      return skillRepository.findAll(pageRequest);
   }
}
