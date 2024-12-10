package com.mysite.jira.dto.project.list;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectListIsLikeDTO {

	private Integer projectIdx;
	private String projectName;
	private String projectIconFilename;
	private String projectKey;
	private String accountName;
	private String accountIconFilename;
	private boolean isLike;
	
	@Builder
	public ProjectListIsLikeDTO(Integer projectIdx, String jiraName, String projectName, String projectIconFilename, String projectKey, String accountName,
			String accountIconFilename, boolean isLike) {
		this.projectIdx = projectIdx;
		this.projectName = projectName;
		this.projectIconFilename = projectIconFilename;
		this.projectKey = projectKey;
		this.accountName = accountName;
		this.accountIconFilename = accountIconFilename;
		this.isLike = isLike;
	}
}
