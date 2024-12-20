package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ChatMembers;

public interface ChatMembersRepository extends JpaRepository<ChatMembers, Integer> {
	List<ChatMembers> findByAccount_Idx(Integer accountIdx);
	
	List<ChatMembers> findByChatRoom_Idx(Integer chatRoomIdx);
}
