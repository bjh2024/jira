package com.mysite.jira.dto.dashboard;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DashboardAllotDTO {
	private String issueIconFilename;
	private String key;
	private String name;
	private String priorityIconFilename;
	
	@Builder
	public DashboardAllotDTO(String issueIconFilename, String key, String name, String priorityIconFilename) {
		this.issueIconFilename = issueIconFilename;
		this.key = key;
		this.name = name;
		this.priorityIconFilename = priorityIconFilename;
	}
}
