package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardIssueFilter;

public interface DashboardIssueFilterRepository extends JpaRepository<DashboardIssueFilter, Integer> {

}