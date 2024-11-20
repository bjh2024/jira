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
public class DashboardIssueFilterCol {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_col_seq")
	@SequenceGenerator(name = "filter_col_seq", sequenceName = "filter_col_seq", allocationSize = 1)
	private Integer idx;
	
	@Column
	@NotNull
	private Integer isSave;
	
	@ManyToOne
	private DashboardIssueFilter dashboardIssueFilter;
	
	@ManyToOne 
	private  DashboardCol dashboardCol;

	@Builder
	public DashboardIssueFilterCol(Integer isSave, DashboardIssueFilter dashboardIssueFilter,
			DashboardCol dashboardCol) {
		this.isSave = isSave;
		this.dashboardIssueFilter = dashboardIssueFilter;
		this.dashboardCol = dashboardCol;
	} 
	
	
	
}
