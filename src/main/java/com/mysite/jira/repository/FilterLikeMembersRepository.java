package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mysite.jira.entity.FilterLikeMembers;

public interface FilterLikeMembersRepository extends JpaRepository<FilterLikeMembers, Integer>{
	// kdw
	List<FilterLikeMembers> findByAccountIdxAndFilter_jiraIdx(@Param("accountIdx") Integer accountIdx, @Param("jiraIdx") Integer jiraIdx);
	// kdw
	FilterLikeMembers findByAccountIdxAndFilterIdx(@Param("accountIdx") Integer accountIdx, @Param("filterIdx") Integer filterIdx);
}
