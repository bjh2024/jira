package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ObserverListDTO {
	private Integer issueIdx;
	private Integer userIdx;
	private String isIn;
	private Integer count;
	
	@Builder
	public ObserverListDTO(String isIn, Integer count) {
		this.isIn = isIn;
		this.count = count;
	}
}
