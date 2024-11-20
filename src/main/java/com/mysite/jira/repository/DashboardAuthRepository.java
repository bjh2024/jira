package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.DashboardAuth;

public interface DashboardAuthRepository extends JpaRepository<DashboardAuth, Integer>{

}
