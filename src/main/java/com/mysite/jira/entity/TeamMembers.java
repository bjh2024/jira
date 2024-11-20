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

@Entity
@Getter
@NoArgsConstructor
public class TeamMembers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_members_seq")
	@SequenceGenerator(name = "team_members_seq", sequenceName = "team_members_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Team team;
	
	@ManyToOne
	private Account account;

	@Builder
	public TeamMembers(Team team, Account account) {
		this.team = team;
		this.account = account;
	}
}
