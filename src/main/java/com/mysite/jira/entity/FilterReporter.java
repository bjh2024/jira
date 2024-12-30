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
public class FilterReporter {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_reporter_seq")
	@SequenceGenerator(name = "filter_reporter_seq", sequenceName = "filter_reporter_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Filter filter;
	
	@ManyToOne
	private Account account;

	@Builder
	public FilterReporter(Filter filter, Account account) {
		this.filter = filter;
		this.account = account;
	}
}
