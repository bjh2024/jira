package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AIQuestionDTO {
	private String question;
	private String answer;
	
	@Builder
	public AIQuestionDTO(String answer) {
		this.answer = answer;
	}
}
