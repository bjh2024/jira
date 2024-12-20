package com.mysite.jira.dto.chatroom;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomListChatRoomDTO {

	private Integer idx;
	private String name;

	@Builder
	public ChatRoomListChatRoomDTO(Integer idx, String name) {
		this.idx = idx;
		this.name = name;
	}
}
