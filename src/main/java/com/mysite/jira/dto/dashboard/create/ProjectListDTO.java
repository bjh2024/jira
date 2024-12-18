package com.mysite.jira.dto.dashboard.create;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectListDTO {
	private Integer idx;
	private String name;
	private String iconFilename;
	
	@Builder
	public ProjectListDTO(Integer idx, String name, String iconFilename) {
		this.idx = idx;
		this.name = name;
		this.iconFilename = iconFilename;
	}
	
}
