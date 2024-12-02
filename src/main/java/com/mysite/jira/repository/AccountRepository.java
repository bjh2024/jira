package com.mysite.jira.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	// kdw (summation 팀 워크로드)
	@Query("""
			SELECT  a.name as name,
			        a.iconFilename as iconFilename,
			        count(i.idx) as count
			FROM    Issue i
			LEFT JOIN    Account a
			ON  a.idx = i.manager.idx
			WHERE   i.project.idx = :projectIdx
			GROUP BY i.manager.idx, a.name, a.iconFilename
			ORDER BY count(i.idx) DESC, i.manager.idx
			""")
	List<Map<String, Object>> findByManagerByIssueCount(@Param("projectIdx") Integer projectIdx); 
}
