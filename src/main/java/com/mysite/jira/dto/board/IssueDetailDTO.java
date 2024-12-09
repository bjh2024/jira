package com.mysite.jira.dto.board;

import com.mysite.jira.entity.ChatMessage;

import lombok.Getter;

@Getter
public class IssueDetailDTO {
	private ChatMessage[] chatMessageList;
}
