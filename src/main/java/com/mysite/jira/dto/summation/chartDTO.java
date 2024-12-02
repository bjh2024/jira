package com.mysite.jira.dto.summation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class chartDTO {

	private String name;
	private Long count;
	
	@Builder
	public chartDTO(String name, Long count) {
		this.name = name;
		this.count = count;
	}
}
