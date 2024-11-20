package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueRecord;

public interface IssueRecordRepository extends JpaRepository<IssueRecord, Integer>{

}
