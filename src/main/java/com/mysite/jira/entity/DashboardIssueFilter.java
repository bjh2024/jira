package com.mysite.jira.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class DashboardIssueFilter {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboard_filter_seq")
	@SequenceGenerator(name = "dashboard_filter_seq", sequenceName = "dashboard_filter_seq", allocationSize = 1)
	private Integer idx;
	
	@Column
	private Integer rowNum;

	@NotNull
	private Integer divOrderX;
	
	@NotNull
	private Integer divOrderY;
	
	@Column
	@NotNull
	private Integer isSave;
	
	@ManyToOne
	private Dashboard dashboard;
	
	@ManyToOne
	private Filter filter;
	
	@OneToMany(mappedBy = "dashboardIssueFilter", cascade = CascadeType.REMOVE)
	private List<DashboardIssueFilterCol> dashboardIssueFilterColList;
	
	@Builder
	public DashboardIssueFilter(Integer rowNum, Integer divOrderX, Integer divOrderY, Dashboard dashboard, Filter filter, Integer isSave) {
		this.isSave = isSave;
		this.rowNum = rowNum;
		this.divOrderX = divOrderX;
		this.divOrderY = divOrderY;
		this.isSave = isSave;
		this.dashboard = dashboard;
		this.filter = filter;
	}
}