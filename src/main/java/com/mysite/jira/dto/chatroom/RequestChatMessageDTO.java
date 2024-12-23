package com.mysite.jira.dto.chatroom;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestChatMessageDTO {
	
	private Integer chatRoomIdx;
	private Integer accountIdx;
	private String content;

	@Builder
	public RequestChatMessageDTO(Integer chatRoomIdx, Integer accountIdx, String content) {
		this.chatRoomIdx = chatRoomIdx;
		this.accountIdx = accountIdx;
		this.content = content;
	}
}
