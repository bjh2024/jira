package com.mysite.jira.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.jira.dto.IssueTypeListDTO;
import com.mysite.jira.dto.ManagerDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.FilterProject;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.Team;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.FilterService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.IssueTypeService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.TeamService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{jiraName}/filter")
public class FilterController {

	private final IssueService issueService;
	private final ProjectService projectService;
	private final IssueTypeService issueTypeService;
	private final AccountService accountService;
	private final FilterService filterService;
	private final BoardMainService boradMainService;
	private final TeamService teamService;
	private final JiraService jiraService;

//	@GetMapping("filter_issue")
//	public String filterIssueMain(Model model, HttpServletRequest request) {
//	    // Jira에서 문제 목록을 가져옴
//	    List<Issue> issueList = issueService.getIssuesByJiraIdx(1);
//	    // 첫 번째 이슈의 Key를 사용하여 리다이렉트
//	    String issueKey = issueList.get(0).getKey();
//
//	    String requestUrl = request.getRequestURL().toString();
//	    String queryString = request.getQueryString();
//
//	    // 쿼리 스트링이 있으면, issueKey를 그 사이에 추가
//	    if (queryString != null) {
//	        // URL의 끝에 '?'가 포함되어 있는지 확인하고 issueKey를 추가
//	        return "redirect:" + requestUrl + "/" + issueKey + "?" + queryString;
//	    }
//
//	    // 쿼리 스트링이 없으면, 그냥 issueKey만 추가
//	    return "redirect:" + requestUrl + "/" + issueKey;
//	}
	@GetMapping("filter_issue")
	public String filterIssue(@RequestParam(name="filter", required = false) Integer filterIdx ,Model model,
			@PathVariable("jiraName") String jiraName) {
		Integer jiraIdx = jiraService.getByNameJira(jiraName).getIdx();
			System.out.println(filterIdx);
		try {
			
			List<Issue> issue = issueService.getIssuesByJiraIdx(jiraIdx);
			
			List<Issue> projectFilterIssue = issueService.getIssueByProjectIdxAndFilterIdx(filterIdx);
			if(projectFilterIssue.size() > 0) {
				issue.retainAll(projectFilterIssue);
			}
			List<Issue> issueTypeFilterIssue = issueService.getIssueByIssueTypeAndFilterIdx(filterIdx);
			if(issueTypeFilterIssue.size() > 0) {
				issue.retainAll(issueTypeFilterIssue);
			}
			model.addAttribute("issue", issue);
			
			List<Integer> filterProjectIdxArr = filterService.getByFilterIdx(filterIdx);
			model.addAttribute("filterProject", filterProjectIdxArr);
			
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
		return "filter/filter_issue";
	}

	@GetMapping("/every_filter")
	public String everyfilter() {
		return "filter/every_filter";
	}

	@GetMapping("/filter_issue_table")
	public String filterIssueTable(@RequestParam(value = "projectKey",required = false) String projectKey,Model model) {
		Integer jiraIdx = 1;

		try {
			model.addAttribute("projectKey", projectKey);
			
			List<Issue> issue = issueService.getIssuesByJiraIdx(jiraIdx);
			model.addAttribute("issue", issue);
			
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
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "filter/filter_issue_table.html";
	}
}
