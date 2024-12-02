package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueStatusListDTO {

	private String name;
	private Integer status;
	
	@Builder
	public IssueStatusListDTO(String name, Integer status) {
		this.name = name;
		this.status = status;
	}
	
	
}
