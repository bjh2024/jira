package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ChartDTO {

	private String name;
	private Long count;
	
	@Builder
	public ChartDTO(String name, Long count) {
		this.name = name;
		this.count = count;
	}
}
