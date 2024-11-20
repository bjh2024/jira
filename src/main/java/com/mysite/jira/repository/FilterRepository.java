package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.Filter;

public interface FilterRepository extends JpaRepository<Filter, Integer>{

}
