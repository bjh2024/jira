package com.mysite.jira.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
	@SequenceGenerator(name = "project_seq", sequenceName = "project_seq", allocationSize = 1)
	private Integer idx; 
	
	@NotNull
	@Column(columnDefinition = "VARCHAR2(20)")
	private String key;
	
	@NotNull
	@Column(columnDefinition = "VARCHAR2(50)")
	private String color;
	
	@Column(columnDefinition = "VARCHAR2(300)")
	private String iconName;
	
	@NotNull
	@Column(columnDefinition = "VARCHAR2(100)")
	private String name;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime clickedDate;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime createDate;
	
	@ManyToOne
	private Jira jira;
	
	@ManyToOne
	private Account account;
	
	@NotNull
	@ColumnDefault("0")
	private Integer sequence;
	
	@ManyToMany
	Set<Account> likeMembers;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE) 
	private List<ProjectMembers> prjmemberList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE) 
	private List<IssueStatus> issueStatusList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE) 
	private List<IssueType> issueTypeList; 
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE) 
	private List<Issue> issueList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE) 
	private List<IssueExtends> extendsList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<DashboardPieChart> dashboardPieChartList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<DashboardAuth> dashboardAuthList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<DashboardIssueComplete> dashboardIssueComplete;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<DashboardIssueRecent> dashboardIssueRecent;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<DashboardIssueStatistics> dashboardIssueStatistics;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<FilterAuth> filterAuthList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<FilterProject> FilterProjectList;
	
}
