package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterManager;

public interface FilterManagerRepository extends JpaRepository<FilterManager, Integer>{

	List<FilterManager> findByFilterIdx(Integer idx);
}
