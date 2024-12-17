package com.mysite.jira.dto.dashboard.create;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountListDTO {

	private Integer idx;
	private String name;
	private String iconFilename;

	@Builder
	public AccountListDTO(Integer idx, String name, String iconFilename) {
		this.idx = idx;
		this.name = name;
		this.iconFilename = iconFilename;
	}
}
