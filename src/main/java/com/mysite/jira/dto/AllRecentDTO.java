package com.mysite.jira.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AllRecentDTO {
	
	private String iconFilename;
	private String name;
	private String elapsedTime;
	
	@Builder
	public AllRecentDTO(String iconFilename, String name, String elapsedTime) {
		this.iconFilename = iconFilename;
		this.name = name;
		this.elapsedTime = elapsedTime;
	}
	
}
