package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ProjectMembers;

public interface ProjectMembersRepository extends JpaRepository<ProjectMembers, Integer>{

}
