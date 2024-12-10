package com.mysite.jira.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Dashboard;

public interface DashboardRepository extends JpaRepository<Dashboard,Integer>{
	
	Optional<Dashboard> findById(Integer idx);
	
	// kdw
	@Query(value="""
			SELECT  d.*
			FROM    dashboard_recent_clicked drc
			JOIN    dashboard d
			ON  d.idx = drc.dashboard_idx
			WHERE   drc.account_idx = :accountIdx
			AND d.jira_idx = :jiraIdx
			    
			minus
			
			SELECT  d.*
			FROM    dashboard_like_members dlm
			JOIN    dashboard d
			ON  d.idx = dlm.dashboard_idx
			WHERE   dlm.account_idx = :accountIdx
			AND d.jira_idx = :jiraIdx
			""", nativeQuery=true)
	List<Dashboard> findByAccountIdxAndJiraIdxMinusLikeMembers(@Param("accountIdx") Integer accountIdx,
															   @Param("jiraIdx") Integer jiraIdx);
	
	// kdw
	@Query("""
			SELECT  d as dashboard,
			        COUNT(dlm.account.idx) AS likeCount,
			        MAX(CASE WHEN dlm.account.idx = :accountIdx THEN 'true' ELSE 'false' END) AS isLike
			FROM    Dashboard d
			LEFT JOIN DashboardLikeMembers dlm
			ON      d.idx = dlm.dashboard.idx
			WHERE   d.jira.idx = :jiraIdx
			GROUP BY d
			ORDER BY isLike DESC, likeCount DESC
			""")
	List<Map<String, Object>> findByDashboardList(@Param("accountIdx") Integer accountIdx, @Param("jiraIdx") Integer jiraIdx);
	
	
}
