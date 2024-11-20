package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueLabelData;

public interface IssueLabelDataRepository extends JpaRepository<IssueLabelData, Integer>{

}
