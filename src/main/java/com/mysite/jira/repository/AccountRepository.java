package com.mysite.jira.repository;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	// kdw (summation 팀 워크로드)
	@Query("""
			SELECT  NVL(pm.account.name, '할당되지 않음') as name,
			        pm.account.iconFilename as iconFilename,
			        (SELECT count(si.idx) FROM Issue si WHERE NVL(i.manager.idx, 0) = NVL(si.manager.idx, 0) AND si.project.idx = :projectIdx) as count
			FROM    Issue i
			LEFT JOIN    ProjectMembers pm
			ON  pm.account.idx = i.manager.idx
			WHERE pm.project.idx = :projectIdx
			GROUP BY i.manager.idx, pm.account.name, pm.account.iconFilename
			ORDER BY count DESC, i.manager.idx
			""")
	List<Map<String, Object>> findByManagerByIssueCount(@Param("projectIdx") Integer projectIdx); 
	
	Optional<Account> findByIdx(Integer idx);
	
	List<Account> findByName(String name);
	
	List<Account> findAllByEmail(String email);

	//kdw 
	Optional<Account> findByEmail(String email);
	
	Optional<Account> findByKakaoSocialKey(String key);
	
	Optional<Account> findByNaverSocialKey(String key);
}
