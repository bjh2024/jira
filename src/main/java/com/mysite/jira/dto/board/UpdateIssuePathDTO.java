package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateIssuePathDTO {
	private Integer projectIdx;
	private Integer childIdx;
	private Integer newParentIdx;
	private Integer oldParentIdx;
	
	private Integer currnetIdx;
	private String issueKey;
	
	@Builder
	public UpdateIssuePathDTO(Integer projectIdx, Integer currentIdx, String issueKey) {
		this.projectIdx = projectIdx;
		this.currnetIdx = currentIdx;
		this.issueKey = issueKey;
	}
}
