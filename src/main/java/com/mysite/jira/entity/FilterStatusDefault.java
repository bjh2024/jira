package com.mysite.jira.entity;

import groovy.transform.builder.Builder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class FilterStatusDefault {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_status_default_seq")
	@SequenceGenerator(name = "filter_status_default_seq", sequenceName = "filter_status_default_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Filter filter;
	
	@ManyToOne
	private IssueStatus issueStatus;

	@Builder
	public FilterStatusDefault(Filter filter, IssueStatus issueStatus) {
		this.filter = filter;
		this.issueStatus = issueStatus;
	}
}
