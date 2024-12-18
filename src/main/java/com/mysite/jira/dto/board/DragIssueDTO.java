package com.mysite.jira.dto.board;

import lombok.Getter;

@Getter
public class DragIssueDTO {
	private Integer issueIdx;
	private Integer statusIdx;
	private Integer oldStatusIdx;
	private Integer oldIdx;
	private Integer newIdx;
}
