package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Integer>{
	List<Issue> findIssuesByProjectIdx(Integer idx);

	List<Issue> findByJiraIdx(Integer jiraIdx);

}
