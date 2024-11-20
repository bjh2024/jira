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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IssueLabel {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_label_seq")
	@SequenceGenerator(name = "issue_label_seq", sequenceName = "issue_label_seq", allocationSize = 1)
	private Integer idx; 
	

	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;

	@OneToMany(mappedBy = "issueLabel", cascade = CascadeType.REMOVE) 
	private List<IssueLabelData> issueLabelDataList;
}
