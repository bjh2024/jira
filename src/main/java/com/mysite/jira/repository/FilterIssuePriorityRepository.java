package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterIssuePriority;

public interface FilterIssuePriorityRepository extends JpaRepository<FilterIssuePriority, Integer>{

	List<FilterIssuePriority> findByFilterIdx(Integer idx);
}
