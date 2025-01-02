package com.mysite.jira.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.AddJiraMemberDTO;
import com.mysite.jira.dto.NewPwDTO;
import com.mysite.jira.dto.account.LoginAddJiraDTO;
import com.mysite.jira.dto.project.SearchDTO;
import com.mysite.jira.email.JiraInviteEmailClient;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.JiraMembersService;
import com.mysite.jira.service.JiraService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountAPIController {

	private final AccountService accountService;

	private final JiraService jiraService;
	
	private final JiraMembersService jiraMembersService;
	
	private final JiraInviteEmailClient jiraInviteEmailClient; 

	private final HttpSession session;
	
	// 유저 이름으로 project에 포함된 유저 search
	@GetMapping("project/members")
	public List<SearchDTO> projectSearchList(@RequestParam("searchName") String searchName){
		Integer projectIdx = (Integer)session.getAttribute("projectIdx");
		// key를 accountIdx로 사용
		return accountService.getProjectMemberList(projectIdx, searchName);
	}
	
	@GetMapping("isExist")
	public Integer getIsExistInJira(@RequestParam("email") String email) {
		if(accountService.getAccountByEmail(email) != null) {
			return 0;
		}
		return 1;
	}
	
	@GetMapping("duplication/jira_member")
	public Integer getDuplicationJiraMembersName(@RequestParam("email") String email) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		if(jiraMembersService.getByJiraIdxAndEmailJiraMember(jiraIdx, email) == null) {
			return 0;
		}
		return 1;
	}
	
	@GetMapping("add/jira_member")
	public void addJiraMember(@RequestBody AddJiraMemberDTO addJiraMemberDTO) {
		String email = addJiraMemberDTO.getEmail();
		Account account = accountService.getAccountByEmail(email);
		
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Jira jira = jiraService.getByIdx(jiraIdx);
		
		jiraMembersService.addJiraMember(account, jira);
	}
	
	@PostMapping("add/jira_member/send_email")
	public Integer addJiraMemberSendEmail(@RequestBody String jiraUserEmail) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		jiraInviteEmailClient.sendEmail(jiraUserEmail.replaceAll("\"", ""), jiraIdx);
		return 1;
	}
	
	@PostMapping("jira/add/login")
	public boolean addJiraMemberLogin(@RequestBody LoginAddJiraDTO LoginDTO) {
		String email = LoginDTO.getEmail();
		String password = LoginDTO.getPassword();
		Integer jiraIdx = LoginDTO.getJiraIdx();
		return accountService.getIsLoginJiraAdd(email, password, jiraIdx, session);
	}
	
	@PostMapping("/password")
	public boolean updatePassword(@RequestBody NewPwDTO newPw) {
		if(accountService.changePassword(newPw)) {
			return true;
		}else {
			return false;
		}
	}
}
