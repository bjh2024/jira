package com.mysite.jira.dto.account;

import lombok.Getter;

@Getter
public enum AccountRole {
	ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

	AccountRole(String value) {
        this.value = value;
    }

    private String value;
}
