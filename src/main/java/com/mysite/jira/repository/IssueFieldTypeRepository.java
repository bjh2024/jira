package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueFieldType;

public interface IssueFieldTypeRepository extends JpaRepository<IssueFieldType, Integer>{

}
