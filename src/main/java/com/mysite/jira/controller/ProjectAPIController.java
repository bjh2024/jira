package com.mysite.jira.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.board.IssueTypeDTO;
import com.mysite.jira.dto.dashboard.create.ProjectListDTO;
import com.mysite.jira.dto.project.SearchDTO;
import com.mysite.jira.dto.project.create.ProjectCreateDTO;
import com.mysite.jira.dto.project.create.ProjectDuplicationKeyDTO;
import com.mysite.jira.dto.project.setting.DeleteIssueTypeDTO;
import com.mysite.jira.dto.project.update.RequestUpdateDTO;
import com.mysite.jira.dto.project.update.UpdateProjectNameDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.ProjectService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectAPIController {

	private final JiraService jiraService;
	
	private final ProjectService projectService;
	
	private final AccountService accountService;
	
	private final HttpSession session;
	
	private final BoardMainService boardMainService;
	
	@GetMapping("duplication/name")
	public Integer getDuplicationProjectName(@RequestParam("projectName") String projectName) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		if(projectService.getByJiraIdxAndNameProject(jiraIdx, projectName) == null) {
			return 0;
		}
		return 1;
	}
	
	@GetMapping("duplication/key")
	public ProjectDuplicationKeyDTO getDuplicationProjectKey(@RequestParam("keyName") String keyName,
															 Principal principal) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Project project = projectService.getByJiraIdxAndKeyProject(jiraIdx, keyName);
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
		String name = projectCreateDTO.getName();
		String key = projectCreateDTO.getKey();
		Account account = new Account();
		if(principal.getName().split("@").length < 2) {
			account = this.accountService.getAccountByKakaoKey(principal.getName());
		}else {
			account = this.accountService.getAccountByEmail(principal.getName());
		}
		
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Jira jira = jiraService.getByIdx(jiraIdx);
		
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
	
	// 프로젝트 삭제
	@PostMapping("delete")
	public boolean projectDelete(@RequestBody Integer projectIdx) {
		projectService.deleteProject(projectIdx);
		return true;
	}
	
	@GetMapping("idx")
	public Integer getProjecIdx(@RequestParam("projectName") String projectName) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Project project = projectService.getByJiraIdxAndNameProject(jiraIdx, projectName);
		
		return project.getIdx();
	}
	
	// 프로젝트 이름으로 search
	@GetMapping("search")
	public List<SearchDTO> projectSearchList(@RequestParam("searchName") String searchName){
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		return projectService.getByJiraIdxAndNameLikeProject(jiraIdx, searchName);
		
	}
	
	// jiraIdx에 해당하는 모든 프로젝트
	@GetMapping("dashboard/list")
	public List<ProjectListDTO> getProjectList(){
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		return projectService.getByJiraIdxProjectListDTO(jiraIdx);
	}
	
	@PostMapping("/update_project_name")
	public void updateProjectName(@RequestBody UpdateProjectNameDTO nameDTO) {
		Project project = projectService.getProjectByIdx(nameDTO.getProjectIdx());
		projectService.updateProjectName(project, nameDTO.getName());
	}
	
	@PostMapping("/update_issueType_name")
	public void updateIssueTypeName(@RequestBody IssueTypeDTO issueTypeDTO) {
		IssueType issueType = boardMainService.getIssueTypeByIdx(issueTypeDTO.getIdx());
		projectService.updateIssueTypeName(issueType, issueTypeDTO.getName());
	}
	
	@PostMapping("/update_issueType_content")
	public void updateIssueTypeContent(@RequestBody IssueTypeDTO issueTypeDTO) {
		IssueType issueType = boardMainService.getIssueTypeByIdx(issueTypeDTO.getIdx());
		projectService.updateIssueTypeContent(issueType, issueTypeDTO.getContent());
	}
	
	@PostMapping("/delete_issueType")
	public void deleteIssueType(@RequestBody DeleteIssueTypeDTO issueTypeDTO) {
		Integer oldTypeIdx = issueTypeDTO.getIssueTypeIdx();
		IssueType newIssueType = boardMainService.getIssueTypeByIdx(issueTypeDTO.getNewTypeIdx());
		List<Issue> issueList = projectService.getIssueListByIssueType(issueTypeDTO.getProjectIdx(), oldTypeIdx);
		System.out.println(oldTypeIdx);
		for(Issue issue : issueList) {
			projectService.updateIssueListType(issue, newIssueType);
		}
		projectService.deleteIssueType(oldTypeIdx);
	}
	
	@PostMapping("/create_issueType")
	public void createIssueType(@RequestBody IssueTypeDTO issueTypeDTO) {
		Project project = projectService.getProjectByIdx(issueTypeDTO.getProjectIdx());
		String name = issueTypeDTO.getName();
		String content = issueTypeDTO.getContent();
		String iconFilename = issueTypeDTO.getIconFilename();
		if(content.equals("")) {
			content = "이 이슈 유형을 사용하는 경우를 사용자들에게 알리기";
		}
		projectService.createIssueType(project, name, content, iconFilename);
	}
}