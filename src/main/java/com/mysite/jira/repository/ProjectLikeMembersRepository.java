package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.ProjectLikeMembers;

public interface ProjectLikeMembersRepository extends JpaRepository<ProjectLikeMembers, Integer>{

	List<ProjectLikeMembers> findByProject_jiraIdxAndAccountIdx(@Param("accountIdx") Integer accountIdx,@Param("jiraIdx") Integer jiraIdx);
}
