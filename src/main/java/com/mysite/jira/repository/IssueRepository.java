package com.mysite.jira.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mysite.jira.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Integer>{
	List<Issue> findIssuesByProjectIdxOrderByDivOrder(Integer idx);

	// kdw
	List<Issue> findByIssueClickedList_AccountIdxAndJiraIdxOrderByIssueClickedList_ClickedDateDesc(
			 @Param("accountIdx") Integer accountIdx, 
			 @Param("jiraIdx") Integer jiraIdx);
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
	List<Issue> findIssuesByLastDate(@Param("lastDate") LocalDateTime LastDate);
	 
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
	//bjh
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
												    	    @Param("startDate") LocalDateTime startDate, 
												    	    @Param("endDate") LocalDateTime endDate);
    
	// kdw 나에게 할당(my_work)
	Integer countByJiraIdxAndManagerIdxAndIssueStatus_StatusIn(Integer jiraIdx, Integer managerIdx, Integer[] statusArr);
	
	// kdw 나에게 할당(my_work)
	List<Issue> findByJiraIdxAndManagerIdxAndIssueStatus_StatusInOrderByIssueStatus_NameDesc(Integer jiraIdx, Integer managerIdx, Integer[] statusArr);
	
	// kdw 프로젝트의 기간당 이슈 개수(완료)
	Integer countByProjectIdxAndIssueStatus_statusAndFinishDateBetween(Integer projectIdx, Integer status, LocalDateTime startDate, LocalDateTime endDate);
	
	// kdw 프로젝트의 기간당 이슈 개수(업데이트)
	Integer countByProjectIdxAndEditDateBetween(Integer projectIdx, LocalDateTime startDate, LocalDateTime endDate);
	
	// kdw 프로젝트의 기간당 이슈 개수(만듦)
	Integer countByProjectIdxAndCreateDateBetween(Integer projectIdx, LocalDateTime startDate, LocalDateTime endDate);
	
	// kdw 프로젝트의 기간당 이슈 개수(기한 초과)
	Integer countByProjectIdxAndDeadlineDateBetween(Integer projectIdx, LocalDateTime startDate, LocalDateTime endDate);

	List<Issue> findByDivOrderGreaterThanEqualAndIssueStatusIdxOrderByDivOrder(Integer newIdx, Integer statusIdx); 
	
	List<Issue> findByProjectIdxAndIssueStatusIdx(Integer projectIdx, Integer issueIdx);
	
}
