package com.mysite.jira.dto.dashboard;

import com.mysite.jira.entity.Dashboard;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DashboardListDTO {
	
	private Dashboard dashboard;
	private boolean isLike;
	private Long likeCount;
	
	@Builder
	public DashboardListDTO(Dashboard dashboard, boolean isLike, Long likeCount) {
		this.dashboard = dashboard;
		this.isLike = isLike;
		this.likeCount = likeCount;
	}
}
