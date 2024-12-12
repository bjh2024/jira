package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterIssueType;

public interface FilterIssueTypeRepository extends JpaRepository<FilterIssueType, Integer>{

	List<FilterIssueType> findByFilterIdx(Integer idx);	
}
