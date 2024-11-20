package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

}
