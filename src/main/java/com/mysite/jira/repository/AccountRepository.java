package com.mysite.jira.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	Optional<Account> findByEmail(String email);
}
