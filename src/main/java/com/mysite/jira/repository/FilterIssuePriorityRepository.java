package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterIssuePriority;

public interface FilterIssuePriorityRepository extends JpaRepository<FilterIssuePriority, Integer>{

}
