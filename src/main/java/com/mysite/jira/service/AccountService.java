package com.mysite.jira.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mysite.jira.dto.NewPwDTO;
import com.mysite.jira.dto.dashboard.create.AccountListDTO;
import com.mysite.jira.dto.project.SearchDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.entity.JiraRecentClicked;
import com.mysite.jira.entity.ProjectMembers;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.JiraMembersRepository;
import com.mysite.jira.repository.JiraRecentClickedRepository;
import com.mysite.jira.repository.ProjectMembersRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	
	private final ProjectMembersRepository projectMembersRepository;
	
	private final JiraService jiraService;
	
	private final JiraMembersRepository jiraMembersRepository;
	
	private final JiraRecentClickedRepository jiraRecentClickedRepository;
	
	private final AccountRepository accountRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public Optional<Account> getByEmail(String email) {
		return accountRepository.findByEmail(email);
	}
	
	public Account getByName(String name){
		List<Account> accountList = accountRepository.findByName(name);
		if(accountList.size() > 0) {
		Account result = accountList.get(0);
		return result;
		}
		return null;
	}
	public Account getAccountByIdx(Integer idx) {
		Optional<Account> opAccount = accountRepository.findById(idx);
		Account account = null;
		if(!opAccount.isEmpty()) {
			account = opAccount.get();
		}
		return account;
	}
	
	public List<Account> getByUserName(String name) {
		return accountRepository.findByName(name);
	}
	
	public List<JiraMembers> getByJiraIdx(Integer idx){
		return jiraMembersRepository.findByJiraIdx(idx);
	}
	
	public List<Account> getAccountList(Integer jiraIdx) {
		List<JiraMembers> jiraMembers = jiraMembersRepository.findByJiraIdx(jiraIdx);

		List<Account> result = new ArrayList<>();
		for (int i = 0; i < jiraMembers.size(); i++) {
			result.add(jiraMembers.get(i).getAccount());
		}
		return result;
	}
	
	public List<AccountListDTO> getAccountListDashboard(Integer jiraIdx) {
		List<Account> accountList = this.getAccountList(jiraIdx);
		List<AccountListDTO> result = new ArrayList<>();
		for (int i = 0; i < accountList.size(); i++) {
			Integer idx = accountList.get(i).getIdx();
			String name = accountList.get(i).getName();
			String iconFilename = accountList.get(i).getIconFilename();
			AccountListDTO dto = AccountListDTO.builder()
											   .idx(idx)
										       .name(name)
										       .iconFilename(iconFilename)
										       .build();
			result.add(dto);
		}
		return result;
	}
	
	public Account signup(String username, String email, String pw) {
		String code = "";
		for(int i = 0; i < 4; i++) {
			int tempCode = (int) (Math.random() * 26) + 65;
			code += (char)tempCode;
		}
		Integer iconNum = (int) (Math.random() * 10) + 1;
		Account newUser = Account.builder()
								.name(username)
								.email(email)
								.pw(passwordEncoder.encode(pw))
								.iconFilename("user_icon_file"+iconNum+".png")
								.authCode(code)
								.build();
		this.accountRepository.save(newUser);
		return newUser;
	}
	
	public Account getAccountByEmail(String email) {
		Optional<Account> optAccount = this.accountRepository.findByEmail(email);
		Account account = null;
		if(optAccount.isPresent()) {
			account = optAccount.get();
		}
		return account;
	}
	
	public void updateAccount(String email) {
		Optional<Account> optUser = this.accountRepository.findByEmail(email);
		Account existingAccount = null;
		if(optUser.isPresent()) {
			existingAccount = optUser.get();
		}
		if(existingAccount == null) {
			throw new NoSuchElementException("Account not found");
		}
		jiraService.addJira(existingAccount.getIdx());
		existingAccount.updateAccount(null, null);
		this.accountRepository.save(existingAccount);
	}
	
	// key를 accountIdx로 사용
	public List<SearchDTO> getProjectMemberList(Integer projectIdx, String searchName){
		searchName = searchName + "%";
		if (searchName.equals("%"))
			searchName = "";
		
		List<ProjectMembers> projectMembersList =  projectMembersRepository.findByprojectIdxAndAccount_NameLike(projectIdx, searchName);
		List<SearchDTO> result = new ArrayList<>();
		for(int i = 0; i < projectMembersList.size(); i++) {
			String name = projectMembersList.get(i).getAccount().getName();
			String iconFilename = projectMembersList.get(i).getAccount().getIconFilename();
			String accountIdx = projectMembersList.get(i).getAccount().getIdx().toString();
			SearchDTO dto = SearchDTO.builder()
									 .name(name)
									 .iconFilename(iconFilename)
									 .key(accountIdx)
									 .build();
			result.add(dto);
		}
		return result;
	}
	
	public Account resetCode(Account user) {
		String code = "";
		for(int i = 0; i < 4; i++) {
			int tempCode = (int) (Math.random() * 26) + 65;
			code += (char)tempCode;
		}
		user.updateAccount(code, LocalDateTime.now().plusMinutes(3));
		this.accountRepository.save(user);
		return user;
	}
	
	public Account getAccountByKakaoKey(String key){
		Optional<Account> optAcc = this.accountRepository.findByKakaoSocialKey(key);
		Account account = null;
		if(optAcc.isPresent()) {
			account = optAcc.get();
		}
		return account;
	}
	
	public Account getAccountByNaverKey(String key) {
		Optional<Account> optAcc = this.accountRepository.findByNaverSocialKey(key);
		Account account = null;
		if(optAcc.isPresent()) {
			account = optAcc.get();
		}
		return account;
	}
	
	public boolean getIsLoginJiraAdd(String email, String password, Integer jiraIdx, HttpSession session) {
		Account account = this.getAccountByEmail(email);
		if(passwordEncoder.matches(password, account.getPw())) {
			session.setAttribute("jiraIdx", jiraIdx);
			Jira jira = jiraService.getByIdx(jiraIdx);
			JiraMembers jiraMembers = JiraMembers.builder()
												 .jira(jira)
												 .account(account)
												 .build();
			jiraMembersRepository.save(jiraMembers);
			JiraRecentClicked jiraRecentClicked = JiraRecentClicked.builder()
																   .jira(jira)
																   .account(account)
																   .build();
			jiraRecentClickedRepository.save(jiraRecentClicked);
			return true;
		}
		return false;
	}
	
	// 비밀번호 변경 메서드
	  public boolean changePassword(@RequestBody NewPwDTO newPw) {
		  System.out.println(newPw.getEmail());
	        Account account = getByEmail(newPw.getEmail()).get();

	        if (account == null || !passwordEncoder.matches(newPw.getOldPw(), account.getPw())) {
	            return false; // 계정을 찾을 수 없거나 기존 비밀번호가 틀린 경우
	        }

	        // 새로운 비밀번호로 변경
	        account.updateAccountPw(passwordEncoder.encode(newPw.getNewPw()));
	        accountRepository.save(account); // 변경된 비밀번호를 저장

	        return true; // 비밀번호 변경 성공
	    }
}
