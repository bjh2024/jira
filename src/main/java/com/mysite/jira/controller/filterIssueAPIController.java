package com.mysite.jira.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.FilterIssueDetailDTO;
import com.mysite.jira.service.IssueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filter_issue")
public class filterIssueAPIController {
	
	@PostMapping("/issue_detail")
	public List<FilterIssueDetailDTO> getissueKey(@RequestBody FilterIssueDetailDTO filterIssueDetail){
		System.out.println("dsd");
		List<FilterIssueDetailDTO> issueKey = new ArrayList<>();
		FilterIssueDetailDTO filterDTO = FilterIssueDetailDTO.builder()
										.issueKey(filterIssueDetail.getIssueKey())
										.build();
		
		issueKey.add(filterDTO);
				
		return issueKey;
	}
}
