package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.FilterProject;

public interface FilterProjectRepository extends JpaRepository<FilterProject, Integer> {

	public List<FilterProject> findByFilterIdx(Integer idx);
}
