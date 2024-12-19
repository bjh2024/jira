package com.mysite.jira.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.IssueTypeListDTO;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.repository.IssueTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueTypeService {
	
	private final IssueTypeRepository issueTypeRepository;
	
	public IssueType getByName(String name){
		List<IssueType> issueTypeList = issueTypeRepository.findByName(name);
		IssueType result = issueTypeList.get(0);
		return result;
	}
	  // distinct한 issueType 데이터 처리
    public List<IssueTypeListDTO> getDistinctIssueTypes(Integer jiraIdx) {
        List<IssueTypeListDTO> issueTypeDTOList = new ArrayList<>();

        // distinct한 name과 iconFilename 가져오기
        List<Object[]> issueTypes = issueTypeRepository.findDistinctNameAndIconFilenameByJiraIdx(jiraIdx);

        // 결과를 DTO로 변환하여 List에 추가
        for (Object[] result : issueTypes) {
            String name = (String) result[0]; // 첫 번째 값: 이름
            String iconFilename = (String) result[1]; // 두 번째 값: 아이콘 파일 이름

            // null 체크 (optional)
            if (name == null) {
                name = "Unknown"; // 기본 값 설정
            }
            if (iconFilename == null) {
                iconFilename = "default.png"; // 기본 값 설정
            }

            // DTO 객체 생성
            IssueTypeListDTO issueTypeDTO = new IssueTypeListDTO(name, iconFilename);

            // DTO 객체를 List에 추가
            issueTypeDTOList.add(issueTypeDTO);
        }

        return issueTypeDTOList;
    }

}
