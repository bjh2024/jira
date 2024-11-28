package com.mysite.jira.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.ProjectLogData;

public interface ProjectLogDataRepository extends JpaRepository<ProjectLogData, Integer> {
	
	// kdw
	List<ProjectLogData> findByIssue_jiraIdxAndCreateDateGreaterThanEqualOrderByCreateDateDesc(@Param("jiraIdx") Integer jiraIdx, @Param("CreateDate") LocalDateTime date);

	// kdw
	@Query(value="""
			SELECT  i.name,
			        i.key,
			        p.name,
			        a.name,
			        a.icon_filename,
			        DECODE(pld.project_log_status_idx, 11, '만듦', '업데이트') is_create
			FROM    project_log_data pld
			JOIN    issue i
			ON  i.idx = pld.issue_idx
			JOIN    jira j
			ON  j.idx = i.jira_idx
			JOIN    account a
			ON  a.idx = pld.account_idx
			JOIN    project p 
			ON  p.idx = i.project_idx
			WHERE j.idx = :jiraIdx
			AND pld.create_date BETWEEN :startDate AND :endDate
			GROUP BY i.name, i.key, p.name, a.name, a.icon_filename, DECODE(pld.project_log_status_idx, 11, '만듦', '업데이트');
			""", nativeQuery=true)
	List<Map<String, Object>> findAllDetailsByJiraIdxAndDateBetween(@Param("jiraIdx") Integer jiraIdx, 
																	@Param("startDate") LocalDateTime startDate,
																	@Param("endDate") LocalDateTime endDate);

}
