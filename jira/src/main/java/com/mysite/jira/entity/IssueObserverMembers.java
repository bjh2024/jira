package com.mysite.jira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class IssueObserverMembers {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_observer_seq")
	@SequenceGenerator(name = "issue_observer_seq", sequenceName = "issue_observer_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Issue issue;
}
