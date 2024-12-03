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
public class Jira {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jira_seq")
	@SequenceGenerator(name = "jira_seq", sequenceName = "jira_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;
	
	@ManyToOne
	private Account account;
	
	@Builder
	public Jira(String name, Account account) {
		this.name = name;
		this.account = account;
	}
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<Project> projectList; 
	
	// 팀 목록 FK 추가
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<Team> teamList;
	
	// 대시보드 FK 추가
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE)
	private List<Dashboard> dashbaordList;
	// 소문자 고침
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE)
	private List<JiraMembers> jiraMembersList;
	
	// Filter FK 추가
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE)
	private List<Filter> filterList;
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<Issue> issueList;
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<ProjectRecentClicked> projectRecentClickedList; 
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<IssueRecentClicked> issueClickedList;
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<DashboardRecentClicked> dashboardClickedList;
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE)
	private List<FilterRecentClicked> filterClickedList;

	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<ProjectRecentClicked> projectClickedList; 
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<DashboardRecentClicked> dashClickedList; 
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<IssueLabel> issueLabelList;

}
