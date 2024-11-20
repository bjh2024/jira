package com.mysite.jira.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DashboardPieChart {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pie_chart_seq")
	@SequenceGenerator(name = "pie_chart_seq", sequenceName = "pie_chart_seq", allocationSize = 1)
	private Integer idx;
	
	@Column(columnDefinition = "VARCHAR2(20)")
	@NotNull
	private String divOrder;
	
	@ManyToOne
	private Dashboard dashboard;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private DashboardCol dashboardCol;
	
}
