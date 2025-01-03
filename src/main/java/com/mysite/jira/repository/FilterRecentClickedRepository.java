package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterRecentClicked;

public interface FilterRecentClickedRepository extends JpaRepository<FilterRecentClicked, Integer> {

	FilterRecentClicked findByFilterIdxAndAccountIdx(Integer filterIdx, Integer accountIdx);
}
