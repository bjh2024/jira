package com.mysite.jira.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelListDTO {
	private String name;
	private Integer issueIdx;
	private Integer labelIdx;
	private Integer labelDataIdx;
	
	@Builder
	public LabelListDTO(String name, Integer issueIdx, Integer labelIdx, Integer labelDataIdx) {
		this.name = name;
		this.issueIdx = issueIdx;
		this.labelIdx = labelIdx;
		this.labelDataIdx = labelDataIdx;
	}
}
