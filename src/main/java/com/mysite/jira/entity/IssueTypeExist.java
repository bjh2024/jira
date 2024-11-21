package com.mysite.jira.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class IssueTypeExist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_type_exist_seq")
	@SequenceGenerator(name = "issue_type_exist_seq", sequenceName = "issue_type_exist_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column
	@Min(0)
	@Max(1)
	private Integer isPriority;
	
	@Column
	@Min(0)
	@Max(1)
	private Integer isLabel;
	
	@Column
	@Min(0)
	@Max(1)
	private Integer isEndDate;
	
	@Column
	@Min(0)
	@Max(1)
	private Integer isStartDate;
	
	@Column
	@Min(0)
	@Max(1)
	private Integer isTeam;
	
	@Column
	@Min(0)
	@Max(1)
	private Integer isReport;
	
	@OneToOne
	private IssueType issueType;
}
