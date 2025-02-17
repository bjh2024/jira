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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IssueFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_file_seq")
	@SequenceGenerator(name = "issue_file_seq", sequenceName = "issue_file_seq", allocationSize = 1)
	private Integer idx;
	
	@Column(columnDefinition = "VARCHAR2(300)")
	@NotNull
	private String name;
	
	@Column
	private LocalDateTime uploadDate;
	
	@ManyToOne
	private Issue issue;
	
	@ManyToOne
	private Account account;

	@Builder
	public IssueFile(String name, LocalDateTime uploadDate, Issue issue, Account account) {
		this.name = name;
		this.uploadDate = uploadDate;
		this.issue = issue;
		this.account = account;
	}
	
}
