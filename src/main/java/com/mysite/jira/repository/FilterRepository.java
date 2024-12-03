package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Filter;

public interface FilterRepository extends JpaRepository<Filter, Integer>{
	// kdw
	List<Filter> findByFilterClickedList_AccountIdxAndJiraIdxOrderByFilterClickedList_ClickedDateDesc(
																							@Param("accountIdx") Integer accountIdx, 
																							@Param("jiraIdx") Integer jiraIdx);
	
}
