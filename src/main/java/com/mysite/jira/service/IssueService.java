package com.mysite.jira.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.IssueTypeListDTO;
import com.mysite.jira.dto.ManagerDTO;
import com.mysite.jira.dto.project.summation.PercentTableDTO;
import com.mysite.jira.dto.project.summation.chartDTO;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.ProjectLogData;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.IssuePriorityRepository;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.IssueStatusRepository;
import com.mysite.jira.repository.IssueTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	
	private final IssueRepository issueRepository;
	
	private final IssueStatusRepository issueStatusRepository;
	
	private final IssuePriorityRepository issuePriorityRepository;
	
	private final IssueTypeRepository issueTypeRepository;
	
	private final AccountRepository accountRepository;
	
	public List<Issue> getIssuesByJiraIdx(Integer jiraIdx) {
		return issueRepository.findByJiraIdx(jiraIdx);
	}
	
	public List<IssuePriority> getIssuePriority(){
		return issuePriorityRepository.findAll();
	}
	
	public List<Issue> getReporterNameIn(String[] name){
		return issueRepository.findByReporterNameIn(name);
	}
	
	public List<Issue> getIssuePriorityNameIn(String[] name){
		return issueRepository.findByIssuePriorityNameIn(name);
	}
	
	public List<Issue> getStartDateGreaterThanEqual(LocalDateTime startDate){
		return issueRepository.findIssuesByStartDate(startDate);
	}
	
	public List<Issue> getLastDateLessThanEqual(LocalDateTime lastDate){
		return issueRepository.findIssuesByLastDate(lastDate);
	}
	
	public List<Issue> getcreateStartDateGreaterThanEqual(LocalDateTime startDate){
		return issueRepository.findIssuesBycreateStartDate(startDate);
	}
	
	public List<Issue> getcreateLastDateLessThanEqual(LocalDateTime lastDate){
		return issueRepository.findIssuesBycreateLastDate(lastDate);
	}
	public List<Issue> getfinishStartDateGreaterThanEqual(LocalDateTime startDate){
		return issueRepository.findIssuesByfinishStartDate(startDate);
	}
	
	public List<Issue> getfinishLastDateLessThanEqual(LocalDateTime lastDate){
		return issueRepository.findIssuesByfinishLastDate(lastDate);
	}

	public List<Issue> getIssueByNameLike(String text){
		return issueRepository.findByNameLike("%" + text + "%");
	}
	
	public List<Issue> getIssueByStatus(Integer number){
		return issueRepository.findByIssueStatus_Status(number);
	}
	
	public List<Issue> getIssueByStatusNot(Integer number){
		return issueRepository.findByIssueStatus_StatusNot(number);
	}
	
	public List<Issue> getIssueByQuery(String text){
		return issueRepository.findByQuery(text);
				}
	public List<Issue> getIssueByBetweenCreateDateDesc(Integer jiraIdx, LocalDateTime startDate, LocalDateTime endDate){
		List<Issue> issues = issueRepository.IssueByJiraIdxAndCreateDateBetweenOrderByCreateDateDesc(jiraIdx, startDate,endDate);
		// ProjectLogData의 중복값 제거
		for (int i = 0; i < issues.size(); i++) {
			Set<Integer> setIconFiles = new HashSet<>();
			List<ProjectLogData> logDataList = new ArrayList<>();
			for (ProjectLogData logData : issues.get(i).getProjectLogDataList()) {
				if (setIconFiles.add(logData.getAccount().getIdx())) {
					logDataList.add(logData);
				}
			}
			if(logDataList.size() != 0) {
				issues.get(i).updateProjectLogDataList(logDataList);
			}
		}
		return issues;
	}
	
	// kdw 오늘
	public List<Issue> getTodayIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.now();
		return this.getIssueByBetweenCreateDateDesc(jiraIdx, startDate, endDate);
	}

	// kdw 어제
	public List<Issue> getYesterdayIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
		return this.getIssueByBetweenCreateDateDesc(jiraIdx, startDate, endDate);
	}

	// kdw 지난주
	public List<Issue> getWeekIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX);
		return this.getIssueByBetweenCreateDateDesc(jiraIdx, startDate, endDate);
	}

	// kdw 지난달
	public List<Issue> getMonthIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(8), LocalTime.MIDNIGHT);
		return this.getIssueByBetweenCreateDateDesc(jiraIdx, startDate, endDate);
	}

	// kdw 한달이상
	public List<Issue> getMonthGreaterIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(365 * 2), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(31), LocalTime.MIDNIGHT);
		return this.getIssueByBetweenCreateDateDesc(jiraIdx, startDate, endDate);
	}
	
	// kdw 보류
	public List<Issue> getManagerByIssueStatusIn(Integer jiraIdx, Integer managerIdx){
		// 할일 = 1, 진행중 = 2
		Integer[] statusArr = {1, 2};
 		return issueRepository.findByJiraIdxAndManagerIdxAndIssueStatus_StatusInOrderByIssueStatus_NameDesc(jiraIdx, managerIdx, statusArr);
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
	
	// kdw
	public Integer getMangerByIssueStatusInCount(Integer jiraIdx, Integer managerIdx) {
		Integer[] statusArr = {1, 2};
		return issueRepository.countByJiraIdxAndManagerIdxAndIssueStatus_StatusIn(jiraIdx, managerIdx, statusArr);
	}

	// kdw (7일이내 완료한 이슈의 개수 summation)
	public Integer getSevenDayComplementIssueCount(Integer ProjectIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.now();
		// 완료 3
		Integer status = 3;
		return issueRepository.countByProjectIdxAndIssueStatus_statusAndFinishDateBetween(ProjectIdx, status, startDate, endDate);
	}
	// kdw (7일이내 업데이트한 이슈의 개수 summation)
	public Integer getSevenDayUpdateIssueCount(Integer ProjectIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.now();
		return issueRepository.countByProjectIdxAndEditDateBetween(ProjectIdx, startDate, endDate);
	}
	// kdw (7일이내 만든 이슈의 개수 summation)
	public Integer getSevenDayCreateIssueCount(Integer ProjectIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.now();
		return issueRepository.countByProjectIdxAndCreateDateBetween(ProjectIdx, startDate, endDate);
	}
	// kdw (7일이내 기한초과한 이슈의 개수 summation)
	public Integer getSevenDayDeadlineIssueCount(Integer ProjectIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.now();
		return issueRepository.countByProjectIdxAndDeadlineDateBetween(ProjectIdx, startDate, endDate);
	}
	
	//kdw (도넛차트, 이슈상태로 묶은 상태의 이슈의 개수)
	public List<chartDTO> getStatusChartDTO(Integer projectIdx){
		List<Map<String, Object>> statusDTOList = issueStatusRepository.findByStatusByIssueCount(projectIdx);
		List<chartDTO> result = new ArrayList<>();
		
		for(int i = 0; i < statusDTOList.size(); i++) {
			String name = "";
			if(statusDTOList.get(i).get("name") instanceof String) {
				name = statusDTOList.get(i).get("name").toString();
			}
			Long count = 0l;
			if(statusDTOList.get(i).get("count") instanceof Long) {
				count = (Long)statusDTOList.get(i).get("count");
			}
			chartDTO dto = chartDTO.builder()
								   .name(name)
								   .count(count)
								   .build();
			result.add(dto);
		}
		return result;
	}
	
	// kdw (이슈 우선순위 차트, 우선순위로 묶은 이슈의 개수)
	public List<chartDTO> getPriorityChartDTO(Integer projectIdx){
		List<Map<String, Object>> priorityDTOList = issuePriorityRepository.findByPriorityByIssueCount(projectIdx);
		List<chartDTO> result = new ArrayList<>();
		for(int i = 0; i < priorityDTOList.size(); i++) {
			String name = "";
			if(priorityDTOList.get(i).get("name") instanceof String) {
				name = priorityDTOList.get(i).get("name").toString();
			}
			Long count = 0l;
			if(priorityDTOList.get(i).get("count") instanceof Long) {
				count = (Long)priorityDTOList.get(i).get("count");
			}
			chartDTO dto = chartDTO.builder()
								   .name(name)
								   .count(count)
								   .build();
			result.add(dto);
		}
		return result;
	}
	
	// kdw (작업 유형에 따른 이슈의 개수: 작업유형)
	public List<PercentTableDTO> getTaskTypeDTO(Integer projectIdx){
		List<Map<String, Object>> taskDTOList = issueTypeRepository.findByTypeByIssueCount(projectIdx);
		List<PercentTableDTO> result = new ArrayList<>();
		for(int i = 0; i < taskDTOList.size(); i++) {
			String name = "";
			if(taskDTOList.get(i).get("name") instanceof String) {
				name = taskDTOList.get(i).get("name").toString();
			}
			String iconFilename = "";
			if(taskDTOList.get(i).get("iconFilename") instanceof String) {
				iconFilename = taskDTOList.get(i).get("iconFilename").toString();
			}
			Long count = 0l;
			if(taskDTOList.get(i).get("count") instanceof Long) {
				count = (Long)taskDTOList.get(i).get("count");
			}
			PercentTableDTO dto = PercentTableDTO.builder()
									     .name(name)
									     .iconFilename(iconFilename)
									     .count(count)
									     .build();
			result.add(dto);
		}
		return result;
	}
	// kdw (작업 유형에 따른 총 이슈의 개수: 작업유형)
	public Integer getSumTaskTypeDTO(Integer projectIdx){
		int sum = 0;
		List<Map<String, Object>> taskDTOList = issueTypeRepository.findByTypeByIssueCount(projectIdx);
		
		for(int i = 0; i < taskDTOList.size(); i++) {
			if(taskDTOList.get(i).get("count") instanceof Long) {
				sum += (Long)taskDTOList.get(i).get("count");
			}
			
		}
		return (int)sum;
	}
	
	// kdw (담당자에 따른 이슈의 개수)
	public List<PercentTableDTO> getManagerIssueCount(Integer projectIdx){
		List<Map<String, Object>> managerByIssueDTOList = accountRepository.findByManagerByIssueCount(projectIdx);
		List<PercentTableDTO> result = new ArrayList<>();
		for(int i = 0; i < managerByIssueDTOList.size(); i++) {
			String name = "";
			if(managerByIssueDTOList.get(i).get("name") instanceof String
			&& managerByIssueDTOList.get(i).get("name") != null) {
				name = managerByIssueDTOList.get(i).get("name").toString();
			}
			String iconFilename = "";
			if(managerByIssueDTOList.get(i).get("iconFilename") instanceof String 
			&& managerByIssueDTOList.get(i).get("iconFilename") != null) {
				iconFilename = managerByIssueDTOList.get(i).get("iconFilename").toString();
			}
			Long count = 0l;
			if(managerByIssueDTOList.get(i).get("count") instanceof Long) {
				count = (Long)managerByIssueDTOList.get(i).get("count");
			}
			PercentTableDTO dto = PercentTableDTO.builder()
									     .name(name)
									     .iconFilename(iconFilename)
									     .count(count)
									     .build();
			result.add(dto);
		}
		return result;
	}
}
