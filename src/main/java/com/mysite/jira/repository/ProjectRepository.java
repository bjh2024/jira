package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	List<Project> findByJiraIdx(Integer jiraIdx);

}
