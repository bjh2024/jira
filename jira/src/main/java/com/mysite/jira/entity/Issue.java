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
import jakarta.persistence.JoinTable;
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
	@NotNull
	private String conetent;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime createDate;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime editDate;
	
	@Column
	private LocalDateTime finishDate;
	
	@Column
	private LocalDateTime deadlineDate;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime clickedDate;
	
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
	private List<IssueObserverMembers> issueObserverList;
	
	@OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE) 
	private List<IssueLabelData> issueLabelDataList;
	
	// emojiList FK 생성
	@OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE) 
	private List<ReplyEmojiRecord> replyEmojiList;
}
