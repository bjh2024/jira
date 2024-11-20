package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueObserverMembers;

public interface IssueObserverMembersRepository extends JpaRepository<IssueObserverMembers, Integer>{

}
