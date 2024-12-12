package com.mysite.jira.dto.board;

import lombok.Getter;

@Getter
public class CreateIssueDTO {
	private String jiraName;
	private Integer projectIdx;
	private Integer issueTypeIdx;
	private Integer reporterIdx;
	private Integer statusIdx;
	private String issueName;
}
