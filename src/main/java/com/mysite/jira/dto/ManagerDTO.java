package com.mysite.jira.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDTO {

	private Integer managerIdx;
	private String managerName;
	private String managerIconFilename;
	
	@Builder
	public ManagerDTO(Integer managerIdx, String managerName, String managerIconFilename) {
		this.managerIdx = managerIdx;
		this.managerName = managerName;
		this.managerIconFilename = managerIconFilename;
	}

}
