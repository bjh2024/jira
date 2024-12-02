package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.dto.IssueTypeListDTO;
import com.mysite.jira.entity.IssueType;

public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {

	// distinct한 name과 iconFilename을 가져오는 쿼리
	@Query("""
			SELECT it.name, it.iconFilename
			FROM IssueType it JOIN Project p 
			on it.project.idx = p.idx
			where p.jira.idx = :jiraIdx
			GROUP BY it.name, it.iconFilename
			""")
	List<Object[]> findDistinctNameAndIconFilenameByJiraIdx(@Param("jiraIdx") Integer jiraIdx);
	
 	List<IssueType> findByProjectIdxAndGradeGreaterThan(Integer projectIdx, Integer grade);
 	
	
}
