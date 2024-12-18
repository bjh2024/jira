package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ProjectLogStatus;

public interface ProjectLogStatusRepository extends JpaRepository<ProjectLogStatus, Integer>{

}
