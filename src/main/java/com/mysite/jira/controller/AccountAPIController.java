package com.mysite.jira.controller;

import java.util.List;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.AddJiraMemberDTO;
import com.mysite.jira.dto.dashboard.create.AccountListDTO;
import com.mysite.jira.dto.project.SearchDTO;
import com.mysite.jira.email.JiraInviteEmailClient;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.JiraMembersService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountAPIController {

	private final AccountService accountService;

	private final ProjectService projectService;
	
	private final JiraService jiraService;
	
	private final JiraMembersService jiraMembersService;
	
	private final JiraInviteEmailClient jiraInviteEmailClient; 

	// 유저 이름으로 project에 포함된 유저 search
	@GetMapping("project/members")
	public List<SearchDTO> projectSearchList(@RequestParam("searchName") String searchName,
											 @RequestParam("uri") String uri){
		String jiraName = uri.split("/")[1];
		String projectKey = uri.split("/")[3];
		Jira jira = jiraService.getByNameJira(jiraName);
		Project project = projectService.getByJiraIdxAndKeyProject(jira.getIdx(), projectKey);
		// key를 accountIdx로 사용
		return accountService.getProjectMemberList(project.getIdx(), searchName);
	}
	
	@GetMapping("dashboard/list")
	public List<AccountListDTO> getAccountList(@RequestParam("uri") String uri){
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		return accountService.getAccountListDashboard(jira.getIdx());
	}
	
	@GetMapping("duplication/jira_member")
	public Integer getDuplicationJiraMembersName(@RequestParam("email") String email,
												 @RequestParam("uri") String uri) {
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		 
		if(jiraMembersService.getByJiraIdxAndEmailJiraMember(jira.getIdx(), email) == null) {
			return 0;
		}
		return 1;
	}
	
	@GetMapping("add/jira_member")
	public void addJiraMember(@RequestBody AddJiraMemberDTO addJiraMemberDTO) {
		String email = addJiraMemberDTO.getEmail();
		String uri = addJiraMemberDTO.getUri();
		
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Account account = accountService.getAccountByEmail(email);
		
		jiraMembersService.addJiraMember(account, jira);
	}
	
	@PostMapping("add/jira_member/send_email")
	public Integer addJiraMemberSendEmail(@RequestBody String jiraUserEmail) {
		System.out.println(jiraUserEmail.replaceAll("\"", ""));
		jiraInviteEmailClient.sendEmail(jiraUserEmail.replaceAll("\"", ""));
		return 1;
	}
}
