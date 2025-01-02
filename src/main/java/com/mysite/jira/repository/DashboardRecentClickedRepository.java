package com.mysite.jira.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardRecentClicked;

public interface DashboardRecentClickedRepository extends JpaRepository<DashboardRecentClicked, Integer>{
	
	DashboardRecentClicked findByDashboard_IdxAndAccount_Idx(Integer dashboardIdx, Integer accoutIdx);
}
