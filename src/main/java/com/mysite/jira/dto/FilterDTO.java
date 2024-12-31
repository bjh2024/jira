package com.mysite.jira.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterDTO {

	// 필터링 할 목록들의 값들
	
	private Integer filterIdx;
	private Integer jiraIdx;
	private Integer accountIdx;
	private Integer reporterAccountIdx;
	private Integer ManagerAccountIdx;
	private Integer projectIdx;
	private Integer projectRole;
	private Integer teamIdx;
	private Integer type;
	private Integer isCompleted;
	private Integer issuePriorityIdx;
	private Integer issueStatusIdx;
	private Integer issueTypeIdx;
	private Integer beforeDate;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String explain;
	private String filterName;
	
}
