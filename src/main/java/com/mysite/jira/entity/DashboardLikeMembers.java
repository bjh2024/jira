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
public class DashboardLikeMembers {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dash_like_members_seq")
	@SequenceGenerator(name = "dash_like_members_seq", sequenceName = "dash_like_members_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Dashboard dashboard; 
	
	@Builder
	public DashboardLikeMembers(Account account, Dashboard dashboard) {
		this.account = account;
		this.dashboard = dashboard;
	} 
}
