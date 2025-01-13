package com.mysite.jira.dto.dashboard;

import lombok.Getter;

@Getter
public class RequestDashboardItemOrderDTO {

	private String type;
	private Integer orderX;
	private Integer orderY;
	private Integer dashboardItemIdx;
}
