package com.mysite.jira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.repository.JiraMembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraMembersService {

	private final JiraMembersRepository jiraMembersRepository;
	
	public List<JiraMembers> getMembersByJiraIdx(Integer idx){
		
		return jiraMembersRepository.findByJiraIdx(idx); 
	}
}
