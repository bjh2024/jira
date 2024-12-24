package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateIssueDTO {
	private Integer projectIdx;
	private Integer issueTypeIdx;
	private Integer reporterIdx;
	private Integer statusIdx;
	private String issueName;
	
	private Integer issueIdx;
	
	@Builder
	public CreateIssueDTO(Integer issueIdx) {
		this.issueIdx = issueIdx;
	}
}
