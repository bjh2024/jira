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
import lombok.Builder;
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
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterAuth> filterAuthList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterDoneDate> filterDoneDateList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterDone> filterDoneList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterIssueUpdate> filterIssueUpdateList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterProject> FilterProjectList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterIssueType> FilterIssueTypeList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterIssueStatus> FilterIssueStatusList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterManager> FilterManagerList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterIssuePriority> FilterIssuePriorityList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterLikeMembers> FilterLikeMembersList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterStatusDefault> FilterStatusDefaultList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterReporter> FilterReporterList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE) 
	private List<FilterRecentClicked> filterClickedList;
	
	@OneToMany(mappedBy = "filter", cascade = CascadeType.REMOVE)
	private List<FilterIssueCreateDate> filterIssueCreateDates;
	
}
