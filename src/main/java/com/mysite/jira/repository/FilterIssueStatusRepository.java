package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterIssueStatus;

public interface FilterIssueStatusRepository extends JpaRepository<FilterIssueStatus, Integer>{

	List<FilterIssueStatus> findByFilterIdx(Integer idx);
}
