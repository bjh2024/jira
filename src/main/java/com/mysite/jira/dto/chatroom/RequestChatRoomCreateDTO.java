package com.mysite.jira.dto.chatroom;

import lombok.Getter;

@Getter
public class RequestChatRoomCreateDTO {

	private String name;
	private Integer[] chatAccountIdxList;
	
}
