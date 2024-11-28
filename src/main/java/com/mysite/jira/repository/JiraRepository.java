package com.mysite.jira.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Jira;

public interface JiraRepository extends JpaRepository<Jira, Integer> {

	// account_idx로 name(jira 리더) 가져오기 kdw
	@Query(value = "SELECT j.name FROM jira j JOIN jira_members jm ON j.idx = jm.jira_idx "
			+ "WHERE jm.account_idx = :accountIdx", nativeQuery = true)
	List<String> findJiraAndMembersByAccountIdxName(@Param("accountIdx") Integer accountIdx);

	// 모든 최근 클릭 테이블 unio kdw
	@Query("""
		    SELECT al.iconFilename AS iconFilename, al.name AS name, al.clickedDate AS clickedDate
		    FROM (
		        SELECT p.iconFilename AS iconFilename, p.name AS name, prc.clickedDate AS clickedDate
		        FROM ProjectRecentClicked prc
		        JOIN Jira j ON j.idx = prc.jira.idx
		        JOIN Project p ON p.idx = prc.project.idx
		        WHERE prc.account.idx = :accountIdx
		        AND j.idx = :jiraIdx
		        UNION
		        SELECT i.issueType.iconFilename AS iconFilename, i.name AS name, irc.clickedDate AS clickedDate
		        FROM IssueRecentClicked irc
		        JOIN Jira j ON j.idx = irc.jira.idx
		        JOIN Issue i ON i.idx = irc.issue.idx
		        WHERE irc.account.idx = :accountIdx
		        AND j.idx = :jiraIdx
		        UNION
		        SELECT 'dashboard_icon.svg' AS iconFilename, d.name AS name, drc.clickedDate AS clickedDate
		        FROM DashboardRecentClicked drc
		        JOIN Jira j ON j.idx = drc.jira.idx
		        JOIN Dashboard d ON d.idx = drc.dashboard.idx
		        WHERE drc.account.idx = :accountIdx
		        AND j.idx = :jiraIdx
		        UNION
		        SELECT 'filter_icon.svg' AS iconFilename, f.name AS name, frc.clickedDate AS clickedDate
		        FROM FilterRecentClicked frc
		        JOIN Jira j ON j.idx = frc.jira.idx
		        JOIN Filter f ON f.idx = frc.filter.idx
		        WHERE frc.account.idx = :accountIdx
		        AND j.idx = :jiraIdx
		    ) al
		    WHERE al.clickedDate BETWEEN :startDate AND :endDate
		    ORDER BY al.clickedDate DESC
		""")
		List<Map<String, Object>> findClickedDataOrderByDateDesc(@Param("accountIdx") Integer accountIdx,
															     @Param("jiraIdx") Integer jiraIdx,
			                                              	     @Param("startDate") LocalDateTime startDate,
			                                              	     @Param("endDate") LocalDateTime endDate);
}
