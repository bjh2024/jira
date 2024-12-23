package com.mysite.jira.dto.chatroom;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseChatMessageDTO {

	private Integer accountIdx;
	private String iconFilename;
	private String content;
	private LocalDateTime sendDate;
	
	@Builder
	public ResponseChatMessageDTO(Integer accountIdx, String iconFilename, String content, LocalDateTime sendDate) {
		this.accountIdx = accountIdx;
		this.iconFilename = iconFilename;
		this.content = content;
		this.sendDate = sendDate;
	}
	
}
