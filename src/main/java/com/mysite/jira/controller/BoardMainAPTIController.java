package com.mysite.jira.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.board.GetLabelDTO;
import com.mysite.jira.dto.board.LabelListDTO;
import com.mysite.jira.dto.board.UpdateStatusDTO;
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
