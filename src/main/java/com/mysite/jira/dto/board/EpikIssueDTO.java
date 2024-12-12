package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EpikIssueDTO {
	private Integer projectIdx;
	
	// get epik issue
	private Integer issueIdx;
	private String iconFilename;
	private String name;
	private String issueKey;
	
	// create issue path
	private Integer parentIdx;
	private Integer childIdx;
	
	@Builder
	public EpikIssueDTO(Integer issueIdx, String iconFilename, String name, String issueKey) {
		this.issueIdx = issueIdx;
		this.iconFilename = iconFilename;
		this.name = name;
		this.issueKey = issueKey;
	}
}
