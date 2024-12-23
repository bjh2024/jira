package com.mysite.jira.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.board.CreateIssueDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ToastAPIController {
	
	private final AccountService accountService;
	private final ProjectService projectService;
	
	@MessageMapping("/toast/{jiraName}")
	@SendTo("/topic/toast/{jiraName}")
	public String issueToastMessage(@RequestBody CreateIssueDTO createIssueDTO) {
		String result = "";
		
		Account account = accountService.getAccountByIdx(createIssueDTO.getReporterIdx());
		Project project = projectService.getByIdx(createIssueDTO.getProjectIdx()).get();
		result = account.getName()+"님이 "+ project.getName() + "에 이슈("+createIssueDTO.getIssueName()+")를 생성하셨습니다";
		System.out.println(result);
		return result;
	}
	
}
