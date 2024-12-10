package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Filter;

public interface FilterRepository extends JpaRepository<Filter, Integer>{
	// kdw
	@Query(value="""
			SELECT  f.*
			FROM    filter_recent_clicked frc
			JOIN    filter f
			ON  f.idx = frc.filter_idx
			WHERE   frc.account_idx = :accountIdx
			AND f.jira_idx = :jiraIdx
			    
			minus
			
			SELECT  f.*
			FROM    filter_like_members flm
			JOIN    filter f
			ON  f.idx = flm.filter_idx
			WHERE   flm.account_idx = :accountIdx
			AND f.jira_idx = :jiraIdx
			""", nativeQuery=true)
	List<Filter> findByAccountIdxAndJiraIdxMinusLikeMembers(@Param("accountIdx") Integer accountIdx, 
															@Param("jiraIdx") Integer jiraIdx);
	
}
