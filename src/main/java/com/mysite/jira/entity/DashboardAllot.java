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
	
	@Column(columnDefinition = "VARCHAR2(20)")
	private String divOrder;
	
	@Column
	@NotNull
	private Integer isSave;
	
	@ManyToOne
	private Dashboard dashboard;
	
	@Builder
	public DashboardAllot(Integer pageNum, String divOrder, Dashboard dashboard, Integer isSave) {
		this.pageNum = pageNum;
		this.divOrder = divOrder;
		this.dashboard = dashboard;
		this.isSave = isSave;
	}
	
	@OneToMany(mappedBy = "dashboardAllot", cascade = CascadeType.REMOVE)
	private List<DashboardAllotCol> dashboardAllotColList;
	
}
