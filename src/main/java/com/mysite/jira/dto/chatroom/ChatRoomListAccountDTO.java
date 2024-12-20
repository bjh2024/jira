package com.mysite.jira.dto.chatroom;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomListAccountDTO {

	private String name;
	private String iconFilename;
	
	@Builder
	public ChatRoomListAccountDTO(String name, String iconFilename) {
		this.name = name;
		this.iconFilename = iconFilename;
	}
}
