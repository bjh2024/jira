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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class IssueType {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_type_seq")
	@SequenceGenerator(name = "issue_type_seq", sequenceName = "issue_type_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;
	
	@Column(columnDefinition = "VARCHAR2(2000)")
	private String content;
	
	@Column(columnDefinition = "VARCHAR2(2000)")
	private String subContent;
	
	@Column(columnDefinition = "VARCHAR2(300)")
	@NotNull
	private String iconFilename;
	
	@Column
	@NotNull
	@Min(value = 1)
	@Max(value = 3)
	private Integer grade;
	
	@ManyToOne
	private Project project;
	
	@Builder
	public IssueType(String name, String content, String subContent, String iconFilename, Integer grade,
			Project project) {
		this.name = name;
		this.content = content;
		this.subContent = subContent;
		this.iconFilename = iconFilename;
		this.grade = grade;
		this.project = project;
	}
	
	@OneToOne(mappedBy = "issueType")
	private IssueTypeExist issTypeExist;
	
	@OneToMany(mappedBy = "issueType", cascade = CascadeType.REMOVE) 
	private List<Issue> issueList; 
	
	@OneToMany(mappedBy = "issueType", cascade = CascadeType.REMOVE) 
	private List<IssueField> issueFieldList; 
}
