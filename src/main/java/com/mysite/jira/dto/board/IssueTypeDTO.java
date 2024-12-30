package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueTypeDTO {
	private Integer projectIdx;
	
	private Integer idx;
	private String name;
	private String iconFilename;
	private String content;
	
	@Builder
	public IssueTypeDTO(Integer projectIdx, Integer idx, String name,String iconFilename) {
		this.projectIdx = projectIdx;
		this.idx = idx;
		this.name = name;
		this.iconFilename = iconFilename;
	}
}
