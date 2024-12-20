package com.mysite.jira.dto.chatroom;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomListDTO {

	private List<ChatRoomListAccountDTO> accountList;
	private ChatRoomListChatRoomDTO chatRoom;
	private String lastContent;
	private LocalDateTime lastSendDate;
	
	@Builder
	public ChatRoomListDTO(List<ChatRoomListAccountDTO> accountList, 
						   ChatRoomListChatRoomDTO chatRoom, 
						   String lastContent, 
						   LocalDateTime lastSendDate) {
		this.accountList = accountList;
		this.chatRoom = chatRoom;
		this.lastContent = lastContent;
		this.lastSendDate = lastSendDate;
	}
}
