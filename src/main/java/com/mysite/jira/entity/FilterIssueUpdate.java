package com.mysite.jira.entity;

import org.hibernate.annotations.ColumnDefault;

import groovy.transform.builder.Builder;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FilterIssueUpdate {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_issue_update_seq")
	@SequenceGenerator(name = "filter_issue_update_seq", sequenceName = "filter_issue_update_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Filter filter;
	
	@ColumnDefault("30")
	@NotNull
	private Integer deadline;

	@Builder
	public FilterIssueUpdate(Filter filter, Integer deadline) {
		this.filter = filter;
		this.deadline = deadline;
	}
	
}
