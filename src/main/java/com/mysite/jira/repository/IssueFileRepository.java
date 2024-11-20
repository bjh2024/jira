package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueFile;

public interface IssueFileRepository extends JpaRepository<IssueFile, Integer>{

}
