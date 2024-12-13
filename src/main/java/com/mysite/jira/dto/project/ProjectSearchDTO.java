package com.mysite.jira.dto.project;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectSearchDTO {

	private String name;
	private String iconFilename;
	private String key;

	@Builder
	public ProjectSearchDTO(String name, String iconFilename, String key) {
		this.name = name;
		this.iconFilename = iconFilename;
		this.key = key;
	}
}
