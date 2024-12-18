package com.mysite.jira.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterDTO {

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
