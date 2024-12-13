package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardIssueFilter;

public interface DashboardIssueFilterRepository extends JpaRepository<DashboardIssueFilter, Integer> {
	List<DashboardIssueFilter> findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(Integer dashboardIdx, Integer divOrderX, Integer divOrderY);

}
