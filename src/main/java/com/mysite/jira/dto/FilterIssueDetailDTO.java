package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterIssueDetailDTO {

	// 이슈 상세보기 화면에서 어떤 이슈인지 걸러줄 키 값
	private String issueKey;

	@Builder
	public FilterIssueDetailDTO(String issueKey) {
		this.issueKey = issueKey;
	}
	
}
