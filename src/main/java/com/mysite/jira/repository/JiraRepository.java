package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Jira;

public interface JiraRepository extends JpaRepository<Jira, Integer> {

	// account_idx로 name(jira 리더) 가져오기
	@Query(value = "SELECT j.name FROM jira j JOIN jira_members jm ON j.idx = jm.jira_idx "
			+ "WHERE jm.account_idx = :accountIdx", nativeQuery = true)
	List<String> findJiraAndMembersByAccountIdxName(@Param("accountIdx") Integer accountIdx);
}
