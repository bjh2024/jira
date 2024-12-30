package com.mysite.jira.dto;

import lombok.Getter;

@Getter
public class NewPwDTO {

	private String oldPw;
	private String newPw;
	private String email;
}
