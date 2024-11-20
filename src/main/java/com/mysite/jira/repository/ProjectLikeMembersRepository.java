package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ProjectLikeMembers;

public interface ProjectLikeMembersRepository extends JpaRepository<ProjectLikeMembers, Integer>{

}
