package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueLogDTO {
	private Integer issueIdx;
	private String order;
	
	private String username;
	private String iconFilename;
	private String logType;
	private String date;
	
	@Builder
	public IssueLogDTO(String username, String iconFilename, String logType, String date) {
		this.username = username;
		this.iconFilename = iconFilename;
		this.logType = logType;
		this.date = date;
	}
}
