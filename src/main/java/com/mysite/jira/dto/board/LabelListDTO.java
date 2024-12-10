package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelListDTO {
	private String name;
	private Integer issueIdx;
	private Integer labelIdx;
	
	@Builder
	public LabelListDTO(String name, Integer issueIdx, Integer labelIdx) {
		this.name = name;
		this.issueIdx = issueIdx;
		this.labelIdx = labelIdx;
	}
}
