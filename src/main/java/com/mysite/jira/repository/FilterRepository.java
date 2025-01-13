package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Filter;

public interface FilterRepository extends JpaRepository<Filter, Integer>{
	// kdw
	List<Filter> findByFilterClickedList_AccountIdxAndJiraIdxOrderByFilterClickedList_ClickedDateDesc(
																							@Param("accountIdx") Integer accountIdx, 
																							@Param("jiraIdx") Integer jiraIdx);
	List<Filter> findByAccountIdxAndJiraIdxOrderByIdxAsc(Integer accountIdx, Integer jiraIdx);
	@Query(value="""
			SELECT f.*
			FROM filter f
			JOIN filter_recent_clicked frc
			  ON f.idx = frc.filter_idx
			LEFT JOIN filter_like_members flm
			  ON f.idx = flm.filter_idx
			  AND flm.account_idx = :accountIdx
			WHERE frc.account_idx = :accountIdx
			  AND frc.jira_idx = :jiraIdx
			  AND flm.filter_idx IS NULL
			ORDER BY frc.clicked_date DESC
			""", nativeQuery=true)
	List<Filter> findByAccountIdxAndJiraIdxMinusLikeMembers(@Param("accountIdx") Integer accountIdx, 
															@Param("jiraIdx") Integer jiraIdx);
	
	List<Filter> findByJiraIdxAndAccountIdx(Integer jiraIdx,Integer AccountIdx); 
	
	List<Filter> findByJiraIdx(Integer idx);
	
	List<Filter> findByIdxIn(Integer[] idx);
	
	List<Filter> findByIdxInAndAccountIdx(Integer[] idx, Integer accountIdx);
	
}
