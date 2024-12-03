package com.mysite.jira.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueType;
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
	
	
	@Query("""
			SELECT DISTINCT a.idx, a.name
			FROM Issue i left JOIN Account a
			on i.manager.idx = a.idx
			where i.jira.idx = :jiraIdx
			""")
	List<Object[]> findManagerIdxAndNameByJiraIdx(@Param("jiraIdx") Integer jiraIdx);
	
	
	@Query("""
			SELECT i
			FROM Issue i
			WHERE manager.idx is null
			or manager.idx in :managerIdxArr
			""")
	List<Issue> findByManagerIdxNullIn(@Param("managerIdxArr") List<Integer> managerIdxArr);
	
	// bjh
	List<Issue> findByIssueTypeNameIn(String[] name);
	
	//bjh
	List<Issue> findByIssueStatusNameIn(String[] name);
	
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
    // bjh
}
