package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoterListDTO {
	private Integer issueIdx;
	private Integer userIdx;
	
	private String name;
	private String iconFilename;
	
	@Builder
	public VoterListDTO(Integer userIdx, String name, String iconFilename) {
		this.userIdx = userIdx;
		this.name = name;
		this.iconFilename = iconFilename;
	}
}
