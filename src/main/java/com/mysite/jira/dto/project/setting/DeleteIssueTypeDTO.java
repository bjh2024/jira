package com.mysite.jira.dto.project.setting;

import lombok.Getter;

@Getter
public class DeleteIssueTypeDTO {
	private Integer projectIdx;
	private Integer issueTypeIdx;
	private Integer newTypeIdx;
}
