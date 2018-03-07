package com.github.chen0040.data.sga.services;

import com.github.chen0040.data.sga.models.UserResume;

import java.util.List;
import java.util.Optional;

/**
 * Created by danielseetoh on 4/4/17.
 */
public interface UserResumeService {

    UserResume saveUserResume(UserResume resume);

    Optional<UserResume> findResumeByUserId(long userId);

    Optional<UserResume> findResumeById(long id);

    List<Long> findAllUserIdsBySkill(String skill);

    List<Long> findAllUserIdsByEducation(String education);

    List<Long> findAllUserIdsByExperience(String experience);

    List<Long> findAllUserIdsByProjects(String projects);

}