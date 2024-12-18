package com.mysite.jira.dto.board;

import lombok.Getter;

@Getter
public class CreateStatusDTO {
	private String name;
	private Integer status;
	private Integer projectIdx;
}
