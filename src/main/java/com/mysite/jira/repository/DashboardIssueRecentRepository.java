package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardIssueFilter;
import com.mysite.jira.entity.DashboardIssueRecent;

public interface DashboardIssueRecentRepository extends JpaRepository<DashboardIssueRecent, Integer>{
	List<DashboardIssueRecent> findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(Integer dashboardIdx, Integer divOrderX, Integer divOrderY);

}
