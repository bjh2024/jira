package com.mysite.jira.entity;

import groovy.transform.builder.Builder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class FilterIssueStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_issue_status_seq")
	@SequenceGenerator(name = "filter_issue_status_seq", sequenceName = "filter_issue_status_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Filter filter;
	
	@ManyToOne
	private IssueStatus issueStatus;

	@Builder
	public FilterIssueStatus(Filter filter, IssueStatus issueStatus) {
		this.filter = filter;
		this.issueStatus = issueStatus;
	}
}
