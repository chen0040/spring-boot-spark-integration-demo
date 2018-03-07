package com.github.chen0040.data.sga.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by danielseetoh on 4/4/17.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "resumes")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class UserResume extends BasicEntity implements Serializable {

    private long userId;

    @Lob
    @Column(name="skills", length=3072)
    private String skills;

    @Lob
    @Column(name="education", length=3072)
    private String education;

    @Lob
    @Column(name="experience", length=3072)
    private String experience;

    @Lob
    @Column(name="projects", length=3072)
    private String projects;

}
