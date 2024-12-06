package com.mysite.jira.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FilterIssueDTO {
	
	private String issueIconFilename;
	private String issueKey;
	private String issueName;
	private String issueManagerIconFilename;
	private String issueManagerName;
	private String issueReporterIconFilename;
	private String issueReporterName;
	private String issuePriorityIconFilename;
	private String issuePriorityName;
	private String issueStatusName;
	private Integer issueStatus;
	private LocalDateTime finishDate;
	private LocalDateTime createDate;
	private LocalDateTime editDate;
	private LocalDateTime deadlineDate;
	
	@Builder
	public FilterIssueDTO(String issueIconFilename, String issueKey, String issueName, String issueManagerIconFilename,
			String issueManagerName, String issueReporterIconFilename, String issueReporterName,
			String issuePriorityIconFilename, String issuePriorityName, String issueStatusName, Integer issueStatus,
			LocalDateTime finishDate,LocalDateTime createDate, LocalDateTime editDate, LocalDateTime deadlineDate) {
		this.issueIconFilename = issueIconFilename;
		this.issueKey = issueKey;
		this.issueName = issueName;
		this.issueManagerIconFilename = issueManagerIconFilename;
		this.issueManagerName = issueManagerName;
		this.issueReporterIconFilename = issueReporterIconFilename;
		this.issueReporterName = issueReporterName;
		this.issuePriorityIconFilename = issuePriorityIconFilename;
		this.issuePriorityName = issuePriorityName;
		this.issueStatusName = issueStatusName;
		this.issueStatus = issueStatus;
		this.finishDate = finishDate;
		this.createDate = createDate;
		this.editDate = editDate;
		this.deadlineDate = deadlineDate;
	}

}
