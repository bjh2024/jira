package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ChatMembers;

public interface ChatMembersRepository extends JpaRepository<ChatMembers, Integer> {

}
