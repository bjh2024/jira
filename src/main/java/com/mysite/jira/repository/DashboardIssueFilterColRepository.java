package com.mysite.jira.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardIssueFilterCol;

public interface DashboardIssueFilterColRepository extends JpaRepository<DashboardIssueFilterCol, Integer>{
}
