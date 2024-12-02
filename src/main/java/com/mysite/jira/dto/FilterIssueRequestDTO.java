package com.mysite.jira.dto;

import lombok.Data;

@Data
public class FilterIssueRequestDTO {

	private Integer[] projectIdx;
	private String[] issueTypes;
	private String[] issueStatus;
}
