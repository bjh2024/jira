package com.mysite.jira.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IssueFieldType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field_type_seq")
	@SequenceGenerator(name = "field_type_seq", sequenceName = "field_type_seq", allocationSize = 1)
	private Integer idx;
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;
	
	@Min(1)
	@Max(2)
	@NotNull
	private Integer type;

	
}
