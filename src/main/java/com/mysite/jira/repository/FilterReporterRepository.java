package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterReporter;

public interface FilterReporterRepository extends JpaRepository<FilterReporter, Integer>{

	List<FilterReporter> findByFilterIdx(Integer idx);
}
