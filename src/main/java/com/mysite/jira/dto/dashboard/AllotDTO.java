package com.mysite.jira.dto.dashboard;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AllotDTO {
	private String iconFilename;
	private String key;
	private String name;
	private String priorityIconFilename;
	
	@Builder
	public AllotDTO(String iconFilename, String key, String name, String priorityIconFilename) {
		this.iconFilename = iconFilename;
		this.key = key;
		this.name = name;
		this.priorityIconFilename = priorityIconFilename;
	}
}
