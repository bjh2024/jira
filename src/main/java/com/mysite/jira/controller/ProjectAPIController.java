package com.mysite.jira.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.dashboard.create.ProjectListDTO;
import com.mysite.jira.dto.project.SearchDTO;
import com.mysite.jira.dto.project.create.ProjectCreateDTO;
import com.mysite.jira.dto.project.create.ProjectDuplicationKeyDTO;
import com.mysite.jira.dto.project.update.RequestUpdateDTO;
import com.mysite.jira.dto.project.update.UpdateProjectNameDTO;
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
											 @RequestParam("uri") String uri) {
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
		String uri = projectCreateDTO.getUri();
		String name = projectCreateDTO.getName();
		String key = projectCreateDTO.getKey();
		Account account = new Account();
		if(principal.getName().split("@").length < 2) {
			account = this.accountService.getAccountByKakaoKey(principal.getName());
		}else {
			account = this.accountService.getAccountByEmail(principal.getName());
		}
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		
		projectService.createProject(name, key ,jira, account);
		
		return true;
	}
	// 프로젝트 수정
	@PostMapping("update")
	public boolean projectupdate(@RequestBody RequestUpdateDTO RequestUpdateDTO) {
		String name = RequestUpdateDTO.getProjectName();
		String key = RequestUpdateDTO.getProjectKey();
		Integer projectIdx = RequestUpdateDTO.getProjectIdx();
		
		Account account = accountService.getAccountByIdx(RequestUpdateDTO.getLeaderIdx());
		projectService.updateProject(projectIdx, name, key ,account);
		
		return true;
	}
	
	@GetMapping("idx")
	public Integer getProjecIdx(@RequestParam("projectName") String projectName,
								@RequestParam("uri") String uri) {
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Project project = projectService.getByJiraIdxAndNameProject(jira.getIdx(), projectName);
		
		return project.getIdx();
	}
	
	// 프로젝트 이름으로 search
	@GetMapping("search")
	public List<SearchDTO> projectSearchList(@RequestParam("searchName") String searchName,
										     @RequestParam("uri") String uri){
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		return projectService.getByJiraIdxAndNameLikeProject(jira.getIdx(), searchName);
		
	}
	
	// jiraIdx에 해당하는 모든 프로젝트
	@GetMapping("dashboard/list")
	public List<ProjectListDTO> getProjectList(@RequestParam("uri") String uri){
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		return projectService.getByJiraIdxProjectListDTO(jira.getIdx());
	}
	
	@PostMapping("/update_project_name")
	public void updateProjectName(@RequestBody UpdateProjectNameDTO nameDTO) {
		Project project = projectService.getProjectByIdx(nameDTO.getProjectIdx());
		projectService.updateProjectName(project, nameDTO.getName());
	}
	
}
