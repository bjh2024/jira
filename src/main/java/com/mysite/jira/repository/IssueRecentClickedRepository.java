package com.mysite.jira.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueRecentClicked;

public interface IssueRecentClickedRepository extends JpaRepository<IssueRecentClicked, Integer>{

}
