package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeContentDTO {
	private String name;
	private String iconFilename;
	
	@Builder
	public LikeContentDTO(String name, String iconFilename) {
		this.name = name;
		this.iconFilename = iconFilename;
	}

}
