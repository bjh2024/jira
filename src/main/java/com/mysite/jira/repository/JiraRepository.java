package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Jira;

public interface JiraRepository extends JpaRepository<Jira, Integer>{

}
