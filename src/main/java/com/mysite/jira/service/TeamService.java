package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.jira.dto.dashboard.create.TeamListDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Team;
import com.mysite.jira.entity.TeamMembers;
import com.mysite.jira.repository.TeamMembersRepository;
import com.mysite.jira.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
	
	private final TeamRepository teamRepository;
	
	private final TeamMembersRepository teamMembersRepository;
	
	public List<Team> getByJiraIdx(Integer jiraIdx){
		return teamRepository.findByJiraIdx(jiraIdx);
	}
	
	public Team getTeamByIdx(Integer idx) {
		Optional<Team> opTeam = teamRepository.findById(idx);
		Team team = null;
		if(!opTeam.isEmpty()) {
			team = opTeam.get();
		}
		return team;
	}
	
	public List<Team> getTeamListByJiraIdx(Integer jiraIdx){
		return teamRepository.findByJiraIdx(jiraIdx);
	}
	
	// 대시보드
	public List<TeamListDTO> getTeamListByJiraIdxDashboard(Integer jiraIdx){
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
	
	public Team getByJiraIdxAndNameTeam(Integer jiraIdx, String teamName) {
		return teamRepository.findByJiraIdxAndName(jiraIdx, teamName);
	}
	
	@Transactional
	public boolean createTeam(Account account, Jira jira, String teamName) {
		System.out.println(teamName);
		Team team = Team.builder()
						.name(teamName)
						.jira(jira)
						.account(account)
						.build();
		teamRepository.save(team);
		
		// 팀 멤버 추가
		TeamMembers teamMembers = TeamMembers.builder()
											 .team(team)
											 .account(account)
											 .build();
		teamMembersRepository.save(teamMembers);
		
		return true;
	}
	
}
