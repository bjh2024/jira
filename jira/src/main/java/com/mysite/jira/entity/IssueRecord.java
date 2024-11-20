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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IssueRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jira_seq")
	@SequenceGenerator(name = "jira_seq", sequenceName = "jira_seq", allocationSize = 1)
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
}
