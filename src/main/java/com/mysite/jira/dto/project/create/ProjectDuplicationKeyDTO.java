package com.mysite.jira.dto.project.create;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectDuplicationKeyDTO {

	private Integer count;
	private String projectName;

	@Builder
	public ProjectDuplicationKeyDTO(Integer count, String projectName) {
		this.count = count;
		this.projectName = projectName;
	}
}
