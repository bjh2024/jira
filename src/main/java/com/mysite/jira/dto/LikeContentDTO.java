package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeContentDTO {
	
	private Integer idx;
	private String jiraName;
	private String name;
	private String iconFilename;
	private String key;
	
	@Builder
	public LikeContentDTO(Integer idx, String jiraName, String name, String iconFilename, String key) {
		this.idx = idx;
		this.jiraName = jiraName;
		this.name = name;
		this.iconFilename = iconFilename;
		this.key = key;
	}
}
