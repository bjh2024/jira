package com.mysite.jira.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.ProjectLogData;

public interface ProjectLogDataRepository extends JpaRepository<ProjectLogData, Integer> {
	
	// kdw
	List<ProjectLogData> findByIssue_jiraIdxAndCreateDateGreaterThanEqualOrderByCreateDateDesc(@Param("jiraIdx") Integer jiraIdx, @Param("CreateDate") LocalDateTime date);

	// kdw
	List<ProjectLogData> findByIssue_ProjectIdxAndCreateDateGreaterThanEqualOrderByCreateDateDesc(@Param("projectIdx") Integer projectIdx, @Param("CreateDate") LocalDateTime date);
	
	List<ProjectLogData> findByIssueIdxOrderByCreateDateAsc(Integer idx);
	
	List<ProjectLogData> findByIssueIdxOrderByCreateDateDesc(Integer idx);
	
}
