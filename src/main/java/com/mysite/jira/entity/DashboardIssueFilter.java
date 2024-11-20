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
	@NotNull
	private Integer viewNum;

	@Column(columnDefinition = "VARCHAR2(20)")
	@NotNull
	private String divOrder;
	
	@ManyToOne
	private Dashboard dashboard;
	
	//@ManyToOne
	//private Filter filter;
	
	@OneToMany(mappedBy = "dashboardIssueFilter", cascade = CascadeType.REMOVE)
	private List<DashboardIssueFilterCol> dashboardIssueFilterColList;

}