package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.IssueStatusListDTO;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.repository.IssueStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueStatusService {

	private final IssueStatusRepository issueStatusRepository;
	
	public IssueStatus getByName(String name){
		List<IssueStatus> issueStatusList = issueStatusRepository.findByName(name);
		IssueStatus result = issueStatusList.get(0);
		return result;
	}
	// issueStatus를 name과 status만 뽑아 만든 DTO
	 public List<IssueStatusListDTO> getDistinctIssueStatus(Integer jiraIdx) {
	        List<IssueStatusListDTO> issueStatusListDTO = new ArrayList<>();

	        // distinct한 name과 iconFilename 가져오기
	        List<Object[]> issueStatus = issueStatusRepository.findDistinctNameAndStatusByJiraIdx(jiraIdx);

	        // 결과를 DTO로 변환하여 List에 추가
	        for (Object[] result : issueStatus) {
	            String name = (String) result[0]; // 첫 번째 값: 이름
	            Integer status = (Integer) result[1]; // 두 번째 값: status

	            // DTO 객체 생성
	            IssueStatusListDTO issueStatusDTO = new IssueStatusListDTO(name, status);

	            // DTO 객체를 List에 추가
	            issueStatusListDTO.add(issueStatusDTO);
	        }

	        return issueStatusListDTO;
	    }
	 
	
}
