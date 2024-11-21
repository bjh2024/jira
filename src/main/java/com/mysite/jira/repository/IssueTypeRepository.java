package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueType;

public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {
}
