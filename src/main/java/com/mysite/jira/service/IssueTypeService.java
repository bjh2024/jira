package com.mysite.jira.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.IssueTypeListDto;
import com.mysite.jira.repository.IssueTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueTypeService {
	
	private final IssueTypeRepository issueTypeRepository;
	
	  // distinct한 issueType 데이터 처리
    public List<IssueTypeListDto> getDistinctIssueTypes(Integer jiraIdx) {
        List<IssueTypeListDto> issueTypeDTOList = new ArrayList<>();

        // distinct한 name과 iconFilename 가져오기
        List<Object[]> issueTypes = issueTypeRepository.findDistinctNameAndIconFilename(jiraIdx);

        // 결과를 DTO로 변환하여 List에 추가
        for (Object[] result : issueTypes) {
            String name = (String) result[0]; // 첫 번째 값: 이름
            String iconFilename = (String) result[1]; // 두 번째 값: 아이콘 파일 이름

            // DTO 객체 생성
            IssueTypeListDto issueTypeDTO = new IssueTypeListDto(name, iconFilename);

            // DTO 객체를 List에 추가
            issueTypeDTOList.add(issueTypeDTO);
        }

        return issueTypeDTOList;
    }

}