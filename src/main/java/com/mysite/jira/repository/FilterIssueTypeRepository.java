package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterIssueType;

public interface FilterIssueTypeRepository extends JpaRepository<FilterIssueType, Integer>{

}