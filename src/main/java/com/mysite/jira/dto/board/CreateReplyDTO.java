package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateReplyDTO {
	private Integer issueIdx;
	private String content;
	private Integer writerIdx;
	
	private Integer replyIdx;
	private String name;
	private String iconFilename;
	private String date;
	
	@Builder
	public CreateReplyDTO(Integer replyIdx, String name, String iconFilename, String content, String date) {
		this.replyIdx = replyIdx;
		this.name = name;
		this.iconFilename = iconFilename;
		this.content = content;
		this.date = date;
	}
}
