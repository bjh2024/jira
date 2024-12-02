package com.mysite.jira.dto.project.summation;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PercentTableDTO {

	private String name;
	private String iconFilename;
	private Long count;

	@Builder
	public PercentTableDTO(String name, String iconFilename, Long count) {
		this.name = name;
		this.iconFilename = iconFilename;
		this.count = count;
	}
}
