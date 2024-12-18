package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueReply;

public interface IssueReplyRepository extends JpaRepository<IssueReply, Integer>{
	List<IssueReply> findAllByOrderByCreateDateDesc();
	
	List<IssueReply> findByIssueProjectIdxOrderByCreateDateDesc(Integer idx);
}
