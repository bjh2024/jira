package com.mysite.jira.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.IssuePriority;

public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Integer>{

	// kdw 우선순위 분석
	@Query("""
			SELECT  ip.name as name,
			        count(i.name) as count
			FROM    IssuePriority ip
			LEFT JOIN    Issue i
			ON  ip.idx = i.issuePriority.idx AND i.project.idx = :projectIdx
			GROUP BY ip.name, ip.idx
			ORDER BY ip.idx DESC
			""")
	List<Map<String, Object>> findByPriorityByIssueCount(@Param("projectIdx") Integer projectIdx);
	
}
