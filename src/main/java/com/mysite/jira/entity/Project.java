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
	private String iconFilename;

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

	@Builder
	public Project(String key, String color, String iconFilename, String name, LocalDateTime clickedDate, Integer sequence,
			Jira jira, Account account) {
		this.key = key;
		this.color = color;
		this.iconFilename = iconFilename;
		this.name = name;
		this.clickedDate = clickedDate;
		this.sequence = sequence;
		this.createDate = LocalDateTime.now();
		this.jira = jira;
		this.account = account;
	}

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
	private List<DashboardIssueComplete> issueCompleteList;

	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<DashboardIssueRecent> issueRecentList;

	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<DashboardIssueStatistics> issueStatisticsList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<ProjectLikeMembers> ProjectLikeMembersList;

	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<FilterAuth> filterAuthList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<FilterProject> FilterProjectList;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<ProjectRecentClicked> projectClickedList;

}
