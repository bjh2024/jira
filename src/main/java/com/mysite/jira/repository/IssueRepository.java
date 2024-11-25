package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Integer>{
	
	List<Issue> findIssuesByProjectIdx(@Param("projectIdx") Integer projectIdx);

    // Jira의 idx를 기준으로 Issue를 조회하고, 관련된 IssueType을 join하며, clickedDate로 정렬하고, 페이징 처리
	List<Issue> findByJiraIdxAndAccount_NameOrderByClickedDateDesc(@Param("jiraIdx") Integer jiraIdx,
            													   @Param("name") String name);




}
