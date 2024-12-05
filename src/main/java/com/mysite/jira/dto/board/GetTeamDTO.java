package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetTeamDTO {
	private Integer teamIdx;
	private Integer jiraIdx;
	private String name;
	private String iconFilename;
	private Integer issueIdx;
	
	@Builder
	public GetTeamDTO(Integer teamIdx, Integer jiraIdx, String name, String iconFilename, Integer issueIdx) {
		this.teamIdx = teamIdx;
		this.jiraIdx = jiraIdx;
		this.name = name;
		this.iconFilename = iconFilename;
		this.issueIdx = issueIdx;
	}
}
