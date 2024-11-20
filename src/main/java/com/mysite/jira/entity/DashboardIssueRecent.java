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
public class DashboardIssueRecent {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_recent_seq")
	@SequenceGenerator(name = "issue_recent_seq", sequenceName = "issue_recent_seq", allocationSize = 1)
	private Integer idx;
	
	@Column
	@NotNull
	private Integer viewDate;
	
	@Column(columnDefinition = "VARCHAR2(20)")
	@NotNull
	private String divOrder;
	
	@Column
	@NotNull
	private Integer isSave;
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String unitPeriod;
	
	@ManyToOne
	private Dashboard dashboard;
	
	@ManyToOne
	private Project project;
	
	@Builder
	public DashboardIssueRecent(Integer viewDate, String divOrder, Dashboard dashboard, 
			Integer isSave, String unitPeriod, Project project) {
		this.viewDate = viewDate;
		this.divOrder = divOrder;
		this.dashboard = dashboard;
		this.project = project;
		this.isSave = isSave;
		this.unitPeriod = unitPeriod;
	}
}
