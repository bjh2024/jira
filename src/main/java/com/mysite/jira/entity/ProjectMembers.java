package com.mysite.jira.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ProjectMembers {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_members_seq")
	@SequenceGenerator(name = "project_members_seq", sequenceName = "project_members_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private Account account;
	
	@Column
	@NotNull
	@Min(value = 1)
	@Max(value = 3)
	private Integer auth_type;

	@Builder
	public ProjectMembers(Project project, Account account, Integer auth_type) {
		this.project = project;
		this.account = account;
		this.auth_type = auth_type;
	}
}
