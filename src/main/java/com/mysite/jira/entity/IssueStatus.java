package com.mysite.jira.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class IssueStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_status_seq")
	@SequenceGenerator(name = "issue_status_seq", sequenceName = "issue_status_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column
	@NotNull
	@Min(value = 1)
	@Max(value = 3)
	private Integer status;
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;
	
	@Column
	@NotNull
	private Integer divOrder;
	
	@ManyToOne
	private Project project;
	
	@OneToMany(mappedBy = "issueStatus", cascade = CascadeType.REMOVE) 
	private List<Issue> issueList; 

	@OneToMany(mappedBy = "issueStatus", cascade = CascadeType.REMOVE) 
	private List<FilterIssueStatus> FilterIssueStatusList; 
	
	@OneToMany(mappedBy = "issueStatus", cascade = CascadeType.REMOVE) 
	private List<FilterStatusDefault> FilterStatusDefaultList; 
}