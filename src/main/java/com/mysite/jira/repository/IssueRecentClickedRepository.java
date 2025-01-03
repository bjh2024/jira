package com.mysite.jira.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueRecentClicked;

public interface IssueRecentClickedRepository extends JpaRepository<IssueRecentClicked, Integer>{
	Optional<IssueRecentClicked> findByJiraIdxAndAccountIdxAndIssueIdx(Integer jiraIdx, Integer accountIdx, Integer issueIdx);
	
	Optional<IssueRecentClicked> findByJiraIdxAndAccountIdx(Integer jiraIdx, Integer accountIdx);
}
