package com.mysite.jira.dto.board;

import lombok.Getter;

@Getter
public class DragIssueBoxDTO {
	private Integer oldIdx;
	private Integer newIdx;
	private Integer projectIdx;
}
