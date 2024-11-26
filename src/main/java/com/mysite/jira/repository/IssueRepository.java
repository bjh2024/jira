package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueType;

public interface IssueRepository extends JpaRepository<Issue, Integer>{
	List<Issue> findIssuesByProjectIdx(Integer idx);

	List<Issue> findByJiraIdx(Integer jiraIdx);

}
