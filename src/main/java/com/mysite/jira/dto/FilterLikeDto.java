package com.mysite.jira.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterLikeDto {
	
	// 필터 즐겨찾기를 위한 값
	
	private Integer accountIdx;
	private Integer filterIdx;
}
