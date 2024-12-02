package com.mysite.jira.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreateUserForm {
	@Email
    @NotEmpty(message = "이메일은 필수 항목입니다.")
	private String email;
	
    @Size(min = 3, max = 25)
	@NotEmpty(message = "사용자 이름은 필수 항목입니다.")
	private String username;

	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	private String pw;
	
	@NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
	private String checkpw;
	
}
