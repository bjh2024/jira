package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateSubIssueDTO {
	private Integer projectIdx;
	private Integer parentIdx;
	private Integer reporterIdx;
	private Integer statusIdx;
	private Integer issueTypeIdx;
	private String jiraName;
	private String name;
	
	// return 객체에만 추가될 값
	private String reporterIconFilename;
	private String priorityIconFilename;
	private String issueTypeIconFilename;
	private String statusName;
	private String issueKey;
	private Integer status;
	
	@Builder
	public CreateSubIssueDTO(String name, Integer statusIdx, String reporterIconFilename, String priorityIconFilename,
			String issueTypeIconFilename, String statusName, String issueKey, Integer status) {
		this.name = name;
		this.statusIdx = statusIdx;
		this.reporterIconFilename = reporterIconFilename;
		this.priorityIconFilename = priorityIconFilename;
		this.issueTypeIconFilename = issueTypeIconFilename;
		this.statusName = statusName;
		this.issueKey = issueKey;
		this.status = status;
	}
}
