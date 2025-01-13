package com.mysite.jira.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FilterIssueRequestDTO {

	// 필터 생성을 위한 값들
	
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
	private Integer filterIdx;
	private Integer updateBefore;
	private Integer doneDateBefore;
	private Integer createDateBefore;
	private String explain;
	private String filterName;
	private String jiraName;
	private Integer[] isCompleted;
	private String issueKey;
	private Integer viewAuth;
	private Integer[] viewProject;
	private Integer[] viewUser; 
	private Integer[] viewTeam;
	private Integer editAuth;
	private Integer[] editProject;
	private Integer[] editUser;
	private Integer[] editTeam;
}
