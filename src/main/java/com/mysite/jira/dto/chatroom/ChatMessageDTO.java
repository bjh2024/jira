package com.mysite.jira.dto.chatroom;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageDTO {
	
	private Integer chatRoomIdx;
	private Integer accountIdx;
	private String accountIconFilename;
	private String content;
	private LocalDateTime sendDate;

	@Builder
	public ChatMessageDTO(Integer chatRoomIdx, Integer accountIdx, String accountIconFilename, String content, LocalDateTime sendDate) {
		this.chatRoomIdx = chatRoomIdx;
		this.accountIdx = accountIdx;
		this.accountIconFilename = accountIconFilename;
		this.content = content;
		this.sendDate = sendDate;
	}
}
