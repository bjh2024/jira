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
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class FilterProject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_project_seq")
	@SequenceGenerator(name = "filter_project_seq", sequenceName = "filter_project_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Filter filter;
	
	@ManyToOne
	private Project project;

	@Builder
	public FilterProject(Filter filter, Project project) {
		this.filter = filter;
		this.project = project;
	}
}
