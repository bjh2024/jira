package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardLikeMembers;

public interface DashboardLikeMembersRepository extends JpaRepository<DashboardLikeMembers,Integer>{

}
