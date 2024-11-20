package com.mysite.jira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class DashboardAllotCol {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allot_col_seq")
	@SequenceGenerator(name = "allot_col_seq", sequenceName = "allot_col_seq", allocationSize = 1)
	private Integer idx;

	@ManyToOne
	private DashboardAllot dashboardAllot;

	@ManyToOne
	private DashboardCol dashboardCol;

	@Builder
	public DashboardAllotCol(DashboardAllot dashboardAllot, DashboardCol dashboardCol) {
		this.dashboardAllot = dashboardAllot;
		this.dashboardCol = dashboardCol;
	}
}
