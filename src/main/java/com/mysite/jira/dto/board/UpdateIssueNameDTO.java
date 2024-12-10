package com.mysite.jira.dto.board;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateIssueNameDTO {
	private Integer issueIdx;
	private String name;
	
	@Builder
	public UpdateIssueNameDTO(Integer issueIdx, String name) {
		this.issueIdx = issueIdx;
		this.name = name;
	}
}
