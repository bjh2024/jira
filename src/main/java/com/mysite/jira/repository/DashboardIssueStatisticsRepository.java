package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardIssueStatistics;

public interface DashboardIssueStatisticsRepository extends JpaRepository<DashboardIssueStatistics, Integer>{

}
