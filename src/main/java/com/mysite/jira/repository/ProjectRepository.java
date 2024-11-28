package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	List<Project> findByJiraIdx(Integer jiraIdx);

	// 지라가 1인 프로젝트의 모든 유형을 가져와서 distinct
	@Query("SELECT DISTINCT iss.status, iss.name "
		     + "FROM IssueStatus iss "
		     + "WHERE iss.project.idx IN ( "
		     + "    SELECT p.idx "
		     + "    FROM Project p "
		     + "    WHERE p.jira.idx = :jiraIdx )"
		     + "    ORDER BY iss.status Desc" )
		List<Object[]> findDistinctStatusAndNameByJiraIdx(@Param("jiraIdx") Integer jiraIdx);

		List<Project> findByKey(String keys); 

}
