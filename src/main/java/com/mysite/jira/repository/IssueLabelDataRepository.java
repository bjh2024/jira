package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueLabelData;

public interface IssueLabelDataRepository extends JpaRepository<IssueLabelData, Integer>{
	List<IssueLabelData> findAll();
	List<IssueLabelData> findDistinctByIssueLabelIdxNot(Integer idx);
}
