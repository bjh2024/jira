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
public class DashboardAllot implements DashboardItem{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allot_seq")
	@SequenceGenerator(name = "allot_seq", sequenceName = "allot_seq", allocationSize = 1)
	private Integer idx;
	
	@Column
	private Integer rowNum;
	
	@Column
	@NotNull
	private Integer divOrderX;
	
	@NotNull
	private Integer divOrderY;
	
	public void updateOrder(Integer divOrderX, Integer divOrderY) {
		this.divOrderX = divOrderX;
		this.divOrderY = divOrderY;
	}
	
	@Column
	@NotNull
	private Integer isSave;
	
	@ManyToOne
	private Dashboard dashboard;
	
	@Builder
	public DashboardAllot(Integer rowNum, Integer divOrderX, Integer divOrderY, Dashboard dashboard, Integer isSave) {
		this.rowNum = rowNum;
		this.dashboard = dashboard;
		this.divOrderX = divOrderX;
		this.divOrderY = divOrderY;
		this.isSave = isSave;
	}
	
	public void updateAllot(Integer rowNum) {
		this.rowNum = rowNum;
		this.isSave = 1;
	}
	
	@OneToMany(mappedBy = "dashboardAllot", cascade = CascadeType.REMOVE)
	private List<DashboardAllotCol> dashboardAllotColList;
	
}
