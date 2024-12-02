package com.mysite.jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueExtends;

public interface IssueExtendsRepository extends JpaRepository<IssueExtends, Integer>{
	List<IssueExtends> findByProjectIdxAndParentIdx(Integer projectIdx, Integer parentIdx);
}
