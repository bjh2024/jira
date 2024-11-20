package com.mysite.jira.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import groovy.transform.builder.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class FilterAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filterAuth_seq")
	@SequenceGenerator(name = "filterAuth_seq", sequenceName = "filterAuth_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column
	private Integer type;
	
	@Column
	private Integer projectRole;
	
	@ManyToOne
	private Filter filter;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Team team;
	
	@Builder
	public FilterAuth(Integer type, Integer projectRole, Filter filter, Project project, Account account, Team team) {
		this.type = type;
		this.projectRole = projectRole;
		this.filter = filter;
		this.project = project;
		this.account = account;
		this.team = team;
	}


}
