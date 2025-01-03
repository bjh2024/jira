package com.mysite.jira.dto.chatroom;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomListAccountDTO {

	private Integer idx;
	private String email;
	private String name;
	private String iconFilename;
	
	@Builder
	public ChatRoomListAccountDTO(Integer idx, String email, String name, String iconFilename) {
		this.idx = idx;
		this.email = email;
		this.name = name;
		this.iconFilename = iconFilename;
	}
}
