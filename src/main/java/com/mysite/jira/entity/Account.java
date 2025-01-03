package com.mysite.jira.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
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
	private String pw;

	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;

	@Column(columnDefinition = "VARCHAR2(100)")
	private String iconFilename;

	@Column
	private LocalDateTime createDate;

	@Column(columnDefinition = "VARCHAR2(4)")
	private String authCode;

	@Column
	private LocalDateTime codeExpDate;
	
	@Column(columnDefinition = "VARCHAR2(500)")
	private String kakaoSocialKey;
	
	@Column(columnDefinition = "VARCHAR2(500)")
	private String naverSocialKey;
	
	@Builder
	public Account(String email, String pw, String name, String iconFilename, 
			String authCode, String kakaoSocialKey, String naverSocialKey) {
		this.email = email;
		this.pw = pw;
		this.name = name;
		this.iconFilename = iconFilename;
		this.createDate = LocalDateTime.now();
		this.authCode = authCode;
		this.codeExpDate = LocalDateTime.now().plusMinutes(3);
		this.kakaoSocialKey = kakaoSocialKey; 
		this.naverSocialKey = naverSocialKey;
	}
	
	public void updateAccount(String authCode, LocalDateTime codeExpDate) {
		this.authCode = authCode;
		this.codeExpDate = codeExpDate;
	}
	
	public void updateAccountPw(String pw) {
		this.pw = pw;
	}

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

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<IssueReply> issueReplyList;

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<IssueFile> issueFileList;

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<Team> teamList;

	// 대시보드 FK 추가
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<Dashboard> dashbaordList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<DashboardAuth> dashboardAuthList;

	// Chat FK 추가
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<ChatMessage> chatMessageList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<ChatMembers> chatMembersList;

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<ChatUnreadList> chatUnreadList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<DashboardLikeMembers> likeMembersList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<IssueLikeMembers> issueLikeMembersList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<FilterReporter> FilterReporterList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE) 
	private List<JiraMembers> JiraMembersList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE) 
	private List<ProjectLikeMembers> ProjectLikeMembersList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE) 
	private List<TeamMembers> TeamMembersList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE) 
	private List<ProjectLogData> projectLogDataList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<ProjectRecentClicked> projectClickedList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<IssueRecentClicked> issueClickedList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<DashboardRecentClicked> dashClickedList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<FilterRecentClicked> filterClickedList;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	private List<JiraRecentClicked> jiraClickedList;
}
