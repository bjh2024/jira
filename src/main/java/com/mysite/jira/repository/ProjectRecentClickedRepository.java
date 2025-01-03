package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ProjectRecentClicked;

public interface ProjectRecentClickedRepository extends JpaRepository<ProjectRecentClicked, Integer>{
	
	ProjectRecentClicked findByProject_idxAndAccount_idx(Integer projectIdx, Integer accountIdx);
}
