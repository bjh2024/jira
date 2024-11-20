package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Integer>{

}
