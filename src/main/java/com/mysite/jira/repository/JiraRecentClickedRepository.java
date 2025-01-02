package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.JiraRecentClicked;

public interface JiraRecentClickedRepository extends JpaRepository<JiraRecentClicked, Integer>{
	
	JiraRecentClicked findByJira_idxAndAccount_idx(Integer jiraIdx, Integer accountIdx);

}
