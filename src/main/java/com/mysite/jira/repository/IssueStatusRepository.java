package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.IssueStatus;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer>{
	@Query("SELECT s.status, COUNT(i.idx) AS issueCount, s.idx, s.name "
			+ "FROM IssueStatus s LEFT JOIN Issue i ON i.issueStatus.idx = s.idx "
			+ "WHERE s.project.idx = :idx "
			+ "GROUP BY s.idx, s.name, s.status, s.divOrder ORDER BY s.divOrder") 
	List<Object[]> findGroupByIssueStatusWithJPQL(@Param("idx") Integer idx);
	
	List<IssueStatus> findAllByProjectIdxOrderByStatusAsc(Integer idx);
	
	List<IssueStatus> findAllByProjectIdxAndIdxNotOrderByStatusAsc(Integer projectIdx, Integer idx);
}
