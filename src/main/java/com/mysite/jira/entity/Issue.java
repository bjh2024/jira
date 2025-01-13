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
@Entity(name = "Issue")
@NoArgsConstructor
public class Issue {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_seq")
	@SequenceGenerator(name = "issue_seq", sequenceName = "issue_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column(columnDefinition = "VARCHAR2(20)")
	@NotNull
	private String key;
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;
	
	@Column(columnDefinition = "VARCHAR2(4000)")
	private String content;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime editDate;
	
	@Column
	private LocalDateTime startDate;
	
	@Column
	private LocalDateTime finishDate;
	
	@Column
	private LocalDateTime deadlineDate;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime createDate;
	
	@Column
	@NotNull
	private Integer divOrder;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private IssueType issueType;
	
	@ManyToOne
	private IssueStatus issueStatus;
	
	@ManyToOne
	private Account manager;
	
	@ManyToOne
	private Account reporter;
	
	@ManyToOne
	private IssuePriority issuePriority;
	
	@ManyToOne
	private Jira jira;
	
	@ManyToOne
	private Team team;
	
	@Builder
	public Issue(String key, String name, String content, LocalDateTime createDate, LocalDateTime editDate,
			LocalDateTime finishDate, LocalDateTime deadlineDate, Integer divOrder,
			Project project, IssueType issueType, IssueStatus issueStatus, Account manager, Account reporter,
			IssuePriority issuePriority, Team team, LocalDateTime startDate, Jira jira) {
		this.key = key;
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.editDate = editDate;
		this.finishDate = finishDate;
		this.deadlineDate = deadlineDate;
		this.divOrder = divOrder;
		this.project = project;
		this.issueType = issueType;
		this.issueStatus = issueStatus;
		this.manager = manager;
		this.reporter = reporter;
		this.issuePriority = issuePriority;
		this.team = team;
		this.startDate = startDate;
		this.jira = jira;
	}
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE) 
	private List<IssueExtends> parentList;
	
	@OneToMany(mappedBy = "child", cascade = CascadeType.REMOVE) 
	private List<IssueExtends> childList;
	
	@OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE) 
	private List<IssueReply> issueReplyList;
	
	@OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE) 
	private List<IssueFile> issueFileList;
	
	@OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE) 
	private List<IssueLikeMembers> issueLikeMembersList;
	
	@OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE) 
	private List<IssueLabelData> issueLabelDataList;
	
	@OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE) 
	private List<ProjectLogData> projectLogDataList;
	
	@OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE)
	private List<IssueRecentClicked> issueClickedList;
	
	public void updateName(String name) {
		this.name = name;
		this.editDate = LocalDateTime.now();
	}
	
	public void updatecontent(String content) {
		this.content = content;
		this.editDate = LocalDateTime.now();
	}
	
	public void updateProjectLogDataList(List<ProjectLogData> projectLogDataList) {
		this.projectLogDataList = projectLogDataList;
	}
	
	public void updateIssueStatus(IssueStatus newStatus) {
		this.issueStatus = newStatus;
		this.editDate = LocalDateTime.now();
	}
	
	public void updateStartDate(LocalDateTime date) {
		this.startDate = date;
		this.editDate = LocalDateTime.now();
	}
	
	public void updateFinishDate(LocalDateTime date) {
		this.finishDate = date;
	}
	
	public void updateDeadlineDate(LocalDateTime date) {
		this.deadlineDate = date;
		this.editDate = LocalDateTime.now();
	}
	
	public void updatePriority(IssuePriority priority) {
		this.issuePriority = priority;
		this.editDate = LocalDateTime.now();
	}
	
	public void updateTeam(Team team) {
		this.team = team;
		this.editDate = LocalDateTime.now();
	}
	
	public void updateReporter(Account reporter) {
		this.reporter = reporter;
		this.editDate = LocalDateTime.now();
	}
	
	public void updateManager(Account manager) {
		this.manager = manager;
		this.editDate = LocalDateTime.now();
	}
	
	public void updateDivOrder(Integer idx) {
		this.divOrder = idx;
	}
	
	public void updateIssueType(IssueType issueType) {
		this.issueType = issueType;
		this.editDate = LocalDateTime.now();
	}
}