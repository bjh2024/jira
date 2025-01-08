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
public class DashboardPieChart implements DashboardItem{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pie_chart_seq")
	@SequenceGenerator(name = "pie_chart_seq", sequenceName = "pie_chart_seq", allocationSize = 1)
	private Integer idx;
	
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
	public DashboardPieChart(Integer divOrderX, Integer divOrderY, Dashboard dashboard, Project project,
			DashboardCol dashboardCol, Integer isSave) {
		this.divOrderX = divOrderX;
		this.divOrderY = divOrderY;
		this.dashboard = dashboard;
		this.project = project;
		this.isSave = isSave;
		this.dashboardCol = dashboardCol;
	}
	
	public void updatePieChart(Project project, DashboardCol dashboardCol) {
		this.project = project;
		this.dashboardCol = dashboardCol;
		this.isSave = 1;
	}
}
