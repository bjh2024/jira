package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusTitleDTO {
	private Integer statusIdx;
	private String name;
	
	@Builder
	public StatusTitleDTO(Integer statusIdx, String name) {
		this.statusIdx = statusIdx;
		this.name = name;
	}
}
