package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardPieChart;

public interface DashboardPieChartRepository extends JpaRepository<DashboardPieChart, Integer>{

}
