package com.mysite.jira.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.jira.dto.IssueTypeListDTO;
import com.mysite.jira.dto.ManagerDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.FilterAuth;
import com.mysite.jira.entity.FilterDoneDate;
import com.mysite.jira.entity.FilterIssueCreateDate;
import com.mysite.jira.entity.FilterIssueUpdate;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.IssueReply;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.Team;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.FilterService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.IssueTypeService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.TeamService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {

	private final IssueService issueService;
	private final ProjectService projectService;
	private final IssueTypeService issueTypeService;
	private final AccountService accountService;
	private final FilterService filterService;
	private final BoardMainService boardMainService;
	private final TeamService teamService;
	private final HttpSession session;
	
// controller에서 걸러준 이슈들을 model객체로 넘겨줌
	@GetMapping("filter_issue")
	public String filterIssue(@RequestParam(name="filter", required = false) Integer filterIdx ,Model model,
			Principal principal){
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Integer accountIdx = accountService.getAccountByEmail(principal.getName()).getIdx();
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
			List<Project> project = projectService.getProjectByJiraIdx(jiraIdx);
			model.addAttribute("project", project);
			
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
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "filter/filter_issue";
	}

	@GetMapping("/every_filter")
	public String everyfilter(@RequestParam(name="filter", required = false) Integer filterIdx ,Model model, Principal principal) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Integer accountIdx = accountService.getAccountByEmail(principal.getName()).getIdx();
		
		List<Filter> filterList = filterService.getByJiraIdx_AccountIdx(jiraIdx, accountIdx);
		model.addAttribute("filterList", filterList);
		
		List<FilterAuth> filterAuth = filterService.getFilterAuthAll();
		model.addAttribute("filterAuth", filterAuth);
		
		List<Team> teamList = teamService.getByJiraIdx(jiraIdx);
		model.addAttribute("teamList", teamList);
		
		return "filter/every_filter";
	}

	@GetMapping("/filter_issue_table")
	public String filterIssueTable
	(@RequestParam(name="filter", required = false) Integer filterIdx ,Model model) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
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
			List<Project> project = projectService.getProjectByJiraIdx(jiraIdx);
			model.addAttribute("project", project);
			
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
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "filter/filter_issue_table.html";
	}
}
