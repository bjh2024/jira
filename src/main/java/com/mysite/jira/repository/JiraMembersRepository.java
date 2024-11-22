package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.JiraMembers;

public interface JiraMembersRepository extends JpaRepository<JiraMembers, Integer>{
	
}
