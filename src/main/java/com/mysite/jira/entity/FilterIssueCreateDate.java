package com.mysite.jira.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

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
@AllArgsConstructor
public class FilterIssueCreateDate {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_issue_create_date_seq")
	@SequenceGenerator(name = "filter_issue_create_date_seq", sequenceName = "filter_issue_create_date_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Filter filter;
	
	@ColumnDefault("30")
	private Integer BeforeDate;

	@Column
	private LocalDateTime startDate;
	
	@Column
	private LocalDateTime endDate;
	
	@Builder
	public FilterIssueCreateDate(Filter filter, Integer BeforeDate, LocalDateTime startDate, LocalDateTime endDate) {
		this.filter = filter;
		this.BeforeDate = BeforeDate;
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
