package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

}
