package com.mysite.jira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Team;
import com.mysite.jira.repository.TeamRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final TeamRepository teamRepository;
	
	public List<Team> getByJiraIdx(Integer idx){
		return teamRepository.findByJiraIdx(idx);
	}
}
