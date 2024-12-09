package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StatusListDTO {
	private Integer idx;
	private String name;
	private Integer status;
	
	@Builder
	public StatusListDTO(Integer idx, String name, Integer status) {
		this.idx = idx;
		this.name = name;
		this.status = status;
	}
}
