package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueRecentInputDTO {
	
	private String name;
	private String key;
	private String iconFilename;
	
	@Builder
	public IssueRecentInputDTO(String name, String key, String iconFilename) {
		this.name = name;
		this.key = key;
		this.iconFilename = iconFilename;
	}
}
