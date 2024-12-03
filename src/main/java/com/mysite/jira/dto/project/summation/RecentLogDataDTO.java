package com.mysite.jira.dto.project.summation;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecentLogDataDTO {

	private String accountName;
	private String accountFilename;
	private String issueName;
	private String issueTypeFilename;
	private String issueStatusName;
	private String logContent;
	private String elapsedTime;
	private String key;
	private LocalDateTime createDate;

	@Builder
	public RecentLogDataDTO(String accountName, String accountFilename, 
						  String issueName, String issueTypeFilename,
						  String issueStatusName, String logContent, 
						  String elapsedTime, String key,
						  LocalDateTime createDate) {
		this.accountName = accountName;
		this.accountFilename = accountFilename;
		this.issueName = issueName;
		this.issueTypeFilename = issueTypeFilename;
		this.issueStatusName = issueStatusName;
		this.logContent = logContent;
		this.elapsedTime = elapsedTime;
		this.key = key;
		this.createDate = createDate;
	}
}
