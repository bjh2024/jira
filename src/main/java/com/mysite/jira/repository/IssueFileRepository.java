package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueFile;

public interface IssueFileRepository extends JpaRepository<IssueFile, Integer>{
	List<IssueFile> findByIssueIdx(Integer idx);
	
	List<IssueFile> findByIssueProjectIdx(Integer idx);
}
