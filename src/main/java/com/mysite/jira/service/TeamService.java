package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.dashboard.create.TeamListDTO;
import com.mysite.jira.entity.Team;
import com.mysite.jira.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
	
	private final TeamRepository teamRepository;
	
	public Team getTeamByIdx(Integer idx) {
		Optional<Team> opTeam = teamRepository.findById(idx);
		Team team = null;
		if(!opTeam.isEmpty()) {
			team = opTeam.get();
		}
		return team;
	}
	
	public List<TeamListDTO> getTeamListByJiraIdx(Integer jiraIdx){
		List<Team> teamList = teamRepository.findByJiraIdx(jiraIdx);
		List<TeamListDTO> result = new ArrayList<>();
		
		for(int i = 0; i < teamList.size(); i++) {
			Integer idx = teamList.get(i).getIdx();
			String name = teamList.get(i).getName();
			TeamListDTO dto = TeamListDTO.builder()
										 .idx(idx)
										 .name(name)
										 .build();
			result.add(dto);
		}
		return result;
	}
}
