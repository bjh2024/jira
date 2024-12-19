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

@Getter
@Entity
@NoArgsConstructor
public class FilterIssuePriority {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_issue_priority_seq")
	@SequenceGenerator(name = "filter_issue_priority_seq", sequenceName = "filter_issue_priority_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Filter filter;
	
	@ManyToOne
	private IssuePriority issuePriority;

	@Builder
	public FilterIssuePriority(Filter filter, IssuePriority issuePriority) {
		this.filter = filter;
		this.issuePriority = issuePriority;
	}
}
