package com.mysite.jira.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.repository.IssuePriorityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssuePriorityService {

	private final IssuePriorityRepository issuePriorityRepository;
	
	public Optional<IssuePriority> getByName(String name){
		return issuePriorityRepository.findByName(name);
	}
}
