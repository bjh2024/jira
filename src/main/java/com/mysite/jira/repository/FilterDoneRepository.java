package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterDone;

public interface FilterDoneRepository extends JpaRepository<FilterDone, Integer>{

	List<FilterDone> findByFilterIdx(Integer idx);
}
