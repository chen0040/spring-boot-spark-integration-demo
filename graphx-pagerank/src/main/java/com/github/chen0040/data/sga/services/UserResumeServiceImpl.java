package com.github.chen0040.data.sga.services;

import com.github.chen0040.data.sga.models.UserResume;
import com.github.chen0040.data.sga.repositories.UserResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by danielseetoh on 4/4/17.
 */
@Service
public class UserResumeServiceImpl implements UserResumeService{

    private static final Logger logger = LoggerFactory.getLogger(UserResumeServiceImpl.class);

    @Autowired
    private UserResumeRepository userResumeRepository;

    @Override
    @Transactional
    public UserResume saveUserResume(UserResume resume){
        Optional<UserResume> resumeOptional = findResumeByUserId(resume.getUserId());
        if (resumeOptional.isPresent()){
            UserResume existingResume = resumeOptional.get();
            existingResume.setSkills(resume.getSkills());
            existingResume.setEducation(resume.getEducation());
            existingResume.setExperience(resume.getExperience());
            existingResume.setProjects(resume.getProjects());
            return userResumeRepository.save(existingResume);
        }
        return userResumeRepository.save(resume);
    }

    public Optional<UserResume> findResumeByUserId(long userId){
        UserResume resume = userResumeRepository.findByUserId(userId);
        if(resume == null){
            return Optional.empty();
        }

        return Optional.of(resume);
    }

    public Optional<UserResume> findResumeById(long id){
        UserResume resume = userResumeRepository.findById(id);
        if(resume == null){
            return Optional.empty();
        }

        return Optional.of(resume);
    }

    @Override
    public List<Long> findAllUserIdsBySkill(String skill){
        List<Long> myUserIds = new ArrayList<>();
        for (UserResume resume : userResumeRepository.findAll()){
            if(resume.getSkills().contains(skill)){
                myUserIds.add(resume.getUserId());
            }
        }
        return myUserIds;
    }

    @Override
    public List<Long> findAllUserIdsByEducation(String education){
        List<Long> myUserIds = new ArrayList<>();
        for (UserResume resume : userResumeRepository.findAll()){
            if(resume.getEducation().contains(education)){
                myUserIds.add(resume.getUserId());
            }
        }
        return myUserIds;
    }

    @Override
    public List<Long> findAllUserIdsByExperience(String experience){
        List<Long> myUserIds = new ArrayList<>();
        for (UserResume resume : userResumeRepository.findAll()){
            if(resume.getExperience().contains(experience)){
                myUserIds.add(resume.getUserId());
            }
        }
        return myUserIds;
    }

    @Override
    public List<Long> findAllUserIdsByProjects(String projects){
        List<Long> myUserIds = new ArrayList<>();
        for (UserResume resume : userResumeRepository.findAll()){
            if(resume.getProjects().contains(projects)){
                myUserIds.add(resume.getUserId());
            }
        }
        return myUserIds;
    }

//    @Override
//    public Optional<UserResume> findUserResumeByUserId(long userId){
//        List<UserResume> resumeList = userResumeRepository.findByUserId(userId);
//        if (resumeList.isEmpty()){
//            return Optional.empty();
//        }
//
//        return Optional.of(resumeList.get(0));
//    }

}
