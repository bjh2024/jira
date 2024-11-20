package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.TeamMembers;

public interface TeamMembersRepository extends JpaRepository<TeamMembers, Integer>{

}
