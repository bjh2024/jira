package com.mysite.jira.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Jira;

public interface JiraRepository extends JpaRepository<Jira, Integer> {

	Optional<Jira> findIdxByName(String name);
	// kdw
	Optional<Jira> findByName(String name);

	// kdw jira로그인정보와 지라이름이 같은 지라의 개수
	Integer countByNameAndJiraMembersList_AccountIdx(String name, Integer accountIdx);
	
	// kdw 최근 방문 jira
	List<Jira> findByAccountIdxOrderByJiraClickedList_ClickedDateDesc(Integer accountIdx);
	
	// 모든 최근 클릭 테이블 unio kdw
	@Query("""
			SELECT iconFilename as iconFilename,
				   name as name,
				   projectName as projectName,
				   key as key,
				   clickedDate as clickedDate
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
	List<Map<String, Object>> findClickedDataOrderByDateDesc(@Param("accountIdx") Integer accountIdx,
			@Param("jiraIdx") Integer jiraIdx, @Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	
	Optional<Jira> findByIdx(Integer idx);
}
