package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReporterDTO {
	private Integer reporterIdx;
	private Integer projectIdx;
	private Integer issueIdx;
	private String name;
	private String iconFilename;
	
	@Builder
	public ReporterDTO(Integer reporterIdx, Integer projectIdx, Integer issueIdx, String name, String iconFilename) {
		this.reporterIdx = reporterIdx;
		this.projectIdx = projectIdx;
		this.issueIdx = issueIdx;
		this.name = name;
		this.iconFilename = iconFilename;
	}
}
