package com.mysite.jira.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DashboardIssueComplete {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_complete_seq")
	@SequenceGenerator(name = "issue_complete_seq", sequenceName = "issue_complete_seq", allocationSize = 1)
	private Integer idx;
	
	@Column
	@NotNull
	private Integer viewDate;
	
	@Column(columnDefinition = "VARCHAR2(20)")
	@NotNull
	private String divOrder;
	
	@ManyToOne
	private Dashboard dashboard;
	
	@ManyToOne
	private Project project;
	
}
