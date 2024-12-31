package com.mysite.jira.dto.account;

import lombok.Getter;

@Getter
public class LoginAddJiraDTO {

	private String email;
	private String password;
	private Integer jiraIdx;
}
