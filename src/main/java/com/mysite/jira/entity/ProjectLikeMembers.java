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
public class ProjectLikeMembers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_like_members_seq")
	@SequenceGenerator(name = "project_like_members_seq", sequenceName = "project_like_members_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private Account account;

	@Builder
	public ProjectLikeMembers(Project project, Account account) {
		this.project = project;
		this.account = account;
	}
}
