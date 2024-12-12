package com.mysite.jira.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueExtends;
import com.mysite.jira.entity.IssueFile;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.IssueReply;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.ProjectMembers;
import com.mysite.jira.entity.Team;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.LogDataService;
import com.mysite.jira.service.ProjectService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{jiraName}/project")
public class ProjectController {
	@Autowired
	private BoardMainService boardMainService;
	
	private final IssueService issueService;
	
	private final ProjectService projectService;
	
	private final LogDataService logDataService;
	
	private final AccountService accountService;
	
	private final JiraService jiraService;
	
	@GetMapping("/{projectKey}/summation")
	public String summationPage(Model model, HttpServletRequest request, Principal principal, 
								@PathVariable("jiraName") String jiraName,
								@PathVariable("projectKey") String projectKey) {
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		
		Jira jira = jiraService.getByNameJira(jiraName);
		Integer jiraIdx = jira.getIdx();
		
		Project project = projectService.getByJiraIdxAndKeyProject(jiraIdx, projectKey);
		Integer projectIdx = project.getIdx();
		
		model.addAttribute("createIssueCount", issueService.getSevenDayCreateIssueCount(projectIdx));
		model.addAttribute("complementIssueCount", issueService.getSevenDayComplementIssueCount(projectIdx));
		model.addAttribute("updateIssueCount", issueService.getSevenDayUpdateIssueCount(projectIdx));
		// 상태 개요
		model.addAttribute("deadlineIssueCount", issueService.getSevenDayDeadlineIssueCount(projectIdx));
		// 최근 활동
		model.addAttribute("projectLogData", logDataService.getProjectLogData(projectIdx));
		// 우선순위 분석
		model.addAttribute("taskTypeData", issueService.getTaskTypeDTO(projectIdx));
		// 작업 유형
		model.addAttribute("sumTaskTypeData", issueService.getSumTaskTypeDTO(projectIdx));
		// 팀 워크로드
		model.addAttribute("managerCountData", issueService.getManagerIssueCount(projectIdx));
		// 관련 프로젝트
		model.addAttribute("relevantProjectList", projectService.getProjectByJiraIdx(jiraIdx));
		return "project/summation";
	}
	
	@GetMapping("/list")
	public String listPage(Model model, 
						   Principal principal, 
						   HttpServletRequest request, 
						   @RequestParam(value="page", defaultValue = "0") int page) {
		// 현재 로그인 계정
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		// 현재 위치한 지라 페이지
		String uri = request.getRequestURI();
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Integer jiraIdx = jira.getIdx();
		model.addAttribute("projectListIsLike", projectService.getProjectListIsLike(accountIdx, jiraIdx, page));
		
		return "project/project_list";
	}
	
	@GetMapping("/create")
	public String createPage(Model model, Principal principal) {
		model.addAttribute("account", accountService.getAccountByEmail(principal.getName()));
		return "project/project_create";
	}
	
	@GetMapping("/{projectKey}/board_main")
	public String boardMain(Model model) {
		Integer projectIdx = 1;
		model.addAttribute("projectIdx", projectIdx);
		
		// 현재 시간용 변수
		LocalDateTime now = LocalDateTime.now();
		model.addAttribute("currentTime", now);
		
		// 프로젝트 별 상태 개수를 알기 위해 그룹화된 상태 리스트
		List<Object[]> statusList = boardMainService.getIssueStatusByProjectIdx(projectIdx);
		model.addAttribute("statusList", statusList);
		
		// 프로젝트 별 이슈 진행도 순으로 정렬된 상태 리스트
		List<IssueStatus> orderedStatusList = boardMainService.getIssueStatusByProjectIdxOrderByStatusAsc(projectIdx);
		model.addAttribute("orderedStatusList", orderedStatusList);
		
		// 프로젝트 별 이슈 리스트
		List<Issue> issueList = boardMainService.getIssuesByProjectIdx(projectIdx);
		model.addAttribute("issueList", issueList);
		
		// 프로젝트 별 서브 이슈 유형을 제외한 이슈 유형 리스트
		List<IssueType> issueTypeList = boardMainService.getIssueTypesByProjectIdxAndGradeGreaterThan(projectIdx, 1);
		model.addAttribute("issueTypeList", issueTypeList);
		
		// 전체 레이블 리스트
		List<IssueLabelData> labelDataList = boardMainService.getLabelData();
		model.addAttribute("labelDataList", labelDataList);
		
		// 전체 댓글 리스트
		List<IssueReply> replyList = boardMainService.getIssueReply();
		model.addAttribute("replyList", replyList);
		
		// 프로젝트 별 상속 관계 리스트
		List<IssueExtends> extendsList = boardMainService.getIssueExtendsByProjectIdx(projectIdx);
		model.addAttribute("extendsList", extendsList);
		
		// 높은 순으로 정렬된 이슈 중요도 리스트
		List<IssuePriority> orderedPriorityList = boardMainService.getIssuePriority();
		model.addAttribute("orderedPriorityList", orderedPriorityList);
		
		// 팀 리스트
		List<Team> teamList = boardMainService.getTeamList();
		model.addAttribute("teamList", teamList);
		
		// 프로젝트 별 참여자 리스트
		List<ProjectMembers> prjMemberList = boardMainService.getPrjMembers(projectIdx);
		model.addAttribute("prjMemberList", prjMemberList);
		
		
		return "project/board_main";
	}
	
	@GetMapping("/{projectKey}/setting_issue_type")
	public String settingIssueType() {
		return "project/setting_issue_type";
	}
	
	@GetMapping("/{projectKey}/attached_files")
	public String attachedFiles(Model model) {
		// 모든 첨부파일 리스트
		List<IssueFile> fileList = boardMainService.getFiles();
		model.addAttribute("fileList", fileList);
		
		return "project/attached_files";
	}
	
	@GetMapping("/{projectKey}/chart")
	public String chart() {
		return "project/chart";
	}
	
	@GetMapping("/profile")
	public String profile(Model model) {
		List<Issue> issueList = issueService.getIssuesByJiraIdx(1);
		model.addAttribute("issue", issueList);
		
		List<Project> projectList = projectService.getProjectByJiraIdx(1);
		model.addAttribute("projectList", projectList);
		return "account/profile.html";
	}

}
