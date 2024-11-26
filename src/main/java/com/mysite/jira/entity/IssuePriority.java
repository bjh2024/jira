package com.mysite.jira.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class IssuePriority {
	@Id
	@Min(value = 1)
	@Max(value = 5)
	private String idx;
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;
	
	@Column(columnDefinition = "VARCHAR2(4000)")
	@NotNull
	private String iconFilename;
	
	@Builder
	public IssuePriority(String name, String iconFilename) {
		this.name = name;
		this.iconFilename = iconFilename;
	}
	
	@OneToMany(mappedBy = "issuePriority", cascade = CascadeType.REMOVE) 
	private List<Issue> issueList; 
	
	@OneToMany(mappedBy = "issuePriority", cascade = CascadeType.REMOVE) 
	private List<FilterIssuePriority> FilterIssuePriorityList;
	
}
