package com.mysite.jira.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCol {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboard_col_seq")
	@SequenceGenerator(name = "dashboard_col_seq", sequenceName = "dashboard_col_seq", allocationSize = 1)
	private Integer idx;
	
	@Column(columnDefinition = "VARCHAR2(300)")
	@NotNull
	private String content;
	
	@OneToMany(mappedBy = "dashboardCol", cascade = CascadeType.REMOVE)
	private List<DashboardPieChart> pieChartList;
	
	@OneToMany(mappedBy = "dashboardCol", cascade = CascadeType.REMOVE)
	private List<DashboardIssueStatistics> dashboardIssueStatistics;
	
	@OneToMany(mappedBy = "dashboardCol", cascade = CascadeType.REMOVE)
	private List<DashboardAllotCol> dashboardAllotColList;
	
	@OneToMany(mappedBy = "dashboardCol", cascade = CascadeType.REMOVE)
	private List<DashboardIssueFilterCol> dashboardIssueFilterColList;
}
