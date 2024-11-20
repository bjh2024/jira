package com.mysite.jira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class IssueLabelData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "label_data_seq")
	@SequenceGenerator(name = "lable_data_seq", sequenceName = "label_data_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private IssueLabel issueLabel;
	
	@ManyToOne
	private Issue issue; 

}
