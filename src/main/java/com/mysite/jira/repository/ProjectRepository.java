package com.mysite.jira.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	List<Project> findByJiraIdx(Integer jiraIdx);
	// kdw
	List<Project> findByProjectClickedList_AccountIdxAndJiraIdxOrderByProjectClickedList_ClickedDateDesc(
						@Param("accountIdx") Integer accountIdx,
						@Param("jiraIdx") Integer jiraIdx);
	List<Project> findByJiraIdxOrderByProjectClickedList_ClickedDateDesc(
			@Param("jiraIdx") Integer jiraIdx);
	// kdw
	@Query(value="""
			SELECT  name,
			        icon_filename,
			        project_key
			FROM(SELECT  p.jira_idx, 
				         p.name, 
				         plm.account_idx,
				         p.icon_filename,
				         p.key as project_key
				 FROM    project p
				 JOIN    project_like_members plm
				 ON  p.idx = plm.project_idx
				 UNION
				 SELECT  d.jira_idx, 
				         d.name, 
				         dlm.account_idx, 
				         'dashboard_icon.svg' as icon_filename,
				         '' as project_key
				 FROM    dashboard d
				 JOIN    dashboard_like_members dlm
				 ON  d.idx = dlm.dashboard_idx
				 UNION
				 SELECT  f.jira_idx, 
				         f.name, 
				         flm.account_idx,
				         'filter_icon.svg' as icon_filename,
				         '' as project_key
				 FROM    filter f
				 JOIN    filter_like_members flm
				 ON  f.idx = flm.filter_idx)
			WHERE   jira_idx = :jiraIdx
			AND     account_idx = :accountIdx
			""", nativeQuery = true)
	List<Map<String, Object>> findLikeMembers(@Param("jiraIdx") Integer jiraIdx, @Param("accountIdx") Integer accountIdx);
	
	// kdw
	@Query(value="""
			SELECT  p.name,
				    p.color,
			        p.icon_filename,
			        count(i.idx) AS issue_count
			FROM    project p
			JOIN    project_recent_clicked prc
			ON      p.idx = prc.project_idx
			JOIN    issue i
			ON      p.idx = i.project_idx
			JOIN    issue_status ist
			ON      ist.idx = i.issue_status_idx
			WHERE   ist.status BETWEEN 1 AND 2
			AND     prc.account_idx = :accountIdx
			AND     p.jira_idx = :jiraIdx
			GROUP BY p.name, p.color, p.icon_filename, prc.clicked_date
			ORDER BY prc.clicked_date DESC
			""", nativeQuery = true)
    List<Map<String, Object>> findProjectIssueCounts(@Param("accountIdx") Integer accountIdx, @Param("jiraIdx") Integer jiraIdx);

	// 지라가 1인 프로젝트의 모든 유형을 가져와서 distinct
	@Query("SELECT DISTINCT iss.status, iss.name "
		     + "FROM IssueStatus iss "
		     + "WHERE iss.project.idx IN ( "
		     + "    SELECT p.idx "
		     + "    FROM Project p "
		     + "    WHERE p.jira.idx = :jiraIdx )"
		     + "    ORDER BY iss.status Desc" )
		List<Object[]> findDistinctStatusAndNameByJiraIdx(@Param("jiraIdx") Integer jiraIdx);


}
