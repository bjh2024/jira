package com.mysite.jira.entity;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.OnDelete;

import groovy.transform.builder.Builder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Filter {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_seq")
	@SequenceGenerator(name = "filter_seq", sequenceName = "filter_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column(columnDefinition = "VARCHAR2(500)")
	private String name;
	
	@Column(columnDefinition = "VARCHAR2(4000)")
	private String explain;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Jira jira;
	
	@Builder
	public Filter(String name, String explain, Account account, Jira jira) {
		this.name = name;
		this.explain = explain;
		this.account = account;
		this.jira = jira;
	}
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterAuth> filterAuthList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterDoneDate> filterDoneDateList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterDone> filterDoneList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterIssueUpdate> filterIssueUpdateList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterProject> FilterProjectList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterIssueType> FilterIssueTypeList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterIssueStatus> FilterIssueStatusList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterManager> FilterManagerList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterIssuePriority> FilterIssuePriorityList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterLikeMembers> FilterLikeMembersList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterStatusDefault> FilterStatusDefaultList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterReporter> FilterReporterList;
	
	@OneToMany(mappedBy = "filter",orphanRemoval = true) 
	private List<FilterRecentClicked> filterClickedList;
	
}
