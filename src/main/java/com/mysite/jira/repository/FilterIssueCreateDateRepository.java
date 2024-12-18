package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterIssueCreateDate;

public interface FilterIssueCreateDateRepository extends JpaRepository<FilterIssueCreateDate, Integer>{

	FilterIssueCreateDate findByFilterIdx(Integer idx);
}
