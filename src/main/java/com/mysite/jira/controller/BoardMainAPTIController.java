package com.mysite.jira.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.board.GetCurrentStatusDTO;
import com.mysite.jira.dto.board.GetLabelDTO;
import com.mysite.jira.dto.board.GetStatusDataDTO;
import com.mysite.jira.dto.board.LabelListDTO;
import com.mysite.jira.dto.board.StatusListDTO;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.service.BoardMainService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class BoardMainAPTIController {
	private final BoardMainService boardMainService;
	
	@PostMapping("/get_label_list")
	public List<LabelListDTO> getLabelList(@RequestBody GetLabelDTO labelListDTO){
		List<IssueLabelData> labelList = boardMainService.getAlterLabelData(labelListDTO.getIdx());
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
	
//	@PatchMapping("/updateStatus")
//	public List<IssueStatus> updateIssueStatus(@RequestBody UpdateStatusDTO updateStatusDTO){
//		Integer projectIdx = updateStatusDTO.getProjectIdx();
//		Integer issueIdx = updateStatusDTO.getIssueIdx();
//		Integer issueStatusIdx = updateStatusDTO.getIssueStatusIdx();
//		
//		Optional<IssueStatus> issueStatus = boardMainService.getOnceIssueStatus(issueStatusIdx);
//		
//		if(issueStatus.isPresent()) {
//			boardMainService.updateIssueStatus(projectIdx, issueIdx, issueStatus.get());
//		}
//		
//		List<IssueStatus> updatedStatusList = boardMainService.getSortedIssueStatus(projectIdx, issueIdx);
//		
//		return updatedStatusList;
//	}

}
