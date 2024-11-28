package com.mysite.jira.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.Project;

public interface IssueRepository extends JpaRepository<Issue, Integer>{
	
	List<Issue> findIssuesByProjectIdx(Integer idx); 
	// kdw
	List<Issue> findByIssueClickedList_AccountIdxAndJiraIdxOrderByIssueClickedList_ClickedDateDesc(
			 @Param("accountIdx") Integer accountIdx, 
			 @Param("jiraIdx") Integer jiraIdx);
	// kdw
	List<Issue> findByJiraIdx(Integer jiraIdx);
	// kdw
	List<Issue> findByEditDateBetween(LocalDateTime startDate, LocalDateTime endDate);
	// kdw
	List<Issue> findByProjectIdxIn(Integer[] projectIdxArr);
	// kdw
	List<Issue> findByManagerIdxIn(Integer[] managerIdxArr);
	// kdw
	List<Issue> findByReporterIdx(Integer reporterIdx);
	// kdw
    List<Issue> findByIssueStatusStatusIn(Integer[] statusArr);
	// kdw
    @Query("""
    	    SELECT i
    	    FROM ProjectLogData pld
    	    JOIN pld.issue i
    	    WHERE pld.createDate IN (
    	        SELECT MAX(pld2.createDate)
    	        FROM ProjectLogData pld2
    	        WHERE pld2.issue.idx = pld.issue.idx
    	        GROUP BY pld2.issue.idx
    	        HAVING MAX(pld2.createDate) BETWEEN :startDate AND :endDate
    	    )
    	    AND i.jira.idx = :jiraIdx
    	    ORDER BY pld.createDate DESC
    	""")
	List<Issue> IssueByJiraIdxAndCreateDateBetweenOrderByCreateDateDesc(@Param("jiraIdx") Integer jiraIdx, 
												    	    @Param("startDate") LocalDateTime startDate, 
												    	    @Param("endDate") LocalDateTime endDate);
	

}
