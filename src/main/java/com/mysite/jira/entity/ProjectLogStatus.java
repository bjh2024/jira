package com.mysite.jira.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProjectLogStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_log_status_seq")
	@SequenceGenerator(name = "project_log_status_seq", sequenceName = "project_log_status_seq", allocationSize = 1)
	private Integer idx;
	
	@Column(columnDefinition = "VARCHAR2(500)")
	@NotNull
	private String content;
	
	@OneToMany(mappedBy = "projectLogStatus", cascade = CascadeType.REMOVE) 
	private List<ProjectLogData> projectLogDataList;
	
}
