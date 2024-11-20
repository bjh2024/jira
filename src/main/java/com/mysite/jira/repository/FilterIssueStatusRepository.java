package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterIssueStatus;

public interface FilterIssueStatusRepository extends JpaRepository<FilterIssueStatus, Integer>{

}
