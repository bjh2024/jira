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
	@Query(value = """
		    SELECT icon_filename, name, clicked_date
		    FROM
		    (SELECT p.icon_filename, p.name, prc.clicked_date
		     FROM project_recent_clicked prc
		     JOIN Jira j ON j.idx = prc.jira_idx
		     JOIN project p ON p.idx = prc.project_idx
		     WHERE prc.account_idx = :accountIdx
		     AND   j.idx = :jiraIdx
		     UNION
		     SELECT 'issue_icon.svg' as icon_filename, i.name, irc.clicked_date
		     FROM issue_recent_clicked irc
		     JOIN Jira j ON j.idx = irc.jira_idx
		     JOIN issue i ON i.idx = irc.issue_idx
		     WHERE irc.account_idx = :accountIdx
		     AND   j.idx = :jiraIdx
		     UNION
		     SELECT 'dashboard_icon.svg' as icon_filename, d.name, drc.clicked_date
		     FROM dashboard_recent_clicked drc
		     JOIN Jira j ON j.idx = drc.jira_idx
		     JOIN dashboard d ON d.idx = drc.dashboard_idx
		     WHERE drc.account_idx = :accountIdx
		     AND   j.idx = :jiraIdx
		     UNION
		     SELECT 'filter_icon.svg' as icon_filename, f.name, frc.clicked_date
		     FROM filter_recent_clicked frc
		     JOIN Jira j ON j.idx = frc.jira_idx
		     JOIN filter f ON f.idx = frc.filter_idx
		     WHERE frc.account_idx = :accountIdx
		     AND   j.idx = :jiraIdx
		     )
		     WHERE clicked_date BETWEEN :startDate AND :endDate
		     ORDER BY clicked_date DESC
		""", nativeQuery = true)
		List<Map<String, Object>> findClickedDataOrderByDateDesc(@Param("accountIdx") Integer accountIdx,
															     @Param("jiraIdx") Integer jiraIdx,
			                                              	     @Param("startDate") LocalDateTime startDate,
			                                              	     @Param("endDate") LocalDateTime endDate);
}
