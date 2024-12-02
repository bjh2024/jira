package com.mysite.jira.dto.board;

import com.mysite.jira.entity.IssueStatus;

import lombok.Getter;

@Getter
public class UpdateStatusDTO {
	private Integer projectIdx;
	private Integer issueIdx;
	private Integer issueStatusIdx;
}
