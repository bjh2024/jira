package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.JiraMembers;

public interface JiraMembersRepository extends JpaRepository<JiraMembers, Integer>{
	List<JiraMembers> findByJiraIdx(Integer idx);
	
	JiraMembers findByJiraIdxAndAccount_Email(Integer jiraIdx, String email);
	
	// kdw account_idx로 name(jira 리더) 가져오기
	List<JiraMembers> findByAccountIdx(Integer accountIdx);
}
