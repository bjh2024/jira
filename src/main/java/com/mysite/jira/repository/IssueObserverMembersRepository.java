package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueObserverMembers;

public interface IssueObserverMembersRepository extends JpaRepository<IssueObserverMembers, Integer>{
	List<IssueObserverMembers> findByIssueIdx(Integer idx);
	List<IssueObserverMembers> findByIssueIdxAndAccountIdx(Integer issueIdx, Integer userIdx);
}
