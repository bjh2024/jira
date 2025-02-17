package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardIssueComplete;

public interface DashboardIssueCompleteRepository extends JpaRepository<DashboardIssueComplete, Integer>{
	List<DashboardIssueComplete> findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(Integer dashboardIdx, Integer divOrderX, Integer divOrderY);
}
