package com.mysite.jira.dto.dashboard;

import java.util.List;

import lombok.Getter;

@Getter
public class RequestDashboardCreateDTO {

	private String name;
	private String explain;
	private String uri;
	private List<AuthTypeDTO> authItems;
}
