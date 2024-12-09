package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.project.create.ProjectCreateDTO;
import com.mysite.jira.dto.project.create.ProjectDuplicationKeyDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectAPIController {

	private final JiraService jiraService;
	
	private final ProjectService projectService;
	
	private final AccountService accountService;
	
	@GetMapping("duplication/name")
	public Integer getDuplicationProjectName(@RequestParam("projectName") String projectName,
											 @RequestParam("uri") String uri,
											 Principal principal) {
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		if(projectService.getByJiraIdxAndNameProject(jira.getIdx(), projectName) == null) {
			return 0;
		}
		return 1;
	}
	
	@GetMapping("duplication/key")
	public ProjectDuplicationKeyDTO getDuplicationProjectKey(@RequestParam("keyName") String keyName,
															 @RequestParam("uri") String uri,
															 Principal principal) {
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Project project = projectService.getByJiraIdxAndKeyProject(jira.getIdx(), keyName);
		Integer count = 0;
		String projectName = "값이 없습니다!";
		if(project != null) {
			count = 1;
			projectName = project.getName();
			
		}
		ProjectDuplicationKeyDTO dto = ProjectDuplicationKeyDTO.builder()
														       .count(count)
														       .projectName(projectName)
														       .build();
		return dto;
	}
	// 프로젝트 생성
	@PostMapping("create")
	public boolean projectCreate(@RequestBody ProjectCreateDTO projectCreateDTO, Principal principal) {
		System.out.println(projectCreateDTO);
		String uri = projectCreateDTO.getUri();
		String name = projectCreateDTO.getName();
		String key = projectCreateDTO.getKey();
		Account account = accountService.getAccountByEmail(principal.getName());
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		
		projectService.createProject(name, key ,jira, account);
		
		return true;
	}
}
