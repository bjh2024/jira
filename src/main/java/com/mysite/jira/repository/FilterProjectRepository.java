package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.FilterProject;

public interface FilterProjectRepository extends JpaRepository<FilterProject, Integer> {

}
