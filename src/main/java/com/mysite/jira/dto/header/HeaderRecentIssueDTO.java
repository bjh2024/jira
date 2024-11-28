package com.mysite.jira.dto.header;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeaderRecentIssueDTO {
	
	private String name;
	private String key;
	private String iconFilename;
	private String projectName;
	private String elapsedTime;
	
	@Builder
	public HeaderRecentIssueDTO(String name, String key, String iconFilename, String projectName, String elapsedTime) {
		this.name = name;
		this.key = key;
		this.iconFilename = iconFilename;
		this.projectName = projectName;
		this.elapsedTime = elapsedTime;
	}
}
