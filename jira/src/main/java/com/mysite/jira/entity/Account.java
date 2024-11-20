package com.mysite.jira.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
	@SequenceGenerator(name = "account_seq", sequenceName = "account_seq", allocationSize = 1)
	private Integer idx;

	@Column(unique = true, columnDefinition = "VARCHAR2(500)")
	@Size(min = 10, max = 500)
	@NotNull
	private String email;

	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String pw;

	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;

	@Column(columnDefinition = "VARCHAR2(100)")
	private String iconFileName;

	@Column
	private LocalDateTime createDate;

	@Column(columnDefinition = "VARCHAR2(4)")
	private String authCode;

	@Column
	private LocalDateTime codeExpDate;

	// 지라 생성자 FK
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<Jira> jiraList;

	// 프로젝트 생성자 FK
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<Project> projectList;

	// 프로젝트 멤버 FK
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<ProjectMembers> prjmemList;

	// 이슈 담당자 FK
	@OneToMany(mappedBy = "manager", cascade = CascadeType.REMOVE)
	private List<Issue> issueManagerList;

	// 이슈 보고자 FK
	@OneToMany(mappedBy = "reporter", cascade = CascadeType.REMOVE)
	private List<Issue> issueReporterList;

	// 이슈 업데이트 멤버 FK
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<IssueRecord> issueRecordList;

	// 이슈 답변 FK
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<IssueReply> issueReplyList;

	// 이슈 파일 FK
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<IssueFile> issueFileList;

	// 팀 FK
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<Team> teamList;

	// 대시보드 FK 추가
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<Dashboard> dashbaordList;

	// chatMessage FK 추가
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<ChatMessage> chatMessageList;

	// DashboardAuth FK 추가
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<DashboardAuth> dashboardAuthList;
	
	// filter FK 추가
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<Filter> filterList;
	
	// filterAuth FK 추가
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<FilterAuth> filterAuthList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<FilterManager> FilterManagerList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<FilterLikeMembers> FilterLikeMembersList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<FilterReporter> FilterReporterList;
	
}
