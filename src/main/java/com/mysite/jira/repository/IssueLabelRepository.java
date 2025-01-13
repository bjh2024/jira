package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueLabel;

public interface IssueLabelRepository extends JpaRepository<IssueLabel, Integer>{
	List<IssueLabel> findByJiraIdx(Integer jiraIdx);
}
