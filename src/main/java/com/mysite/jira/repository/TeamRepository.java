package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Integer>{

}
