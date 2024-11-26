package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	
}
