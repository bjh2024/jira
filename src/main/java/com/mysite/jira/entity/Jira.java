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
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Jira {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jira_seq")
	@SequenceGenerator(name = "jira_seq", sequenceName = "jira_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;
	
	@ManyToOne
	private Account account;
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<Project> projectList; 
	
	// 팀 목록 FK 추가
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE) 
	private List<Team> teamList;
	
	// 대시보드 FK 추가
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE)
	private List<Dashboard> dashbaordList;
	
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE)
	private List<JiraMembers> JiraMembersList;
	
	// Filter FK 추가
	@OneToMany(mappedBy = "jira", cascade = CascadeType.REMOVE)
	private List<Filter> filterList;
}