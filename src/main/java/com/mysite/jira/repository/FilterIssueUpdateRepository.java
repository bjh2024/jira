package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterIssueUpdate;

public interface FilterIssueUpdateRepository extends JpaRepository<FilterIssueUpdate, Integer>{

	FilterIssueUpdate findByFilterIdx(Integer idx); 
}
