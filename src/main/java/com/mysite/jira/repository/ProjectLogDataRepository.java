package com.mysite.jira.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.ProjectLogData;

public interface ProjectLogDataRepository extends JpaRepository<ProjectLogData, Integer> {
	
	// kdw
	List<ProjectLogData> findByIssue_jiraIdxAndCreateDateGreaterThanEqualOrderByCreateDateDesc(@Param("jiraIdx") Integer jiraIdx, @Param("CreateDate") LocalDateTime date);

}
