package com.mysite.jira.service;

import java.math.BigDecimal;
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
	
	public List<Issue> getByManagerIdxIn(Integer[] idx){
		return issueRepository.findByManagerIdxIn(idx);
	}
	
	public List<ManagerDTO> getManagerIdxAndNameByJiraIdx(Integer idx){
		List<ManagerDTO> managerList = new ArrayList<>();
		
		List<Object[]> managerListObject = issueRepository.findByManagerIdxNullIn(idx);
		 for (Object[] result : managerListObject) {
			 	Integer managerIdx=((BigDecimal)result[0]).intValue(); 
	            String name = (String) result[1]; 
	            String iconFilename = (String) result[2];
	            // DTO 객체 생성
	            ManagerDTO managerDTO = new ManagerDTO(managerIdx, name, iconFilename);
	            // DTO 객체를 List에 추가
	            managerList.add(managerDTO);
	        }
		return managerList;
	}
	
	public List<Issue> getByManagerNameIn(String[] name){
		return issueRepository.findByManagerNameIn(name);
	}
	
	public List<Issue> getByManagerNull(){
		return issueRepository.findByManagerIsNull();
	}
	
}
