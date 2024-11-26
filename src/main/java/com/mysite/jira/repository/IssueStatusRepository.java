package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.IssueStatus;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer>{
	@Query("SELECT s.name, count(s.idx), s.idx, s.status "
			+ "FROM Issue i LEFT JOIN IssueStatus s ON i.issueStatus.idx = s.idx "
			+ "WHERE i.project.idx = :idx "
			+ "GROUP BY s.idx, s.name, s.status, s.divOrder ORDER BY s.divOrder") 
	List<Object[]> findGroupByIssueStatusWithJPQL(@Param("idx") Integer idx);
	

}
