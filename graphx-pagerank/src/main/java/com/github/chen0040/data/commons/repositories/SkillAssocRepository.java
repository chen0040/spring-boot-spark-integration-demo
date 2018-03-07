package com.github.chen0040.data.commons.repositories;


import com.github.chen0040.data.commons.models.SkillAssocContract;

import java.util.List;


/**
 * Created by xschen on 1/6/17.
 */
public interface SkillAssocRepository {
   void bootstrap();

   List<SkillAssocContract> findByCountry(String companyName, int limit);

   SkillAssocContract save(SkillAssocContract skillAssoc);

   boolean delete(SkillAssocContract officer);
}
