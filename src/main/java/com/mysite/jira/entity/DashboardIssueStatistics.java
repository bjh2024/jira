package com.mysite.jira.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class DashboardIssueStatistics {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_statistics_seq")
	@SequenceGenerator(name = "issue_statistics_seq", sequenceName = "issue_statistics_seq", allocationSize = 1)
	private Integer idx;
	
	@Column
	private Integer viewNum;
	
	@Column
	@NotNull
	private Integer divOrderX;
	
	@Column
	@NotNull
	private Integer divOrderY;
	
	public void updateOrder(Integer divOrderX, Integer divOrderY) {
		this.divOrderX = divOrderX;
		this.divOrderY = divOrderY;
	}
	
	@Column
	@NotNull
	private Integer isSave;
	
	@ManyToOne
	private Dashboard dashboard;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private DashboardCol dashboardCol;
	
	@Builder
	public DashboardIssueStatistics(Integer viewNum, Integer divOrderX, Integer divOrderY,
			Dashboard dashboard, Project project, DashboardCol dashboardCol, Integer isSave) {
		this.viewNum = viewNum;
		this.divOrderX = divOrderX;
		this.divOrderY = divOrderY;
		this.dashboard = dashboard;
		this.project = project;
		this.dashboardCol = dashboardCol;
		this.isSave = isSave;
	}
	
	public void updateIssueStatistics(Project project, DashboardCol dashboardCol, Integer rowNum) {
		this.project = project;
		this.dashboardCol = dashboardCol;
		this.viewNum = rowNum;
		this.isSave = 1;
	}
	
}
