package com.mysite.jira.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class JiraMembers {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jira_members_seq")
	@SequenceGenerator(name = "jira_members_seq", sequenceName = "jira_members_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Jira jira;
	
	@ManyToOne
	private Account account;
	
	@Builder
	public JiraMembers(Jira jira, Account account, LocalDateTime clickedDate) {
		this.jira = jira;
		this.account = account;
	}
}
