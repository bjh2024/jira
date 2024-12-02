package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelListDTO {
	private String name;
	
	@Builder
	public LabelListDTO(String name) {
		this.name = name;
	}
}
