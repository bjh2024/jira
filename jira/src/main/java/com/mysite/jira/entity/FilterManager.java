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
public class FilterManager {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_manager_seq")
	@SequenceGenerator(name = "filter_manager_seq", sequenceName = "filter_manager_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Filter filter;
	
	@ManyToOne
	private Account account;

	@Builder
	public FilterManager(Filter filter, Account account) {
		this.filter = filter;
		this.account = account;
	}
}
