package com.github.chen0040.data.sga.repositories;

import com.github.chen0040.data.sga.models.MyJob;
import com.github.chen0040.data.sga.models.JobSkill;
import com.github.chen0040.data.sga.viewmodels.JobSkillViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
	
	@Query("SELECT new com.github.chen0040.data.sga.viewmodels.JobSkillViewModel(line) FROM JobSkill line where line.job.id = :jobId")
	List<JobSkillViewModel> findViewModelsByJobId(@Param("jobId") Long jobId);
	
	Long countByJob(MyJob job);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM JobSkill line WHERE line.job.id = :jobId")
	void deleteByJobId(@Param("jobId") Long jobId);

	@Transactional
	@Modifying
	@Query("UPDATE JobSkill line SET line.skill.id = :newSkillId WHERE line.skill.id = :skillId")
	void updateSkillIdBySkillId(@Param("skillId") Long skillId, @Param("newSkillId") Long newSkillId);
}
