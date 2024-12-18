package com.mysite.jira.dto.dashboard;

import lombok.Getter;

@Getter
public class RequestCompleteRecentDTO {
	private Integer idx;
	private Integer projectIdx;
	private Integer viewDate;
	private String unitPeriod;
}
