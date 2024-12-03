package com.mysite.jira.dto.mywork;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecentProjectDTO {
	
	private String name;
	private String color;
	private String iconFilename;
	private String key;
	private Integer issueCount;
	
	@Builder
	public RecentProjectDTO(String name, String color, String iconFilename, String key, Integer issueCount) {
		this.name = name;
		this.color = color;
		this.iconFilename = iconFilename;
		this.key = key;
		this.issueCount = issueCount;
	}
	
}
