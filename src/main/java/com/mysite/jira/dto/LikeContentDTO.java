package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeContentDTO {
	
	private String type;
	private Integer idx;
	private String jiraName;
	private String name;
	private String iconFilename;
	private String key;
	
	@Builder
	public LikeContentDTO(String type, Integer idx, String jiraName, String name, String iconFilename, String key) {
		this.type = type;
		this.idx = idx;
		this.jiraName = jiraName;
		this.name = name;
		this.iconFilename = iconFilename;
		this.key = key;
	}
}
