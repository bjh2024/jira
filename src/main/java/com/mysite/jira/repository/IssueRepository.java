package com.mysite.jira.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mysite.jira.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
	
	Optional<Issue> findById(Integer idx);
	
	List<Issue> findByKey(String key);
	
	List<Issue> findIssuesByProjectIdx(Integer idx);
	
	List<Issue> findIssuesByProjectIdxAndIssueTypeGradeGreaterThanOrderByDivOrder(Integer idx, Integer grade);

	// kdw
	List<Issue> findByIssueClickedList_AccountIdxAndJiraIdxAndCreateDateBetweenOrderByIssueClickedList_ClickedDateDesc(
																											Integer accountIdx, 
																											Integer jiraIdx, 
																											LocalDateTime startDate, 
																											LocalDateTime endDate);
	// kdw
	List<Issue> findByJiraIdxAndNameLike(Integer jiraIdx, String searchText);
	
	// kdw
	List<Issue> findByJiraIdx(Integer jiraIdx);

	@Query("""
			SELECT DISTINCT a.idx, a.name
			FROM Issue i left JOIN Account a
			on i.manager.idx = a.idx
			where i.jira.idx = :jiraIdx
			""")
	List<Object[]> findManagerIdxAndNameByJiraIdx(@Param("jiraIdx") Integer jiraIdx);

	@Query("""
			SELECT distinct nvl(a.idx,0), nvl(a.name,'할당되지 않음'), nvl(a.iconFilename, 'default_icon_file.png')
			FROM Issue i left join Account a
			on i.manager.idx = a.idx
			where i.jira.idx = :jiraIdx
			""")
	List<Object[]> findByManagerIdxNullIn(@Param("jiraIdx") Integer jiraIdx);

	List<Issue> findByManagerNameIn(String[] name);

	List<Issue> findByReporterNameIn(String[] name);

	List<Issue> findByIssuePriorityNameIn(String[] name);

	List<Issue> findByManagerIsNull();

	List<Issue> findByNameLike(String text);

	List<Issue> findByIssueStatus_Status(Integer number);

	List<Issue> findByIssueStatus_StatusNot(Integer number);

	@Query("""
			SELECT :query
			""")
	List<Issue> findByQuery(@Param("query") String text);

	@Query("SELECT i FROM Issue i WHERE i.editDate >= :startDate")
	List<Issue> findIssuesByStartDate(@Param("startDate") LocalDateTime startDate);

	@Query("SELECT i FROM Issue i WHERE i.editDate <= :lastDate")
	List<Issue> findIssuesByLastDate(@Param("lastDate") LocalDateTime lastDate);
	 
	@Query("SELECT i FROM Issue i WHERE i.createDate >= :startDate")
	List<Issue> findIssuesBycreateStartDate(@Param("startDate") LocalDateTime startDate);

	@Query("SELECT i FROM Issue i WHERE i.createDate <= :lastDate")
	List<Issue> findIssuesBycreateLastDate(@Param("lastDate") LocalDateTime LastDate);

	@Query("SELECT i FROM Issue i WHERE i.finishDate >= :startDate")
	List<Issue> findIssuesByfinishStartDate(@Param("startDate") LocalDateTime startDate);

	@Query("SELECT i FROM Issue i WHERE i.finishDate <= :lastDate")
	List<Issue> findIssuesByfinishLastDate(@Param("lastDate") LocalDateTime LastDate);

	// bjh
	List<Issue> findByIssueTypeNameIn(String[] name);

	// bjh
	List<Issue> findByIssueStatusNameIn(String[] name);

	// kdw
	List<Issue> findByEditDateBetween(LocalDateTime startDate, LocalDateTime endDate);

	// kdw
	List<Issue> findByProjectIdxIn(Integer[] projectIdxArr);

	// kdw
	List<Issue> findByManagerIdxIn(Integer[] managerIdxArr);

	// kdw
	List<Issue> findByReporterIdx(Integer reporterIdx);

	// kdw
	List<Issue> findByIssueStatusStatusIn(Integer[] statusArr);

	// kdw
	@Query("""
			    SELECT i
			    FROM ProjectLogData pld
			    JOIN pld.issue i
			    WHERE pld.createDate IN (
			        SELECT MAX(pld2.createDate)
			        FROM ProjectLogData pld2
			        WHERE pld2.issue.idx = pld.issue.idx
			        GROUP BY pld2.issue.idx
			        HAVING MAX(pld2.createDate) BETWEEN :startDate AND :endDate
			    )
			    AND i.jira.idx = :jiraIdx
			    ORDER BY pld.createDate DESC
			""")
	List<Issue> IssueByJiraIdxAndCreateDateBetweenOrderByCreateDateDesc(@Param("jiraIdx") Integer jiraIdx,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	// kdw 나에게 할당(my_work)
	Integer countByJiraIdxAndManagerIdxAndIssueStatus_StatusIn(Integer jiraIdx, Integer managerIdx,
			Integer[] statusArr);

	// kdw 나에게 할당(my_work)
	List<Issue> findByJiraIdxAndManagerIdxAndIssueStatus_StatusInOrderByIssueStatus_NameDesc(Integer jiraIdx,
			Integer managerIdx, Integer[] statusArr);

	// kdw 프로젝트의 기간당 이슈 개수(완료)
	Integer countByProjectIdxAndIssueStatus_statusAndFinishDateBetween(Integer projectIdx, Integer status,
			LocalDateTime startDate, LocalDateTime endDate);

	// kdw 프로젝트의 기간당 이슈 개수(업데이트)
	Integer countByProjectIdxAndEditDateBetween(Integer projectIdx, LocalDateTime startDate, LocalDateTime endDate);

	// kdw 프로젝트의 기간당 이슈 개수(만듦)
	Integer countByProjectIdxAndCreateDateBetween(Integer projectIdx, LocalDateTime startDate, LocalDateTime endDate);

	// kdw 프로젝트의 기간당 이슈 개수(기한 초과)
	Integer countByProjectIdxAndDeadlineDateBetween(Integer projectIdx, LocalDateTime startDate, LocalDateTime endDate);

	// kdw 우선순위 분석 차트
	@Query("""
			SELECT  ip.name as name,
			        COUNT(i.name) as count
			FROM    IssuePriority ip
			LEFT JOIN    Issue i
			ON  ip.idx = i.issuePriority.idx AND i.project.idx = :projectIdx
			GROUP BY ip.name, ip.idx
			ORDER BY ip.idx DESC
			""")
	List<Map<String, Object>> findByPriorityByIssueCount(@Param("projectIdx") Integer projectIdx);
	
	// kdw 이슈 유형 차트
	@Query("""
			SELECT  it.name as name,
				COUNT(i.idx) as count
			FROM    IssueType it
			LEFT JOIN    Issue i
			ON  it.idx = i.issueType.idx
			WHERE   it.project.idx = :projectIdx
			GROUP BY it.name
			ORDER BY count DESC, name
			""")
	List<Map<String, Object>> findByTypeIssueCount(@Param("projectIdx") Integer projectIdx);
		
	// kdw 보고자 차트
	@Query("""
			SELECT  a.name as name,
			        COUNT(i.idx) as count
			FROM    Issue i
			LEFT JOIN    Account a
			ON  a.idx = i.reporter.idx
			WHERE i.project.idx = :projectIdx
			GROUP BY a.name
			ORDER BY count DESC, name
			""")
	List<Map<String, Object>> findByReporterIssueCount(@Param("projectIdx") Integer projectIdx);
	
	// kdw 담당자 차트
	@Query("""
			SELECT  NVL(a.name, '할당되지 않음') as name,
			        (SELECT COUNT(si.idx) FROM Issue si WHERE NVL(i.manager.idx, 0) = NVL(si.manager.idx, 0) AND si.project.idx = :projectIdx) as count
			FROM    Issue i
			LEFT JOIN    Account a
			ON  a.idx = i.manager.idx
			GROUP BY a.name, i.manager.idx
			ORDER BY count DESC, i.manager.idx
			""")
	List<Map<String, Object>> findByManagerIssueCount(@Param("projectIdx") Integer projectIdx);
	
	// kdw 이슈 상태 차트
	@Query("""
			SELECT  ist.name as name,
					COUNT(i.idx) as count
			FROM    IssueStatus ist
			LEFT JOIN    Issue i
			ON  ist.idx = i.issueStatus.idx
			WHERE   ist.project.idx = :projectIdx
			GROUP BY ist.name
			ORDER BY count DESC, name
			""")
	List<Map<String, Object>> findByStatusByIssueCount(@Param("projectIdx") Integer projectIdx);
	
	// kdw 레이블 차트
	@Query("""
			SELECT  il.name as name,
			        COUNT(i.idx) as count
			FROM    Issue i
			LEFT JOIN    IssueLabelData ild
			ON  i.idx = ild.issue.idx
			LEFT JOIN    IssueLabel il
			ON  il.idx = ild.issueLabel.idx
			WHERE i.project.idx = :projectIdx
			GROUP BY il.name
			ORDER BY count DESC, name
			""")
	List<Map<String, Object>> findByLabelByIssueCount(@Param("projectIdx") Integer projectIdx);
	
	// kdw 만듦 대비 해결됨 차트(생성일에 따른 이슈 개수)
	@Query("""
			SELECT  TO_CHAR(createDate, 'YYYY-MM-DD') as issueDate,
			        count(idx) as count
			FROM    Issue
			WHERE createDate BETWEEN :startDate and sysdate
			AND project.idx = :projectIdx
			GROUP BY TO_CHAR(createDate, 'YYYY-MM-DD')
			""")
	List<Map<String, Object>> findByIssueCreateCountBetweenCreateDate(@Param("projectIdx") Integer projectIdx,
			            									    	  @Param("startDate") LocalDateTime startDate);
	
	// kdw 만듦 대비 해결됨 차트(생성일에 따른 이슈 개수)
	@Query("""
			SELECT  TO_CHAR(finishDate, 'YYYY-MM-DD') as issueDate,
			        count(idx) as count
			FROM    Issue
			WHERE finishDate BETWEEN :startDate and sysdate
			AND project.idx = :projectIdx
			GROUP BY TO_CHAR(finishDate, 'YYYY-MM-DD')
			""")
	List<Map<String, Object>> findByIssueCreateCountBetweenFinishDate(@Param("projectIdx") Integer projectIdx,
			            									    	  @Param("startDate") LocalDateTime startDate);
	
	// kdw 최근에 만듦 차트(미완료에 따른 이슈 개수)
	@Query("""
			SELECT  TO_CHAR(createDate, 'YYYY-MM-DD') as issueDate,
			        count(idx) as count
			FROM    Issue
			WHERE createDate BETWEEN :startDate and sysdate
			AND project.idx = :projectIdx
			AND finishDate IS NULL
			GROUP BY TO_CHAR(createDate, 'YYYY-MM-DD')
			""")
	List<Map<String, Object>> findByIssueNotCompleteCountBetweenCreateDate(@Param("projectIdx") Integer projectIdx,
																	       @Param("startDate") LocalDateTime startDate);
	
	// kdw 최근에 만듦 차트(완료에 따른 이슈 개수)
	@Query("""
			SELECT  TO_CHAR(createDate, 'YYYY-MM-DD') as issueDate,
			        count(idx) as count
			FROM    Issue
			WHERE createDate BETWEEN :startDate and sysdate
			AND project.idx = :projectIdx
			AND finishDate IS NOT NULL
			GROUP BY TO_CHAR(createDate, 'YYYY-MM-DD')
			""")
	List<Map<String, Object>> findByIssueCompleteCountBetweenCreateDate(@Param("projectIdx") Integer projectIdx,
																	    @Param("startDate") LocalDateTime startDate);
	List<Issue> findByDivOrderGreaterThanEqualAndIssueStatusIdxOrderByDivOrder(Integer newIdx, Integer statusIdx); 
	
	List<Issue> findByProjectIdxAndIssueStatusIdx(Integer projectIdx, Integer issueIdx);
	
	List<Issue> findByProjectIdxAndIssueTypeIdx(Integer projectIdx, Integer issueTypeIdx);
	
	List<Issue> findByProjectIdxAndIssueTypeGrade(Integer projectIdx, Integer grade);
	
	List<Issue> findByProjectIdxAndIssueTypeGradeAndIdxNot(Integer projectIdx, Integer grade, Integer idx);
	
	Optional<Issue> findByProjectIdxAndName(Integer projectIdx, String name);
}
