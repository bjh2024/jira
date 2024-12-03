package com.mysite.jira.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AllRecentDTO {
	
	private String iconFilename;
	private String name;
	private String projectName;
	private String key;
	private LocalDateTime elapsedTime;

	@Builder
	public AllRecentDTO(String iconFilename, String name, String projectName, String key, LocalDateTime elapsedTime) {
		this.iconFilename = iconFilename;
		this.name = name;
		this.projectName = projectName;
		this.key = key;
		this.elapsedTime = elapsedTime;
	}
}
