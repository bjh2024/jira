package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.IssueTypeListDTO;
import com.mysite.jira.dto.ManagerDTO;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {

	private final IssueRepository issueRepository;
	
	public List<Issue> getIssuesByJiraIdx(Integer jiraIdx){
		return issueRepository.findByJiraIdx(jiraIdx);
	}
	
	public List<Issue> getIssuesByIssueTypeName(String[] name){
		List<Issue> result = issueRepository.findByIssueTypeNameIn(name);
		return result;
	}
	
	public List<Issue> getIssuesByIssueStatusName(String[] name){
		List<Issue> result = issueRepository.findByIssueStatusNameIn(name);
		return result;
	}
	
	public List<Issue> getIssuesByManagerIdxIn(List<Integer> idx) {
	    // idx가 null이거나 빈 리스트일 경우 빈 리스트를 반환
	    if (idx == null || idx.isEmpty()) {
	        return new ArrayList<>(); // 빈 리스트 반환
	    }

	    return issueRepository.findByManagerIdxNullIn(idx);
	}

	
	public List<ManagerDTO> getManagerIdxAndNameByJiraIdx(Integer idx){
		List<ManagerDTO> managerList = new ArrayList<>();
		
		List<Object[]> managerListObject = issueRepository.findManagerIdxAndNameByJiraIdx(idx);
		 for (Object[] result : managerListObject) {
			 	Integer managerIdx= (Integer) result[0]; 
	            String name = (String) result[1]; 
	            
	            // DTO 객체 생성
	            ManagerDTO managerDTO = new ManagerDTO(managerIdx, name);
	            // DTO 객체를 List에 추가
	            managerList.add(managerDTO);
	        }
		return managerList;
	}
}
