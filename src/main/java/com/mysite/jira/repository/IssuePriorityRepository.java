package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssuePriority;

public interface IssuePriorityRepository extends JpaRepository<IssuePriority, String>{

}
