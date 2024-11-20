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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DashboardAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboard_auth_seq")
	@SequenceGenerator(name = "dashboard_auth_seq", sequenceName = "dashboard_auth_seq", allocationSize = 1)
	private Integer idx;
	
	@Column
	@NotNull
	@Min(1)
	@Max(2)
	private Integer type;
	
	@Column
	@Min(1)
	@Max(3)
	private Integer projectRole;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private Team team;
	
	@ManyToOne
	private Account account;
	
	
}
