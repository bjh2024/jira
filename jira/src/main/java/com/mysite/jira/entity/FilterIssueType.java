package com.mysite.jira.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
public class FilterIssueType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_issue_type_seq")
	@SequenceGenerator(name = "filter_issue_type_seq", sequenceName = "filter_issue_type_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Filter filter;
	
	@ManyToOne
	private IssueType issueType;

	@Builder
	public FilterIssueType(Filter filter, IssueType issutype) {
		this.filter = filter;
		this.issueType = issutype;
	}
}
