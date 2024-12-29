package com.mysite.jira.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.IssueType;

public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {
	// kdw 작업 유형(summation)
	@Query("""
			SELECT  it.name as name,
			        it.iconFilename as iconFilename,
			        count(i.idx) as count
			FROM    IssueType it
			LEFT JOIN   Issue i
			ON  it.idx = i.issueType.idx
			WHERE   it.project.idx = :projectIdx
			GROUP BY it.name, it.iconFilename, it.idx
			ORDER BY CASE WHEN count(i.idx) = 0 THEN 1 ELSE 0 END,
			count(i.idx) DESC
			""")
	List<Map<String, Object>> findByTypeByIssueCount(@Param("projectIdx") Integer projectIdx);
	
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
 	
	List<IssueType> findByProjectIdxAndGrade(Integer projectIdx, Integer grade);
	
	List<IssueType> findByProjectIdxAndGradeAndIdxNot(Integer projectIdx, Integer grade, Integer idx);
	
	List<IssueType> findByName(String name);
	
	List<IssueType> findByProjectIdx(Integer projectIdx);
	
}
