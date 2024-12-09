package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ProjectMembers;

public interface ProjectMembersRepository extends JpaRepository<ProjectMembers, Integer>{
	List<ProjectMembers> findAllByProjectIdx(Integer projectIdx);
	
	List<ProjectMembers> findAllByProjectIdxAndAccountIdxNot(Integer projectIdx, Integer accountIdx);
}
