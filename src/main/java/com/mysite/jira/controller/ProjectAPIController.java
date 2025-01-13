package com.mysite.jira.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.ProjectLikeDTO;
import com.mysite.jira.dto.board.IssueTypeDTO;
import com.mysite.jira.dto.project.SearchDTO;
import com.mysite.jira.dto.project.create.ProjectCreateDTO;
import com.mysite.jira.dto.project.create.ProjectMemberCreateDTO;
import com.mysite.jira.dto.project.setting.DeleteIssueTypeDTO;
import com.mysite.jira.dto.project.update.RequestUpdateDTO;
import com.mysite.jira.dto.project.update.UpdateProjectNameDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.ProjectLikeMembers;
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
	public String getDuplicationProjectKey(@RequestParam("keyName") String keyName,
															 Principal principal) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Project project = projectService.getByJiraIdxAndKeyProject(jiraIdx, keyName);
		String projectName = "";
		if(project != null) {
			projectName = project.getName();
			
		}
		return projectName;
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
		List<Issue> issueList = projectService.getIssueListByIssueType(issueTypeDTO.getProjectIdx(), oldTypeIdx);
		if(issueTypeDTO.getNewTypeIdx() != null) {
			IssueType newIssueType = boardMainService.getIssueTypeByIdx(issueTypeDTO.getNewTypeIdx());
			for(Issue issue : issueList) {
				projectService.updateIssueListType(issue, newIssueType);
			}
		}
		projectService.deleteIssueType(oldTypeIdx);
	}
	
	@PostMapping("/verification_issueType")
	public boolean verificationIssueType(@RequestBody IssueTypeDTO issueTypeDTO) {
		Integer projectIdx = issueTypeDTO.getProjectIdx();
		String name = issueTypeDTO.getName();
		return projectService.verificationIssueType(projectIdx, name);
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
	
	@PostMapping("/create_project_member")
	public void createProjectMember(@RequestBody ProjectMemberCreateDTO projectDTO) {
		Account user = boardMainService.getAccountById(projectDTO.getUserIdx());
		Project project = projectService.getProjectByIdx(projectDTO.getProjectIdx());
		projectService.createProjectMember(user, project);
	}
	
	@PostMapping("/is_project_liked")
	public Integer isProjectLiked(@RequestBody ProjectLikeDTO projectLikeDTO) {
		Integer projectIdx = projectLikeDTO.getProjectIdx();
		Integer userIdx = projectLikeDTO.getUserIdx();
		return projectService.isProjectLiked(userIdx, projectIdx);
	}
	
	@PostMapping("/project_like_control")
	public boolean projectLikeControl(@RequestBody ProjectLikeDTO projectLikeDTO) {
		Project project = projectService.getProjectByIdx(projectLikeDTO.getProjectIdx());
		Account user = accountService.getAccountByIdx(projectLikeDTO.getUserIdx());
		Integer isLiked = projectLikeDTO.getIsLiked();
		if(isLiked == 0) {
			projectService.createProjectLikeData(project, user);
			return true;
		}
		
		Integer likeIdx = projectService.isProjectLiked(projectLikeDTO.getUserIdx(), projectLikeDTO.getProjectIdx());
		projectService.deleteProjectLikeData(likeIdx);
		return false;
	}
}