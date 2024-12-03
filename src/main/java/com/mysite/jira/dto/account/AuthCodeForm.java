package com.mysite.jira.dto.account;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCodeForm {
	private String email;
	
	@NotEmpty(message = "첫번째 인증 코드 항목은 비워 둘 수 없습니다.")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "알맞은 영문자를 입력해 주세요.")
	private String first;
	
	@NotEmpty(message = "두번째 인증 코드 항목은 비워 둘 수 없습니다.")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "알맞은 영문자를 입력해 주세요.")
	private String second;
	
	@NotEmpty(message = "세번째 인증 코드 항목은 비워 둘 수 없습니다.")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "알맞은 영문자를 입력해 주세요.")
	private String third;
	
	@NotEmpty(message = "네번째 인증 코드 항목은 비워 둘 수 없습니다.")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "알맞은 영문자를 입력해 주세요.")
	private String fourth;
}
