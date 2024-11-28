package com.mysite.jira.dto.mywork;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecentProjectDTO {
	
	private String name;
	private String color;
	private String iconFilename;
	private Integer issueCount;
	
	@Builder
	public RecentProjectDTO(String name, String color, String iconFilename, Integer issueCount) {
		this.name = name;
		this.color = color;
		this.iconFilename = iconFilename;
		this.issueCount = issueCount;
	}
	
}
