package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.IssueLikeMembers;

public interface IssueLikeMembersRepository extends JpaRepository<IssueLikeMembers, Integer>{

}
