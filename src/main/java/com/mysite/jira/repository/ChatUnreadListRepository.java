package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ChatUnreadList;

public interface ChatUnreadListRepository extends JpaRepository<ChatUnreadList, Integer> {

}
