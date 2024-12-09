package com.mysite.jira.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.board.GetCurrentStatusDTO;
import com.mysite.jira.dto.board.GetLabelDTO;
import com.mysite.jira.dto.board.GetPriorityDTO;
import com.mysite.jira.dto.board.GetStatusDataDTO;
import com.mysite.jira.dto.board.GetTeamDTO;
import com.mysite.jira.dto.board.LabelListDTO;
import com.mysite.jira.dto.board.ReporterDTO;
import com.mysite.jira.dto.board.StatusListDTO;
import com.mysite.jira.dto.board.UpdateDateDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.ProjectMembers;
import com.mysite.jira.entity.Team;
import com.mysite.jira.service.BoardMainService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class BoardMainAPTIController {
	private final BoardMainService boardMainService;
	
	
	@PostMapping("/get_label_list")
	public List<LabelListDTO> getLabelList(@RequestBody GetLabelDTO labelListDTO){
		System.out.println(labelListDTO.getIdx()[0]);
		List<IssueLabelData> labelList = new ArrayList<>();
		if(labelListDTO.getIdx()[0] == -1) {
			labelList = boardMainService.getLabelData();
		}else {
			labelList = boardMainService.getAlterLabelData(labelListDTO.getIdx());
		}
		List<LabelListDTO> alterLabelList = new ArrayList<>();
		for(int i = 0; i < labelList.size(); i++) {
			LabelListDTO dto = LabelListDTO.builder().name(labelList.get(i).getIssueLabel().getName()).build();
			alterLabelList.add(dto);
		}
		return alterLabelList;
	}
	
	@PostMapping("/get_status_list")
	public List<StatusListDTO> getStatusList(@RequestBody GetCurrentStatusDTO getCurrentStatusDTO){
		List<IssueStatus> statusList = boardMainService.getSortedIssueStatus(getCurrentStatusDTO.getProjectIdx(), getCurrentStatusDTO.getStatusIdx());
		List<StatusListDTO> alterStatusList = new ArrayList<>();
		for(int i = 0; i < statusList.size(); i++) {
			IssueStatus status = statusList.get(i);
			StatusListDTO dto = StatusListDTO.builder()
								.idx(status.getIdx())
								.name(status.getName())
								.status(status.getStatus())
								.build();
			alterStatusList.add(dto);
		}
		return alterStatusList;
	}
	
	@PostMapping("/update_current_status")
	public StatusListDTO updateIssueStatus(@RequestBody GetStatusDataDTO getStatusDTO){
		IssueStatus newStatus =	boardMainService.getOnceIssueStatus(getStatusDTO.getStatusIdx());
		Issue currentIssue = boardMainService.getIssueByIdx(getStatusDTO.getIssueIdx());
		boardMainService.updateStatus(currentIssue, newStatus);
		
		StatusListDTO dto = StatusListDTO.builder()
							.idx(newStatus.getIdx())
							.name(newStatus.getName())
							.status(newStatus.getStatus())
							.build();
		return dto;
	}
	
	@PostMapping("/update_date")
	public void updateStartDate(@RequestBody UpdateDateDTO updateDateDTO) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Issue issue = boardMainService.getIssueByIdx(updateDateDTO.getIssueIdx());
		LocalDateTime date = LocalDateTime.parse(updateDateDTO.getDate() + " 00:00:00", formatter);
		if(updateDateDTO.getType().contains("start")) {
			boardMainService.updateStartDate(issue, date);
		}else {
			boardMainService.updateDeadlineDate(issue, date);
		}
	}

	@PostMapping("/get_priority_list")
	public List<GetPriorityDTO> getPriorityList(@RequestBody GetPriorityDTO getPriorityDTO) {
		List<IssuePriority> arrList = boardMainService.getAlterIssuePriority(getPriorityDTO.getPriorityIdx());
		List<GetPriorityDTO> priorityList = new ArrayList<>();
		for(int i = 0; i < arrList.size(); i++) {
			GetPriorityDTO dto = GetPriorityDTO.builder()
											   .issueIdx(getPriorityDTO.getIssueIdx())
											   .priorityIdx(arrList.get(i).getIdx())
											   .iconFilename(arrList.get(i).getIconFilename())
											   .name(arrList.get(i).getName())
											   .build();
			priorityList.add(dto);
		}
		return priorityList;
	}
	
	@PostMapping("/update_priority")
	public GetPriorityDTO updatePriority(@RequestBody GetPriorityDTO getPriorityDTO) {
		IssuePriority priority = boardMainService.getOnceIssuePriority(getPriorityDTO.getPriorityIdx());
		Issue currentIssue = boardMainService.getIssueByIdx(getPriorityDTO.getIssueIdx());
		boardMainService.updatePriority(currentIssue, priority);
		GetPriorityDTO newPriority = GetPriorityDTO.builder()
											.issueIdx(getPriorityDTO.getIssueIdx())
											.priorityIdx(getPriorityDTO.getPriorityIdx())
											.iconFilename(getPriorityDTO.getIconFilename())
											.name(getPriorityDTO.getName())
											.build();
		
		return newPriority;
	}
	
	@PostMapping("/get_team_list")
	public List<GetTeamDTO> getTeamList(@RequestBody GetTeamDTO getTeamDTO){
		List<Team> arrList = boardMainService.getJiraTeamList(getTeamDTO.getJiraIdx());
		List<GetTeamDTO> teamList = new ArrayList<>();
		for(int i = 0; i < arrList.size(); i++) {
			GetTeamDTO dto = GetTeamDTO.builder()
										.teamIdx(arrList.get(i).getIdx())
										.jiraIdx(arrList.get(i).getJira().getIdx())
										.name(arrList.get(i).getName())
										.iconFilename(arrList.get(i).getAccount().getIconFilename())
										.issueIdx(arrList.get(i).getAccount().getIdx())
										.build();
			teamList.add(dto);
		}
		
		return teamList;
	}
	
	@PostMapping("/update_team")
	public GetTeamDTO updateTeam(@RequestBody GetTeamDTO getTeamDTO) {
		Team team = boardMainService.getOnceTeam(getTeamDTO.getTeamIdx());
		Issue currentIssue = boardMainService.getIssueByIdx(getTeamDTO.getIssueIdx());
		boardMainService.updateTeam(currentIssue, team);
		GetTeamDTO newTeam = GetTeamDTO.builder()
										.teamIdx(team.getIdx())
										.name(team.getName())
										.iconFilename(team.getAccount().getIconFilename())
										.build();
		return newTeam;
	}
	
	@PostMapping("/get_reporter_list")
	public List<ReporterDTO> getReporterList(@RequestBody ReporterDTO reporterDTO){
		List<ProjectMembers> arrList = boardMainService.getReporterList(reporterDTO.getProjectIdx(), reporterDTO.getReporterIdx());
		List<ReporterDTO> reporterList = new ArrayList<>();
		for(int i = 0; i < arrList.size(); i++) {
			ReporterDTO dto = ReporterDTO.builder()
										.projectIdx(reporterDTO.getProjectIdx())
										.issueIdx(reporterDTO.getIssueIdx())
										.reporterIdx(arrList.get(i).getAccount().getIdx())
										.name(arrList.get(i).getAccount().getName())
										.iconFilename(arrList.get(i).getAccount().getIconFilename())
										.build();
			reporterList.add(dto);
		}
		return reporterList;
	}
	
	@PostMapping("/update_reporter")
	public ReporterDTO updateReporter(@RequestBody ReporterDTO reporterDTO) {
		Account account = boardMainService.getAccountById(reporterDTO.getReporterIdx());
		Issue currentIssue = boardMainService.getIssueByIdx(reporterDTO.getIssueIdx());
		boardMainService.updateReporter(currentIssue, account);
		ReporterDTO newReporter = ReporterDTO.builder()
											.projectIdx(currentIssue.getProject().getIdx())
											.issueIdx(currentIssue.getIdx())
											.reporterIdx(account.getIdx())
											.name(account.getName())
											.iconFilename(account.getIconFilename())
											.build();

		return newReporter;
	}

}
