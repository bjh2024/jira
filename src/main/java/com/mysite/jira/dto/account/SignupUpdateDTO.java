package com.mysite.jira.dto.account;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class SignupUpdateDTO {
	public static final String NULL_VALUE = "__NULL__";
	private String authCode;
    private LocalDateTime codeExpDate;
}
