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
public class JiraRecentClicked {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jira_clicked_seq")
	@SequenceGenerator(name = "jira_clicked_seq", sequenceName = "jira_clicked_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Jira jira;
	
	@ManyToOne
	private Account account;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime clickedDate;

	@Builder
	public JiraRecentClicked(Integer idx, Jira jira, Account account) {
		this.idx = idx;
		this.jira = jira;
		this.account = account;
		this.clickedDate = LocalDateTime.now();
	}
	
	public void updateDate() {
		this.clickedDate = LocalDateTime.now();
	}
	
	
	
}
