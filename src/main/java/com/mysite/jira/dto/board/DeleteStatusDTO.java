package com.mysite.jira.dto.board;

import lombok.Getter;

@Getter
public class DeleteStatusDTO {
	private Integer projectIdx;
	private Integer statusIdx;
	private Integer newStatusIdx;
}
