package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueStatus;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer>{

}
