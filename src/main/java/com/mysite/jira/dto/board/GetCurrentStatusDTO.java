package com.mysite.jira.dto.board;

import lombok.Getter;

@Getter
public class GetCurrentStatusDTO {
	private Integer projectIdx;
	private Integer statusIdx;
}
