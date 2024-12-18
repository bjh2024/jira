package com.mysite.jira.dto.dashboard;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueCompleteChartDTO {

	private String date;
	private Long count;

	@Builder
	public IssueCompleteChartDTO(String date, Long count) {
		this.date = date;
		this.count = count;
	}
	
	
	
}
