package com.mysite.jira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class IssueExtends {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_extends_seq")
	@SequenceGenerator(name = "issue_extends_seq", sequenceName = "issue_extends_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "parent")
	private Issue parent;
	
	@ManyToOne
	@JoinColumn(name = "child")
	private Issue child;
	
	@Builder
	public IssueExtends(Project project, Issue parent, Issue child) {
		this.project = project;
		this.parent = parent;
		this.child = child;
	}
}
