package com.mysite.jira.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class FilterDoneDate {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_doned_ate_seq")
	@SequenceGenerator(name = "filter_doned_ate_seq", sequenceName = "filter_doned_ate_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Filter filter;
	
	@ColumnDefault("30")
	@NotNull
	private Integer beforeDate;

	@Column
	private LocalDateTime startDate;
	
	@Column
	private LocalDateTime endDate;

	@Builder
	public FilterDoneDate(Filter filter, Integer beforeDate, LocalDateTime startDate, LocalDateTime endDate) {
		this.filter = filter;
		this.beforeDate = beforeDate;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	

}
