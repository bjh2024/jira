package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReporterDTO {
	private Integer userIdx;
	private Integer projectIdx;
	private Integer issueIdx;
	private String name;
	private String iconFilename;
	private String type;
	
	@Builder
	public ReporterDTO(Integer userIdx, Integer projectIdx, Integer issueIdx, String name, String iconFilename) {
		this.userIdx = userIdx;
		this.projectIdx = projectIdx;
		this.issueIdx = issueIdx;
		this.name = name;
		this.iconFilename = iconFilename;
	}
}
