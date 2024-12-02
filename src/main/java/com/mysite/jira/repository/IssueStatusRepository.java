package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.IssueStatus;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer>{
	@Query("SELECT s.status, count(s.idx), s.idx, s.name "
			+ "FROM Issue i LEFT JOIN IssueStatus s ON i.issueStatus.idx = s.idx "
			+ "WHERE i.project.idx = :idx "
			+ "GROUP BY s.idx, s.name, s.status, s.divOrder ORDER BY s.divOrder") 
	List<Object[]> findGroupByIssueStatusWithJPQL(@Param("idx") Integer idx);
	
	// jira가 1인 issueStatus의 모든 목록을 distinct로 추출
	@Query("""
			SELECT distinct iss.name, iss.status
			FROM IssueStatus iss JOIN Project p 
			on iss.project.idx = p.idx
			where p.jira.idx = :idx
			""")
	List<Object[]> findDistinctNameAndStatusByJiraIdx(@Param("idx") Integer idx);
	
	List<IssueStatus> findAllByProjectIdxOrderByStatusAsc(Integer idx);
}
