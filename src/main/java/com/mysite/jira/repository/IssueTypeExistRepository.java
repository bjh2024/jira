package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueTypeExist;

public interface IssueTypeExistRepository extends JpaRepository<IssueTypeExist, Integer>{

}
