package com.mysite.jira.service;

import java.lang.module.ModuleDescriptor.Builder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.DTO.IssueRecentInputDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.JiraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeaderService {

	private final JiraRepository jiraRepository;
	
	private final IssueRepository issueRepository;
	
	// jira리더 list
	public List<String> getjiraLeaderList(Integer accountIdx){
		List<String> result = jiraRepository.findJiraAndMembersByAccountIdxName(accountIdx);
		// 두글자만 나오게
		for(int i = 0; i < result.size(); i++) {
			result.set(i, result.get(i).substring(0, 2));
		}
		return result;
	}
	
	// jiraIdx에 대한 issue list
	public List<IssueRecentInputDTO> getRecentInputList(Integer jiraIdx, Account account){
		List<Issue> issueList = issueRepository.findByJiraIdxAndAccount_NameOrderByClickedDateDesc(jiraIdx, account.getName());
		List<IssueRecentInputDTO> result = new ArrayList<>();
		for(int i = 0; i < issueList.size(); i++) {
			IssueRecentInputDTO dto = IssueRecentInputDTO
										.builder()
										.name(issueList.get(i).getName())
										.key(issueList.get(i).getKey())
										.iconFilename(issueList.get(i).getIssueType().getIconFilename())
										.build();
			
			result.add(dto);
		}
		return result;
	}
	
	
}
