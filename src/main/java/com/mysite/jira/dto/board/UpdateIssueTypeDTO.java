package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateIssueTypeDTO {
	private Integer projectIdx;
	private Integer issueTypeIdx;
	private Integer issueIdx;
	
	private String name;
	private String iconFilename;
	
	@Builder 
	public UpdateIssueTypeDTO(Integer issueIdx, Integer projectIdx, Integer issueTypeIdx, String name, String iconFilename) {
		this.issueIdx = issueIdx;
		this.projectIdx = projectIdx;
		this.issueTypeIdx = issueTypeIdx;
		this.name = name;
		this.iconFilename = iconFilename;
	}
}
