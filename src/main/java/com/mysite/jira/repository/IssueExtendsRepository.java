package com.mysite.jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueExtends;

public interface IssueExtendsRepository extends JpaRepository<IssueExtends, Integer>{
	List<IssueExtends> findAllByProjectIdx(Integer projectIdx);
	
	Optional<IssueExtends> findByProjectIdxAndParentIdxAndChildIdx(Integer projectIdx, Integer parentIdx, Integer childIdx);
	
	List<IssueExtends> findByParentIdx(Integer parentIdx);
}
