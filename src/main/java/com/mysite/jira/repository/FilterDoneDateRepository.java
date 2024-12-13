package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterDoneDate;

public interface FilterDoneDateRepository extends JpaRepository<FilterDoneDate, Integer>{

	List<FilterDoneDate> findByFilterIdx(Integer idx);
}
