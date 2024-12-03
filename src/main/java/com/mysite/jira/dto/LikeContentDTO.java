package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeContentDTO {
	private String name;
	private String iconFilename;
	private String projectKey;
	
	@Builder
	public LikeContentDTO(String name, String iconFilename, String projectKey) {
		this.name = name;
		this.iconFilename = iconFilename;
		this.projectKey = projectKey;
	}
}
