package com.mysite.jira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class IssueField {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_field_seq")
	@SequenceGenerator(name = "issue_field_seq", sequenceName = "issue_field_seq", allocationSize = 1)
	private Integer idx;

	@ManyToOne
	private IssueType issueType;

	@ManyToOne
	private IssueFieldType issueFieldType;

	@Builder
	public IssueField(IssueType issueType, IssueFieldType issueFieldType) {
		this.issueType = issueType;
		this.issueFieldType = issueFieldType;
	}

}
