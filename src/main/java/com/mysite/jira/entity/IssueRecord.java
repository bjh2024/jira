package com.mysite.jira.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class IssueRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_record_seq")
	@SequenceGenerator(name = "issue_record_seq", sequenceName = "issue_record_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column(columnDefinition = "VARCHAR2(100)")
	private String prevValue;
	
	@Column(columnDefinition = "VARCHAR2(100)")
	private String afterValue;
	
	@Column(columnDefinition = "VARCHAR2(2000)")
	private String content;
	
	@NotNull
	@Column
	private LocalDateTime createDate;
	
	@ManyToOne
	private Issue issue;
	
	@ManyToOne
	private Account account;
	
	@Builder
	public IssueRecord(String prevValue, String afterValue, String content, LocalDateTime createDate, Issue issue,
			Account account) {
		this.prevValue = prevValue;
		this.afterValue = afterValue;
		this.content = content;
		this.createDate = createDate;
		this.issue = issue;
		this.account = account;
	}
}
