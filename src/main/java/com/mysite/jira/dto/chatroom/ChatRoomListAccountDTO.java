package com.mysite.jira.dto.chatroom;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomListAccountDTO {

	private Integer idx;
	private String name;
	private String iconFilename;
	
	@Builder
	public ChatRoomListAccountDTO(Integer idx, String name, String iconFilename) {
		this.idx = idx;
		this.name = name;
		this.iconFilename = iconFilename;
	}
}
