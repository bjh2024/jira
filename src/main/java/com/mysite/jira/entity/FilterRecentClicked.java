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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class FilterRecentClicked {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filter_clicked_seq")
   @SequenceGenerator(name = "filter_clicked_seq", sequenceName = "filter_clicked_seq", allocationSize = 1)
   private Integer idx; 
   
   @ManyToOne
   private Account account;
   
   @ManyToOne
   private Filter filter;
   
   @ManyToOne
   private Jira jira;
   
   @ColumnDefault("sysdate")
   @Column
   private LocalDateTime clickedDate;

	@Builder
	public FilterRecentClicked(Integer idx, Account account, Filter filter, Jira jira) {
	this.account = account;
	this.filter = filter;
	this.jira = jira;
	this.clickedDate = LocalDateTime.now();
}
   
   

}
