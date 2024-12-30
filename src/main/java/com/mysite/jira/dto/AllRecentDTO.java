package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AllRecentDTO {
	
	private String iconFilename;
	private String name;
	private String projectName;
	private String key;
	private String elapsedTime;
	
	@Builder
	public AllRecentDTO(String iconFilename, String name, String projectName, String key, String elapsedTime) {
		this.iconFilename = iconFilename;
		this.name = name;
		this.projectName = projectName;
		this.key = key;
		this.elapsedTime = elapsedTime;
	}
	
}
