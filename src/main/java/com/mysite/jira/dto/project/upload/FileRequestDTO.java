package com.mysite.jira.dto.project.upload;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileRequestDTO {
	private Integer issueIdx;
	
	private Integer userIdx;
	private Integer fileIdx;
	private String name;
	private String createDate;
	
	@Builder
	public FileRequestDTO(Integer userIdx, Integer fileIdx, Integer issueIdx, String name, String createDate) {
		this.userIdx = userIdx;
		this.fileIdx = fileIdx;
		this.issueIdx = issueIdx;
		this.name = name;
		this.createDate = createDate;
	}
}
