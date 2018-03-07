package com.github.chen0040.data.sga.viewmodels;


import com.github.chen0040.data.sga.models.JobSkill;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;


/**
 * Created by xschen on 30/9/2016.
 */
@Getter
@AllArgsConstructor
public class JobSkillViewModel implements Serializable {

   private static final long serialVersionUID = 6633970065550786678L;

   private JobSkill jobSkill;
}
