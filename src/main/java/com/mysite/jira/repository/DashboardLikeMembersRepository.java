package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.entity.DashboardLikeMembers;

public interface DashboardLikeMembersRepository extends JpaRepository<DashboardLikeMembers,Integer>{
	// kdw
	List<DashboardLikeMembers> findByDashboard_jiraIdxAndAccountIdx(@Param("accountIdx") Integer accountIdx, @Param("jiraIdx") Integer jiraIdx);
	// kdw
	DashboardLikeMembers findByAccountIdxAndDashboardIdx(@Param("accountIdx") Integer accountIdx, @Param("dashboardIdx") Integer dashboardIdx);
}
