package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardIssueRecent;

public interface DashboardIssueRecentRepository extends JpaRepository<DashboardIssueRecent, Integer>{

}
