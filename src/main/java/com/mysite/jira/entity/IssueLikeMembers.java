package com.mysite.jira.entity;

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
public class IssueLikeMembers {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_like_members_seq")
	@SequenceGenerator(name = "issue_like_members_seq", sequenceName = "issue_like_members_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Issue issue; 

	@Builder
	public IssueLikeMembers(Account account, Issue issue) {
		this.account = account;
		this.issue = issue;
	}
}
