package com.mysite.jira.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.jira.dto.IssueTypeListDTO;
import com.mysite.jira.dto.ManagerDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.FilterDoneDate;
import com.mysite.jira.entity.FilterIssueCreateDate;
import com.mysite.jira.entity.FilterIssueUpdate;
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
import com.mysite.jira.service.FilterService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.IssueTypeService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.LogDataService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.TeamService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private BoardMainService boardMainService;
	private final IssueService issueService;
	private final ProjectService projectService;
	private final LogDataService logDataService;
	private final AccountService accountService;
	private final JiraService jiraService;
	private final FilterService filterService;
	private final IssueTypeService issueTypeService;
	private final TeamService teamService;
	private final HttpSession session;
	
	@GetMapping("/{projectKey}/summation")
	public String summationPage(Model model, HttpServletRequest request, Principal principal) {
		
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Integer projectIdx = (Integer)session.getAttribute("projectIdx");
		
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
						   @RequestParam(value="page", defaultValue = "0") int page) {
		// 현재 로그인 계정
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		model.addAttribute("projectListIsLike", projectService.getProjectListIsLike(accountIdx, jiraIdx, page));
		
		return "project/project_list";
	}
	
	@GetMapping("/create")
	public String createPage(Model model, Principal principal) {
		Account currentUser = new Account();
		if(principal.getName().split("@").length < 2) {
			Account accKakao = this.accountService.getAccountByKakaoKey(principal.getName());
			Account accNaver = this.accountService.getAccountByNaverKey(principal.getName());
			currentUser = accKakao != null ? accKakao : accNaver;
		}else {
			currentUser = this.accountService.getAccountByEmail(principal.getName());
		}
		model.addAttribute("account", currentUser);
		return "project/project_create";
	}
	
	@GetMapping("/{projectKey}/board_main")
	public String boardMain(HttpServletRequest request, Model model) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Integer projectIdx = (Integer)session.getAttribute("projectIdx");
		
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
		List<IssueLabelData> labelDataList = boardMainService.getLabelData(projectIdx);
		model.addAttribute("labelDataList", labelDataList);
		
		// 전체 댓글 리스트
		List<IssueReply> replyList = boardMainService.getIssueReply(projectIdx);
		model.addAttribute("replyList", replyList);
		
		// 프로젝트 별 상속 관계 리스트
		List<IssueExtends> extendsList = boardMainService.getIssueExtendsByProjectIdx(projectIdx);
		model.addAttribute("extendsList", extendsList);
		
		// 높은 순으로 정렬된 이슈 중요도 리스트
		List<IssuePriority> orderedPriorityList = boardMainService.getIssuePriority();
		model.addAttribute("orderedPriorityList", orderedPriorityList);
		
		// 팀 리스트
		List<Team> teamList = boardMainService.getJiraTeamList(jiraIdx);
		model.addAttribute("teamList", teamList);
		
		// 프로젝트 별 참여자 리스트
		List<ProjectMembers> prjMemberList = boardMainService.getPrjMembers(projectIdx);
		model.addAttribute("prjMemberList", prjMemberList);
		
		
		return "project/board_main";
	}
	
	@GetMapping("/{projectKey}/setting/issue_type/{issueTypeIdx}")
	public String settingIssueType(@PathVariable("issueTypeIdx") Integer issueTypeIdx, Model model) {
		IssueType currentType = boardMainService.getIssueTypeByIdx(issueTypeIdx);
		model.addAttribute("IssueType", currentType);
		
		Integer projectIdx = (Integer)session.getAttribute("projectIdx");
		List<Issue> issueList = projectService.getIssueListByIssueType(projectIdx, issueTypeIdx);
		model.addAttribute("issueList", issueList);
		
		List<IssueType> issueTypeList = boardMainService.getUpdateIssueTypeList(projectIdx, issueTypeIdx);
		model.addAttribute("issueTypeList", issueTypeList);
		return "project/setting/issue_type";
	}
	
	@GetMapping("/{projectKey}/setting/info")
	public String settingProjectInfo() {
		return "project/setting/info";
	}
	
	@GetMapping("/{projectKey}/setting/access")
	public String settingAccess() {
		return "project/setting/access";
	}
	
	@GetMapping("/{projectKey}/attached_files")
	public String attachedFiles(HttpServletRequest request, Model model) {
		Integer projectIdx = (Integer)session.getAttribute("projectIdx");
		
		// 모든 첨부파일 리스트
		List<IssueFile> fileList = boardMainService.getFilesbyProjectIdx(projectIdx);
		model.addAttribute("fileList", fileList);
		
		return "project/attached_files";
	}
	
	@GetMapping("/{projectKey}/chart")
	public String chart(HttpServletRequest request, Model model) {
		Integer projectIdx = (Integer)session.getAttribute("projectIdx");
		List<Issue> issueList = issueService.getByProjectIdx(projectIdx);
		model.addAttribute("issueList", issueList);
		
		return "project/chart";
	}
	@GetMapping("/{projectKey}/project_issue")
	public String projectIssue(@RequestParam(name="filter", required = false) Integer filterIdx ,Model model,
			@PathVariable("projectKey") String projectKey,
			Principal principal, HttpServletRequest request){
		Integer jiraIdx = projectService.getByKey(projectKey).get(0).getJira().getIdx();
		Integer accountIdx = accountService.getAccountByEmail(principal.getName()).getIdx();
		Integer projectIdx = projectService.getByKey(projectKey).get(0).getIdx();
		try {
			
			List<Issue> issue = issueService.getIssuesByJiraIdx(jiraIdx);
			model.addAttribute("issue", issue);
 
			List<Integer> filterProjectIdxArr = filterService.getProjectIdxArrByFilterIdx(filterIdx);
			model.addAttribute("filterProject", filterProjectIdxArr);
			
			List<String> filterIssueTypeNameArr = filterService.getIssueTypeNameArrByFilterIdx(filterIdx);
			model.addAttribute("filterIssueTypeNameArr", filterIssueTypeNameArr);
			
			List<String> filterIssueStatusNameArr = filterService.getIssueStatusNameArrByFilterIdx(filterIdx);
			model.addAttribute("filterIssueStatusNameArr", filterIssueStatusNameArr);
			
			List<String> filterIssueManagerNameArr = filterService.getIssueManagerNameArrByFilterIdx(filterIdx);
			model.addAttribute("filterIssueManagerNameArr", filterIssueManagerNameArr);
			
			List<Integer> filterIssuePriorityIdxArr = filterService.getIssuePriorityIdxByFilterIdx(filterIdx);
			model.addAttribute("filterIssuePriorityIdxArr", filterIssuePriorityIdxArr);
			
			List<String> filterReporterNameArr = filterService.getIssueReporterNameArrByFilterIdx(filterIdx);
			model.addAttribute("filterReporterNameArr", filterReporterNameArr);
			
			List<Integer> filterDone = filterService.getIssueStatusIdxByFilterIdx(filterIdx);
			model.addAttribute("filterDone", filterDone);
			
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			FilterIssueUpdate updateDate = filterService.getIssueUpdateDateByFilterIdx(filterIdx);
			model.addAttribute("updateDate", updateDate);
			LocalDateTime updateStartDate = (updateDate != null)? updateDate.getStartDate() : null;
			LocalDateTime updateEndDate = (updateDate != null)? updateDate.getEndDate() : null;
	        String updateStartDate2 = (updateStartDate != null) ? dateTimeFormatter.format(updateStartDate) : null;
	        String updateEndDate2 = (updateEndDate != null) ? dateTimeFormatter.format(updateEndDate) : null;
			model.addAttribute("updateStartDate", updateStartDate2);
			model.addAttribute("updateEndDate", updateEndDate2);
			
			FilterDoneDate doneDate = filterService.getIssueDoneDateByFilterIdx(filterIdx);
			model.addAttribute("doneDate", doneDate);
			LocalDateTime doneStartDate = (doneDate != null)?doneDate.getStartDate(): null;
			LocalDateTime doneEndDate = (doneDate != null)?doneDate.getEndDate(): null;
			String doneStartDate2 = (doneStartDate != null) ? dateTimeFormatter.format(doneStartDate) : null;
			String doneEndDate2 = (doneEndDate != null) ? dateTimeFormatter.format(doneEndDate) : null;
			model.addAttribute("doneStartDate", doneStartDate2);
			model.addAttribute("doneEndDate", doneEndDate2);
			
			FilterIssueCreateDate createDate = filterService.getIssueCreateDateByFilterIdx(filterIdx);
			model.addAttribute("createDate", createDate);
			LocalDateTime createStartDate = (createDate != null)?createDate.getStartDate():null;
			LocalDateTime createEndDate = (createDate != null)?createDate.getEndDate():null;
			String createStartDate2 = (createStartDate != null) ? dateTimeFormatter.format(createStartDate) : null;
			String createEndDate2 = (createEndDate != null) ? dateTimeFormatter.format(createEndDate) : null;
			model.addAttribute("createStartDate", createStartDate2);
			model.addAttribute("createEndDate", createEndDate2);
			
			
// ====================== 필터용 ==================================================================================
//			List<Project> project = projectService.getProjectByJiraIdx(jiraIdx);
//			model.addAttribute("project", project);
			
			List<IssueTypeListDTO> issueType = issueTypeService.getDistinctIssueTypes(jiraIdx);
			model.addAttribute("issueType", issueType);
			
			List<Object[]> issueStatus = projectService.getDistinctStatusAndNameByJiraIdx(jiraIdx);
			model.addAttribute("issueStatus", issueStatus);
			
			List<ManagerDTO> ManagerDTO = issueService.getManagerIdxAndNameByJiraIdx(jiraIdx);
			model.addAttribute("account", ManagerDTO);
			
			List<IssuePriority> issuePriority = issueService.getIssuePriority();
			model.addAttribute("issuePriority", issuePriority);
			
			List<Account> jiraMembers = accountService.getAccountList(jiraIdx);
			model.addAttribute("jiraMembers", jiraMembers);
			
			List<Team> teamList = teamService.getByJiraIdx(jiraIdx);
			model.addAttribute("teamList", teamList);
			
			List<Filter> filterList = filterService.getByJiraIdx_AccountIdx(jiraIdx, accountIdx);
			model.addAttribute("filterList", filterList);
			model.addAttribute("filterIdx", filterIdx);
			
			// 전체 댓글 리스트
			List<IssueReply> replyList = boardMainService.getFilterIssueReply();
			model.addAttribute("replyList", replyList);
// =====================================================================================================
			String uri = request.getRequestURI(); 
			Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
			
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
			List<IssueLabelData> labelDataList = boardMainService.getLabelData(projectIdx);
			model.addAttribute("labelDataList", labelDataList);
			
			// 프로젝트 별 상속 관계 리스트
			List<IssueExtends> extendsList = boardMainService.getIssueExtendsByProjectIdx(projectIdx);
			model.addAttribute("extendsList", extendsList);
			
			// 높은 순으로 정렬된 이슈 중요도 리스트
			List<IssuePriority> orderedPriorityList = boardMainService.getIssuePriority();
			model.addAttribute("orderedPriorityList", orderedPriorityList);
			
			// 프로젝트 별 참여자 리스트
			List<ProjectMembers> prjMemberList = boardMainService.getPrjMembers(projectIdx);
			model.addAttribute("prjMemberList", prjMemberList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "project/project_issue";
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
