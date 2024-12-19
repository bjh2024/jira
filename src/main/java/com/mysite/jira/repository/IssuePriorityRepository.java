package com.mysite.jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssuePriority;

public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Integer>{

	Optional<IssuePriority> findByName(String name);
	
	List<IssuePriority> findAllByOrderByIdxDesc(); 
	
	List<IssuePriority> findAllByIdxNotOrderByIdxDesc(Integer idx); 
}
