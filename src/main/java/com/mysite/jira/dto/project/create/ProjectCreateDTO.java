package com.mysite.jira.dto.project.create;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectCreateDTO {

	private String name;
	private String key;
	
}
