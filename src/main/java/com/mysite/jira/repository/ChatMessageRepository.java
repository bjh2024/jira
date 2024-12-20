package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
	
	// kdw 가장 최근에 친 채팅메세지
	@Query(value="""
				SELECT 	*
				FROM (SELECT	*
					  FROM	chat_message
					  WHERE	chat_room_idx = :chatRoomIdx
					  ORDER BY send_date DESC)
				WHERE rownum = 1
			""", nativeQuery=true)
	ChatMessage findTop1ByChatRoomIdx(@Param("chatRoomIdx") Integer chatRoomIdx);
	
	List<ChatMessage> findByChatRoom_IdxOrderBySendDate(Integer chatRoomIdx);

}
