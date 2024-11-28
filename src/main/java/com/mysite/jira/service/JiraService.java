package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.repository.JiraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraService {

	private final JiraRepository jiraRepository;
	
	public List<String> getjiraLeaderList(Integer accountIdx) {
		List<String> result = new ArrayList<>();
		try {
			result = jiraRepository.findJiraAndMembersByAccountIdxName(accountIdx);
			for (int i = 0; i < result.size(); i++) {
				result.set(i, result.get(i).substring(0, 2));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		// 두글자만 나오게
		return result;
	}
	
}
