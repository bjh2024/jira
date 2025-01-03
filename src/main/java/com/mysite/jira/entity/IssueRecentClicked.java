package com.mysite.jira.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class IssueRecentClicked {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_clicked_seq")
	@SequenceGenerator(name = "issue_clicked_seq", sequenceName = "issue_clicked_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Issue issue;
	
	@ManyToOne
	private Jira jira;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime clickedDate;
	
	@Builder
	public IssueRecentClicked(Integer idx, Account account, Issue issue, Jira jira) {
		this.account = account;
		this.issue = issue;
		this.jira = jira;
		this.clickedDate = LocalDateTime.now();
	}
	
	public void updateClickedDate() {
		this.clickedDate = LocalDateTime.now();
	}
	
}
