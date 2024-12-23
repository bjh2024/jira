package com.mysite.jira.dto.chatroom;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessageListDTO {

	private Integer accountIdx;
	private String accountIconFilename;
	private String content;
	private LocalDateTime sendDate;

	@Builder
	public ChatMessageListDTO(Integer accountIdx, String accountIconFilename, String content, LocalDateTime sendDate) {
		this.accountIdx = accountIdx;
		this.accountIconFilename = accountIconFilename;
		this.content = content;
		this.sendDate = sendDate;
	}
	
}
