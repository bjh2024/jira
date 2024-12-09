package com.mysite.jira.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FilterIssueRequestDTO {

	private Integer[] projectIdx;
	private String[] issueTypes;
	private String[] issueStatus;
	private String[] issueManager;
	private String[] issueReporter;
	private String[] issuePriority;
	private LocalDateTime updateStartDate;
	private LocalDateTime updateLastDate;
	private LocalDateTime updateBeforeDate;
	private LocalDateTime createStartDate;
	private LocalDateTime createLastDate;
	private LocalDateTime createBeforeDate;
	private LocalDateTime doneStartDate;
	private LocalDateTime doneLastDate;
	private LocalDateTime doneBeforeDate;
	private String searchContent;
	private Integer doneCheck;
	private Integer notDoneCheck;
}
