package com.mysite.jira.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.ToastInfoDTO;
import com.mysite.jira.dto.board.CreateIssueDTO;
import com.mysite.jira.dto.board.DeleteIssueDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ToastAPIController {
	
	private final AccountService accountService;
	private final ProjectService projectService;
	private final IssueService issueService;
	
	@MessageMapping("/toast/{jiraIdx}")
	@SendTo("/topic/toast/{jiraIdx}")
	public String issueToastMessage(@RequestBody ToastInfoDTO toastInfoDTO) {
		String result = "";
		System.out.println(toastInfoDTO.getIsCreate());
		try {
		if(toastInfoDTO.getIsCreate() == 1) {
			Account account = accountService.getAccountByIdx(toastInfoDTO.getReporterIdx());
			Project project = projectService.getByIdx(toastInfoDTO.getProjectIdx()).get();
			result = account.getName()+"님이 "+ project.getName() + "에 이슈("+toastInfoDTO.getIssueName()+")를 생성하셨습니다";
			System.out.println(result);
		}else if(toastInfoDTO.getIsCreate() == 0) {
			Project project = projectService.getByIdx(toastInfoDTO.getProjectIdx()).get();
			result = project.getName()+"의 이슈("+toastInfoDTO.getIssueName()+")가 삭제되었습니다.";
				System.out.println(result);
		}
		return result;
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
}
