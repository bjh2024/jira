package com.mysite.jira.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.IssuePriority;

public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Integer>{

	List<IssuePriority> findAllByOrderByIdxDesc(); 
	
	List<IssuePriority> findAllByIdxNotOrderByIdxDesc(Integer idx); 
}
