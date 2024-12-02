package com.mysite.jira.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.IssueStatus;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer>{
	@Query("SELECT s.name, count(s.idx), s.idx "
			+ "FROM Issue i LEFT JOIN IssueStatus s ON i.issueStatus.idx = s.idx "
			+ "WHERE i.project.idx = :idx "
			+ "GROUP BY s.idx, s.name, s.status, s.divOrder ORDER BY s.divOrder") 
	List<Object[]> findGroupByIssueStatusWithJPQL(@Param("idx") Integer idx);
	
//	// kdw 보류
//	List<IssueStatus> findByIssueList_JiraIdxAndIssueList_ManagerIdxAndStatusIn(Integer jiraIdx, Integer managerIdx, Integer[] statusArr);
	
	// kdw
	@Query("""
			SELECT  ist.name as name, 
					count(i.idx) as count
			FROM    IssueStatus ist
			LEFT JOIN    Issue i
			ON  ist.idx = i.issueStatus.idx
			WHERE   ist.project.idx = :projectIdx
			GROUP BY ist.name
			""")
	List<Map<String, Object>> findByStatusByIssueCount(@Param("projectIdx") Integer projectIdx);
}
