package com.mysite.jira.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
public class ProjectLogData {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_log_data_seq")
	@SequenceGenerator(name = "project_log_data_seq", sequenceName = "project_log_data_seq", allocationSize = 1)
	private Integer idx;
	
	@Column
	private LocalDateTime createDate;
	
	@ManyToOne
	private Issue issue;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private ProjectLogStatus projectLogStatus;
	
	@Builder
	public ProjectLogData(Issue issue, Account account, ProjectLogStatus projectLogStatus) {
		this.issue = issue;
		this.account = account;
		this.projectLogStatus = projectLogStatus;
		this.createDate = LocalDateTime.now();
	}
	
}
