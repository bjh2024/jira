package com.mysite.jira.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.jira.dto.IssueTypeListDTO;
import com.mysite.jira.dto.ManagerDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.IssueTypeService;
import com.mysite.jira.service.JiraMembersService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{jiraName}/filter")
public class FilterController {

	private final IssueService issueService;
	private final ProjectService projectService;
	private final IssueTypeService issueTypeService;
	private final AccountService accountService;
	private final BoardMainService boardMainService;

	@GetMapping("/filter_issue/{issueKey}")
	public String filterIssue(@PathVariable(name = "issueKey", required = false) String issueKey,Model model) {
		Integer jiraIdx = 1;

		try {
			model.addAttribute("projectKey", issueKey);
			
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
