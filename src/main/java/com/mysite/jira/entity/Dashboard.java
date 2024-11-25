package com.mysite.jira.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

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
public class Dashboard {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboard_seq")
	@SequenceGenerator(name = "dashboard_seq", sequenceName = "dashboard_seq", allocationSize = 1)
	private Integer idx;
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;
	
	@Column(columnDefinition = "VARCHAR2(4000)")
	private String explain;
	
	@Column
	@ColumnDefault("sysdate")
	private LocalDateTime clickedDate;
	
	@ManyToOne
	private Jira jira;
	
	@ManyToOne
	private Account account;
	
	@Builder
	public Dashboard(String name, String explain, LocalDateTime clickedDate, Jira jira, Account account) {
		this.name = name;
		this.explain = explain;
		this.clickedDate = clickedDate;
		this.jira = jira;
		this.account = account;
	}
	
	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.REMOVE)
	private List<DashboardPieChart> pieChartList;
	
	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.REMOVE)
	private List<DashboardAllot> allotList;
	
	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.REMOVE)
	private List<DashboardIssueComplete> dashboardIssueCompleteList;
	
	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.REMOVE)
	private List<DashboardIssueRecent> dashboardIssueRecent;
	
	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.REMOVE)
	private List<DashboardIssueStatistics> dashboardIssueStatistics;
	
	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.REMOVE)
	private List<DashboardIssueFilter> dashboardIssueFilter;
	
	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.REMOVE)
	private List<DashboardLikeMembers> likeMembersList;
	
	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.REMOVE)
	private List<DashboardAuth> dashboardAuthList;
	
	@OneToMany(mappedBy = "dashboard", cascade = CascadeType.REMOVE)
	private List<DashboardRecentClicked> dashClickedList;
}
