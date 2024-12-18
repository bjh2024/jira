package com.mysite.jira.dto.project.update;

import lombok.Getter;

@Getter
public class RequestUpdateDTO {
	private String projectName;
	private String projectKey;
	private Integer projectIdx;
	private Integer leaderIdx;
}
