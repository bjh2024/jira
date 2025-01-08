package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AllRecentDTO {
	
	private String type;
	private Integer idx;
	private String iconFilename;
	private String name;
	private String accountName;
	private String projectName;
	private String key;
	private String elapsedTime;
	
	@Builder
	public AllRecentDTO(String type, Integer idx, String iconFilename, String name, String accountName, String projectName, String key, String elapsedTime) {
		this.type = type;
		this.idx = idx;
		this.iconFilename = iconFilename;
		this.name = name;
		this.accountName = accountName;
		this.projectName = projectName;
		this.key = key;
		this.elapsedTime = elapsedTime;
	}
	
}
