package com.mysite.jira.entity;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import groovy.transform.builder.Builder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class FilterDone {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_done_seq")
	@SequenceGenerator(name = "filter_done_seq", sequenceName = "filter_done_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Filter filter;
	
	@ColumnDefault("0")
	@Min(0)
	@Max(1)
	private Integer	isCompleted;

	@Builder
	public FilterDone(Filter filter,Integer isCompleted) {
		this.filter = filter;
		this.isCompleted = isCompleted;
	}
}
