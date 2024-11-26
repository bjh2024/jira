package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.IssueType;

public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {

	// distinct한 name과 iconFilename을 가져오는 쿼리
	@Query("SELECT DISTINCT it.name, it.iconFilename " + "FROM Issue i JOIN i.issueType it "
			+ "WHERE i.issueType.idx = it.idx " + "AND i.jira.idx = :idx")
	List<Object[]> findDistinctNameAndIconFilename(@Param("idx") Integer idx);

	List<IssueType> findByProjectIdxAndGradeGreaterThan(Integer projectIdx, Integer grade);
}
