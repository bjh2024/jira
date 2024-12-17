package com.mysite.jira.dto.dashboard.create;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TeamListDTO {
	private Integer idx;
	private String name;

	@Builder
	public TeamListDTO(Integer idx, String name) {
		this.idx = idx;
		this.name = name;
	}
	
}
