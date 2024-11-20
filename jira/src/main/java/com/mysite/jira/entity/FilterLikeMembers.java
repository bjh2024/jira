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
import net.bytebuddy.utility.nullability.MaybeNull;

@Getter
@Entity
@NoArgsConstructor
public class FilterLikeMembers {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_like_members_seq")
	@SequenceGenerator(name = "filter_like_members_seq", sequenceName = "filter_like_members_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Filter filter;
	
	@ManyToOne
	private Account account;

	@Builder
	public FilterLikeMembers(Filter filter, Account account) {
		this.filter = filter;
		this.account = account;
	}
	
}
