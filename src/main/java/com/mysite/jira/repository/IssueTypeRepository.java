package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueType;

public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {
	List<IssueType> findByProjectIdxAndGradeGreaterThan(Integer projectIdx, Integer grade);
}
