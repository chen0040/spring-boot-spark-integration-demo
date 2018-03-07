package com.github.chen0040.data.sga.repositories;

import com.github.chen0040.data.sga.models.UserResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by danielseetoh on 4/4/17.
 */
@Repository
public interface UserResumeRepository extends CrudRepository<UserResume, Long>, JpaSpecificationExecutor<UserResume>{

    Page<UserResume> findByUserId(long userId, Pageable pageable);

    Page<UserResume> findById(long id, Pageable pageable);

    Page<UserResume> findAll(Pageable pageable);

    UserResume findByUserId(long userId);

    UserResume findById(long id);

}

