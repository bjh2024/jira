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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IssueReply {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_reply_seq")
	@SequenceGenerator(name = "issue_reply_seq", sequenceName = "issue_reply_seq", allocationSize = 1)
	private Integer idx;
	
	@Column(columnDefinition = "VARCHAR2(4000)")
	@NotNull
	private String content;
	
	@Column
	@ColumnDefault("sysdate")
	private LocalDateTime createDate;
	
	@Column
	@ColumnDefault("sysdate")
	private LocalDateTime editDate;
	
	@ManyToOne
	private Issue issue;
	
	@ManyToOne
	private Account account;
	
	// emojiList FK 생성
	@OneToMany(mappedBy = "issueReply", cascade = CascadeType.REMOVE) 
	private List<ReplyEmojiRecord> replyEmojiList;
	
}
