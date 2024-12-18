package com.mysite.jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueLikeMembers;

public interface IssueLikeMembersRepository extends JpaRepository<IssueLikeMembers, Integer>{
	List<IssueLikeMembers> findByIssueIdx(Integer idx);
	
	Optional<IssueLikeMembers> findByAccountIdxAndIssueIdx(Integer userIdx, Integer issueIdx);
}
