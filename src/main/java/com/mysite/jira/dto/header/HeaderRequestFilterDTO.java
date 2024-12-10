package com.mysite.jira.dto.header;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class HeaderRequestFilterDTO {
	private Integer jiraIdx;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Integer[] projectIdxArr;
	private Integer[] managerIdxArr;
	private Boolean isReporter;
	private Integer[] statusArr;
	private String currentUrl;
}
