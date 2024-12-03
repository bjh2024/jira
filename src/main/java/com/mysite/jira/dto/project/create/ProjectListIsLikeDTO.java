package com.mysite.jira.dto.project.create;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectListIsLikeDTO {

	private String projectName;
	private String projectIconFilename;
	private String projectKey;
	private String accountName;
	private String accountIconFilename;
	private boolean isLike;
	
	@Builder
	public ProjectListIsLikeDTO(String projectName, String projectIconFilename, String projectKey, String accountName,
			String accountIconFilename, boolean isLike) {
		super();
		this.projectName = projectName;
		this.projectIconFilename = projectIconFilename;
		this.projectKey = projectKey;
		this.accountName = accountName;
		this.accountIconFilename = accountIconFilename;
		this.isLike = isLike;
	}
}
