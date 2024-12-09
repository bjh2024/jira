package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetPriorityDTO {
	private Integer issueIdx;
	private Integer priorityIdx;
	private String iconFilename;
	private String name;
	
	@Builder
	public GetPriorityDTO(Integer issueIdx, Integer priorityIdx, String iconFilename, String name) {
		this.issueIdx = issueIdx;
		this.priorityIdx = priorityIdx;
		this.iconFilename = iconFilename;
		this.name = name;
	}
}
