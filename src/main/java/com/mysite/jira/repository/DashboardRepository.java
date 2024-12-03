package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Dashboard;

public interface DashboardRepository extends JpaRepository<Dashboard,Integer>{
	// kdw
	List<Dashboard> findByDashClickedList_AccountIdxAndJiraIdxOrderByDashClickedList_ClickedDateDesc(
					 @Param("accountIdx") Integer accountIdx,
					 @Param("jiraIdx") Integer jiraIdx);
	
}
