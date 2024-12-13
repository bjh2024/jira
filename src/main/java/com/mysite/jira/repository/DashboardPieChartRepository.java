package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardPieChart;

public interface DashboardPieChartRepository extends JpaRepository<DashboardPieChart, Integer>{
			
	List<DashboardPieChart> findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(Integer dashboardIdx, Integer divOrderX, Integer divOrderY);
}
