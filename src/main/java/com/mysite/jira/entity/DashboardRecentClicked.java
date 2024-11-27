package com.mysite.jira.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
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
public class DashboardRecentClicked {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dash_clicked_seq")
	@SequenceGenerator(name = "dash_clicked_seq", sequenceName = "dash_clicked_seq", allocationSize = 1)
	private Integer idx; 
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Dashboard dashboard;
	
	@ManyToOne
	private Jira jira;
	
	@ColumnDefault("sysdate")
	@Column
	private LocalDateTime clickedDate;
}
