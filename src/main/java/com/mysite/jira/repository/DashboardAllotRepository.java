package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardAllot;

public interface DashboardAllotRepository extends JpaRepository<DashboardAllot, Integer>{
	
	List<DashboardAllot> findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(Integer dashboardIdx, Integer divOrderX, Integer divOrderY);
}
