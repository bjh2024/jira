package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterIssueDetailDTO {

	private String issueKey;

	@Builder
	public FilterIssueDetailDTO(String issueKey) {
		this.issueKey = issueKey;
	}
	
}
