package com.mysite.jira.dto.board;

import lombok.Getter;

@Getter
public class ClickedIssueDTO {
	private Integer jiraIdx;
	private Integer userIdx;
	private Integer issueIdx;
}
