package com.mysite.jira.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

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

@Getter
@Entity
@NoArgsConstructor
public class ProjectRecentClicked {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_clicked_seq")
	@SequenceGenerator(name = "project_clicked_seq", sequenceName = "project_clicked_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private Jira jira;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime clickedDate;

	@Builder
	public ProjectRecentClicked(Integer idx, Account account, Project project, Jira jira) {
		this.account = account;
		this.project = project;
		this.jira = jira;
		this.clickedDate = LocalDateTime.now();
	}
}
