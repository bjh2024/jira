package com.mysite.jira.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.dto.AllRecentDTO;
import com.mysite.jira.entity.Jira;

public interface JiraRepository extends JpaRepository<Jira, Integer> {

	// account_idx로 name(jira 리더) 가져오기 kdw
	@Query(value = "SELECT j.name FROM jira j JOIN jira_members jm ON j.idx = jm.jira_idx "
			+ "WHERE jm.account_idx = :accountIdx", nativeQuery = true)
	List<String> findJiraAndMembersByAccountIdxName(@Param("accountIdx") Integer accountIdx);

	// 모든 최근 클릭 테이블 unio kdw
	@Query("""
			SELECT new com.mysite.jira.dto.AllRecentDTO(iconFilename, name, projectName, key, clickedDate)
			FROM(
				SELECT  i.issueType.iconFilename as iconFilename, i.name as name, i.project.name as projectName, i.key as key, irc.clickedDate as clickedDate
				FROM    IssueRecentClicked irc
				JOIN    Issue i
				ON  i.idx = irc.issue.idx
				WHERE   irc.account.idx = :accountIdx
				AND i.jira.idx = :jiraIdx
				UNION
				SELECT  p.iconFilename as iconFilename, p.name as name, '' as projectName, '' as key, prc.clickedDate as clickedDate
				FROM    ProjectRecentClicked prc
				JOIN    Project p
				ON  p.idx = prc.project.idx
				WHERE   prc.account.idx = :accountIdx
				AND p.jira.idx = :jiraIdx
				UNION
				SELECT  'dashboard_icon.svg' as iconFilename, d.name as name, '' as projectName, '' as key, drc.clickedDate as clickedDate
				FROM    DashboardRecentClicked drc
				JOIN    Dashboard d
				ON  d.idx = drc.dashboard.idx
				WHERE   drc.account.idx = :accountIdx
				AND d.jira.idx = :jiraIdx
				UNION
				SELECT  'filter_icon.svg' as iconFilename, f.name as name, '' as projectName, '' as key, frc.clickedDate as clickedDate
				FROM    FilterRecentClicked frc
				JOIN    Filter f
				ON  f.idx = frc.filter.idx
				WHERE   frc.account.idx = :accountIdx
				AND f.jira.idx = :jiraIdx
			)
			WHERE clickedDate BETWEEN :startDate AND :endDate
			ORDER BY clickedDate Desc
					""")
	List<AllRecentDTO> findClickedDataOrderByDateDesc(@Param("accountIdx") Integer accountIdx,
															@Param("jiraIdx") Integer jiraIdx, 
															@Param("startDate") LocalDateTime startDate,
															@Param("endDate") LocalDateTime endDate);
}
