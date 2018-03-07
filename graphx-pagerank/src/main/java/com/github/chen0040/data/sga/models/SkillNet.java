package com.github.chen0040.data.sga.models;

import com.github.chen0040.data.sga.viewmodels.SkillAssocAggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by ASUS-PC on 4/2/2017.
 */
@Setter
@Getter
@AllArgsConstructor
public class SkillNet {
    public List<SkillAssocAggregate> nodes;
    public List<SkillLink> links;
}