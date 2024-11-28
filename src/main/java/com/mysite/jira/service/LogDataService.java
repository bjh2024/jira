package com.mysite.jira.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.header.HeaderAlarmDTO;
import com.mysite.jira.entity.ProjectLogData;
import com.mysite.jira.repository.ProjectLogDataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogDataService {

	private final ProjectLogDataRepository projectLogDataRepository;
	
	private final UtilityService utilityService;
	
	// 30일전 jira 로그 데이터 가져오기
	public List<HeaderAlarmDTO> getProjectLogData(Integer jiraIdx){
		
	List<ProjectLogData> logDataList = projectLogDataRepository.findByIssue_jiraIdxAndCreateDateGreaterThanEqualOrderByCreateDateDesc(jiraIdx, LocalDateTime.now().minusDays(30));
	List<HeaderAlarmDTO> alarmDTO = new ArrayList<>();
		for(int i = 0; i < logDataList.size(); i++) {
			String elapsedComment = utilityService.getElapsedComment(logDataList.get(i).getCreateDate());
			HeaderAlarmDTO dto = HeaderAlarmDTO.builder()
										   .accountName(logDataList.get(i).getAccount().getName())
										   .accountFilename(logDataList.get(i).getAccount().getIconFilename())
										   .issueName(logDataList.get(i).getIssue().getName())
										   .issueTypeFilename(logDataList.get(i).getIssue().getIssueType().getIconFilename())
										   .issueStatusName(logDataList.get(i).getIssue().getIssueStatus().getName())
										   .logContent(logDataList.get(i).getProjectLogStatus().getContent())
										   .elapsedTime(elapsedComment)
										   .key(logDataList.get(i).getIssue().getKey())
										   .build();			
			alarmDTO.add(dto);
		}
		return alarmDTO;
				
	}
}
