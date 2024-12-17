package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.jira.dto.project.SearchDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.entity.ProjectMembers;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.JiraMembersRepository;
import com.mysite.jira.repository.ProjectMembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	
	private final ProjectMembersRepository projectMembersRepository;
	
	private final JiraMembersRepository jiraMembersRepository;
	
	private final AccountRepository accountRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public Account getAccountByIdx(Integer idx) {
		Optional<Account> opAccount = accountRepository.findById(idx);
		Account account = null;
		if(!opAccount.isEmpty()) {
			account = opAccount.get();
		}
		return account;
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
	
	public Account singup(String username, String email, String pw) {
		String code = "";
		for(int i = 0; i < 4; i++) {
			int tempCode = (int) (Math.random() * 26) + 65;
			code += (char)tempCode;
		}
		Account newUser = Account.builder()
								.name(username)
								.email(email)
								.pw(passwordEncoder.encode(pw))
								.iconFilename("user_icon_file5.png")
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
		}else {
			throw new NoSuchElementException("Account not found");
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
		
		existingAccount.updateAccount();
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
}
