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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class DashboardAllot {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allot_seq")
	@SequenceGenerator(name = "allot_seq", sequenceName = "allot_seq", allocationSize = 1)
	private Integer idx;
	
	@Column
	@NotNull
	private Integer pageNum;
	
	@Column
	@NotNull
	private Integer divOrderX;
	
	@Column
	@NotNull
	private Integer divOrderY;
	
	@Column
	@NotNull
	private Integer isSave;
	
	@ManyToOne
	private Dashboard dashboard;
	
	@Builder
	public DashboardAllot(Integer pageNum, Integer divOrderX, Integer divOrderY , Dashboard dashboard, Integer isSave) {
		this.pageNum = pageNum;
		this.dashboard = dashboard;
		this.divOrderX = divOrderX;
		this.divOrderY = divOrderY;
		this.isSave = isSave;
	}
	
	@OneToMany(mappedBy = "dashboardAllot", cascade = CascadeType.REMOVE)
	private List<DashboardAllotCol> dashboardAllotColList;
	
}
