package com.mysite.jira.dto;

import java.util.List;

import lombok.Data;

@Data
public class FilterIssueRequestDTO {

	private Integer[] projectIdx;
	private String[] issueTypes;
	private String[] issueStatus;
	private List<Integer> issueManagerIdx;
}
