package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDTO {

	private Integer managerIdx;
	private String managerName;
	
	@Builder
	public ManagerDTO(Integer managerIdx, String managerName) {
		this.managerIdx = managerIdx;
		this.managerName = managerName;
	}

}
