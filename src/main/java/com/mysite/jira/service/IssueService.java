package com.mysite.jira.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.ChartDTO;
import com.mysite.jira.dto.ManagerDTO;
import com.mysite.jira.dto.dashboard.AllotDTO;
import com.mysite.jira.dto.dashboard.IssueCompleteChartDTO;
import com.mysite.jira.dto.project.summation.PercentTableDTO;
import com.mysite.jira.entity.FilterIssueType;
import com.mysite.jira.entity.FilterProject;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.ProjectLogData;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.FilterDoneDateRepository;
import com.mysite.jira.repository.FilterDoneRepository;
import com.mysite.jira.repository.FilterIssueCreateDateRepository;
import com.mysite.jira.repository.FilterIssuePriorityRepository;
import com.mysite.jira.repository.FilterIssueStatusRepository;
import com.mysite.jira.repository.FilterIssueTypeRepository;
import com.mysite.jira.repository.FilterIssueUpdateRepository;
import com.mysite.jira.repository.FilterManagerRepository;
import com.mysite.jira.repository.FilterProjectRepository;
import com.mysite.jira.repository.FilterReporterRepository;
import com.mysite.jira.repository.IssuePriorityRepository;
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
	
	private final FilterProjectRepository filterProjectRepository;
	private final FilterIssueStatusRepository filterIssueStatusRepository;
	private final FilterIssueTypeRepository filterIssueTypeRepository;
	private final FilterManagerRepository filterManagerRepository;
	private final FilterIssuePriorityRepository filterIssuePriorityRepository;
	private final FilterDoneRepository filterDoneRepository;
	private final FilterDoneDateRepository filterDoneDateRepository;
	private final FilterIssueUpdateRepository filterIssueUpdateRepository;
	private final FilterIssueCreateDateRepository filterIssueCreateDateRepository;
	private final FilterReporterRepository filterReporterRepository;
	
	public List<Issue> getIssuesByJiraIdx(Integer jiraIdx) {
		return issueRepository.findByJiraIdx(jiraIdx);
	}

	public List<IssuePriority> getIssuePriority() {
		return issuePriorityRepository.findAll();
	}

	public List<Issue> getReporterNameIn(String[] name) {
		return issueRepository.findByReporterNameIn(name);
	}

	public List<Issue> getIssuePriorityNameIn(String[] name) {
		return issueRepository.findByIssuePriorityNameIn(name);
	}

	public List<Issue> getStartDateGreaterThanEqual(LocalDateTime startDate) {
		return issueRepository.findIssuesByStartDate(startDate);
	}

	public List<Issue> getLastDateLessThanEqual(LocalDateTime lastDate) {
		return issueRepository.findIssuesByLastDate(lastDate);
	}

	public List<Issue> getcreateStartDateGreaterThanEqual(LocalDateTime startDate) {
		return issueRepository.findIssuesBycreateStartDate(startDate);
	}

	public List<Issue> getcreateLastDateLessThanEqual(LocalDateTime lastDate) {
		return issueRepository.findIssuesBycreateLastDate(lastDate);
	}

	public List<Issue> getfinishStartDateGreaterThanEqual(LocalDateTime startDate){
		return issueRepository.findIssuesByfinishStartDate(startDate);
	}

	public List<Issue> getfinishLastDateLessThanEqual(LocalDateTime lastDate) {
		return issueRepository.findIssuesByfinishLastDate(lastDate);
	}

	public List<Issue> getIssueByNameLike(String text) {
		return issueRepository.findByNameLike("%" + text + "%");
	}

	public List<Issue> getIssueByStatus(Integer number) {
		return issueRepository.findByIssueStatus_Status(number);
	}

	public List<Issue> getIssueByStatusNot(Integer number) {
		return issueRepository.findByIssueStatus_StatusNot(number);
	}

	public List<Issue> getIssueByQuery(String text) {
		return issueRepository.findByQuery(text);
	}

	public List<Issue> getIssueByProjectIdxAndFilterIdx(Integer filterIdx){
		List<FilterProject> issueProject = filterProjectRepository.findByFilterIdx(filterIdx);
		
		Integer[] projectIdxArr = new Integer[issueProject.size()];
		
		for (int i = 0; i < issueProject.size(); i++) {
			projectIdxArr[i] = issueProject.get(i).getProject().getIdx();
		}
		
		List<Issue> list = issueRepository.findByProjectIdxIn(projectIdxArr);
		return list;
	}
	
	public List<Issue> getIssueByIssueTypeAndFilterIdx(Integer filterIdx){
		List<FilterIssueType> issueType = filterIssueTypeRepository.findByFilterIdx(filterIdx);
		String[] issueTypeArr = new String[issueType.size()];
		for (int i = 0; i < issueType.size(); i++) {
			issueTypeArr[i] = issueType.get(i).getIssueType().getName();
		}
		List<Issue> list = issueRepository.findByIssueTypeNameIn(issueTypeArr);
		return list;
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
			if (logDataList.size() != 0) {
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

	// kdw 나에게 할당된 이슈의 총 개수
	public Integer getMangerByIssueStatusInCount(Integer jiraIdx, Integer managerIdx) {
		Integer[] statusArr = { 1, 2 };
		return issueRepository.countByJiraIdxAndManagerIdxAndIssueStatus_StatusIn(jiraIdx, managerIdx, statusArr);
	}

	// kdw 완료된 이슈를 제외한 나에게 할당된 이슈목록
	public List<Issue> getManagerByIssueStatusIn(Integer jiraIdx, Integer managerIdx) {
		// 할일 = 1, 진행중 = 2
		Integer[] statusArr = { 1, 2 };
		return issueRepository.findByJiraIdxAndManagerIdxAndIssueStatus_StatusInOrderByIssueStatus_NameDesc(jiraIdx,
				managerIdx, statusArr);
	}

	// kdw 완료된 이슈를 제외한 나에게 할당된 이슈목록(페이징)
	public List<AllotDTO> getManagerByIssueStatusIn(Integer jiraIdx, Integer managerIdx, Integer pageNum,
			Integer col) {
		// 할일 = 1, 진행중 = 2
		Integer[] statusArr = { 1, 2 };
		List<Issue> issueList = issueRepository
				.findByJiraIdxAndManagerIdxAndIssueStatus_StatusInOrderByIssueStatus_NameDesc(jiraIdx, managerIdx,
						statusArr);
		List<AllotDTO> result = new ArrayList<>();
		int start = pageNum * col;
		// if(start > issueList.size())
		// 에러 처리
		// start = 0;
		int end = start + col > issueList.size() ? issueList.size() : start + col;
		// js에서도 처리중 => col
		for (int i = start; i < end; i++) {
			String iconFilename = issueList.get(i).getIssueType().getIconFilename();
			String key = issueList.get(i).getKey();
			String name = issueList.get(i).getName();
			String priorityIconFilename = issueList.get(i).getIssuePriority().getIconFilename();
			AllotDTO dto = AllotDTO.builder().iconFilename(iconFilename).key(key).name(name)
					.priorityIconFilename(priorityIconFilename).build();
			result.add(dto);
		}
		return result;
	}

	public List<Issue> getIssuesByIssueTypeName(String[] name) {
		List<Issue> result = issueRepository.findByIssueTypeNameIn(name);
		return result;
	}

	public List<Issue> getIssuesByIssueStatusName(String[] name) {
		List<Issue> result = issueRepository.findByIssueStatusNameIn(name);
		return result;
	}

	public List<Issue> getByManagerIdxIn(Integer[] idx) {
		return issueRepository.findByManagerIdxIn(idx);
	}

	public List<ManagerDTO> getManagerIdxAndNameByJiraIdx(Integer idx) {
		List<ManagerDTO> managerList = new ArrayList<>();

		List<Object[]> managerListObject = issueRepository.findByManagerIdxNullIn(idx);
		for (Object[] result : managerListObject) {
			Integer managerIdx = ((BigDecimal) result[0]).intValue();
			String name = (String) result[1];
			String iconFilename = (String) result[2];
			// DTO 객체 생성
			ManagerDTO managerDTO = new ManagerDTO(managerIdx, name, iconFilename);
			// DTO 객체를 List에 추가
			managerList.add(managerDTO);
		}
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

	public List<Issue> getByManagerNameIn(String[] name) {
		return issueRepository.findByManagerNameIn(name);
	}

	public List<Issue> getByManagerNull() {
		return issueRepository.findByManagerIsNull();
	}

	// kdw (7일이내 완료한 이슈의 개수 summation)
	public Integer getSevenDayComplementIssueCount(Integer ProjectIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.now();
		// 완료 3
		Integer status = 3;
		return issueRepository.countByProjectIdxAndIssueStatus_statusAndFinishDateBetween(ProjectIdx, status, startDate,
				endDate);
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
	
	// 차트 공통 메서드
	public List<ChartDTO> getChartDTO(List<Map<String, Object>> statusDTOList) {
		List<ChartDTO> result = new ArrayList<>();
		for (int i = 0; i < statusDTOList.size(); i++) {
			String name = "";
			if (statusDTOList.get(i).get("name") instanceof String) {
				name = statusDTOList.get(i).get("name").toString();
			}
			Long count = 0l;
			if (statusDTOList.get(i).get("count") instanceof Long) {
				count = (Long) statusDTOList.get(i).get("count");
			}
			ChartDTO dto = ChartDTO.builder().name(name).count(count).build();
			result.add(dto);
		}
		return result;
	}

	// kdw (도넛차트, 이슈 유형으로 묶은 상태의 이슈의 개수)
	public List<ChartDTO> getTypeChartDTO(Integer projectIdx) {
		List<Map<String, Object>> typeDTOList = issueRepository.findByTypeIssueCount(projectIdx);
		return this.getChartDTO(typeDTOList);
	}

	// kdw (도넛차트, 보고자로 묶은 상태의 이슈의 개수)
	public List<ChartDTO> getReporterChartDTO(Integer projectIdx) {
		List<Map<String, Object>> reporterDTOList = issueRepository.findByReporterIssueCount(projectIdx);
		return this.getChartDTO(reporterDTOList);
	}
	
	// kdw (도넛차트, 담당자로 묶은 상태의 이슈의 개수)
	public List<ChartDTO> getManagerChartDTO(Integer projectIdx) {
		List<Map<String, Object>> managerDTOList = issueRepository.findByManagerIssueCount(projectIdx);
		return this.getChartDTO(managerDTOList);
	}
	
	// kdw (도넛차트, 이슈 상태로 묶은 상태의 이슈의 개수)
	public List<ChartDTO> getStatusChartDTO(Integer projectIdx) {
		List<Map<String, Object>> statusDTOList = issueRepository.findByStatusByIssueCount(projectIdx);
		return this.getChartDTO(statusDTOList);
	}
	
	// kdw (도넛차트, 레이블로 묶은 이슈의 개수)
	public List<ChartDTO> getLabelChartDTO(Integer projectIdx) {
		List<Map<String, Object>> labelDTOList = issueRepository.findByLabelByIssueCount(projectIdx);
		return this.getChartDTO(labelDTOList);
	}
		
	// kdw (이슈 우선순위 차트, 우선순위로 묶은 이슈의 개수)
	public List<ChartDTO> getPriorityChartDTO(Integer projectIdx) {
		List<Map<String, Object>> priorityDTOList = issueRepository.findByPriorityByIssueCount(projectIdx);
		return this.getChartDTO(priorityDTOList);
	}
	
	// kdw (작업 유형에 따른 이슈의 개수: 작업유형)
	public List<PercentTableDTO> getTaskTypeDTO(Integer projectIdx) {
		List<Map<String, Object>> taskDTOList = issueTypeRepository.findByTypeByIssueCount(projectIdx);
		List<PercentTableDTO> result = new ArrayList<>();
		for (int i = 0; i < taskDTOList.size(); i++) {
			String name = "";
			if (taskDTOList.get(i).get("name") instanceof String) {
				name = taskDTOList.get(i).get("name").toString();
			}
			String iconFilename = "";
			if (taskDTOList.get(i).get("iconFilename") instanceof String) {
				iconFilename = taskDTOList.get(i).get("iconFilename").toString();
			}
			Long count = 0l;
			if (taskDTOList.get(i).get("count") instanceof Long) {
				count = (Long) taskDTOList.get(i).get("count");
			}
			PercentTableDTO dto = PercentTableDTO.builder().name(name).iconFilename(iconFilename).count(count).build();
			result.add(dto);
		}
		return result;
	}

	// kdw (작업 유형에 따른 총 이슈의 개수: 작업유형)
	public Integer getSumTaskTypeDTO(Integer projectIdx) {
		int sum = 0;
		List<Map<String, Object>> taskDTOList = issueTypeRepository.findByTypeByIssueCount(projectIdx);

		for (int i = 0; i < taskDTOList.size(); i++) {
			if (taskDTOList.get(i).get("count") instanceof Long) {
				sum += (Long) taskDTOList.get(i).get("count");
			}

		}
		return (int) sum;
	}

	// kdw (담당자에 따른 이슈의 개수)
	public List<PercentTableDTO> getManagerIssueCount(Integer projectIdx) {
		List<Map<String, Object>> managerByIssueDTOList = accountRepository.findByManagerByIssueCount(projectIdx);
		List<PercentTableDTO> result = new ArrayList<>();
		for (int i = 0; i < managerByIssueDTOList.size(); i++) {
			String name = "";
			if (managerByIssueDTOList.get(i).get("name") instanceof String
					&& managerByIssueDTOList.get(i).get("name") != null) {
				name = managerByIssueDTOList.get(i).get("name").toString();
			}
			String iconFilename = "";
			if (managerByIssueDTOList.get(i).get("iconFilename") instanceof String
					&& managerByIssueDTOList.get(i).get("iconFilename") != null) {
				iconFilename = managerByIssueDTOList.get(i).get("iconFilename").toString();
			}
			Long count = 0l;
			if (managerByIssueDTOList.get(i).get("count") instanceof Long) {
				count = (Long) managerByIssueDTOList.get(i).get("count");
			}
			PercentTableDTO dto = PercentTableDTO.builder().name(name).iconFilename(iconFilename).count(count).build();
			result.add(dto);
		}
		return result;
	}
	
	// (만듦 대비 해결됨, 최근의 만듦) 차트 DTO 메서드
	public List<IssueCompleteChartDTO> getIssueCompleteChartDTO(List<Map<String, Object>> issueList){
		List<IssueCompleteChartDTO> result = new ArrayList<>();
		for(int i = 0; i < issueList.size(); i++) {
			String date = issueList.get(i).get("issueDate").toString();
			Long count = (Long)issueList.get(i).get("count");
			
			IssueCompleteChartDTO dto = IssueCompleteChartDTO.builder()
														   .date(date)
														   .count(count)
														   .build();
			result.add(dto);
		}
		return result;
	}
	
	// 만듦 대비 해결됨 차트(생성일)
	public List<IssueCompleteChartDTO> getIssueCountBetweenCreateDate(Integer projectIdx, LocalDateTime startDate){
		List<Map<String, Object>> issueList = issueRepository.findByIssueCreateCountBetweenCreateDate(projectIdx, startDate);
		return this.getIssueCompleteChartDTO(issueList);
	}
	
	// 만듦 대비 해결됨 차트(완료)
	public List<IssueCompleteChartDTO> getIssueCountBetweenFinishDate(Integer projectIdx, LocalDateTime startDate){
		List<Map<String, Object>> issueList = issueRepository.findByIssueCreateCountBetweenFinishDate(projectIdx, startDate);
		return this.getIssueCompleteChartDTO(issueList);
	}
	
	// 최근의 만듦 차트(미완료)
	public List<IssueCompleteChartDTO> getIssueNotCompleteCountBetweenCreateDate(Integer projectIdx, LocalDateTime startDate){
		List<Map<String, Object>> issueList = issueRepository.findByIssueNotCompleteCountBetweenCreateDate(projectIdx, startDate);
		return this.getIssueCompleteChartDTO(issueList);
	}
	
	// 최근의 만듦 차트(완료)
	public List<IssueCompleteChartDTO> getIssueCompleteCountBetweenCreateDate(Integer projectIdx, LocalDateTime startDate){
		List<Map<String, Object>> issueList = issueRepository.findByIssueCompleteCountBetweenCreateDate(projectIdx, startDate);
		return this.getIssueCompleteChartDTO(issueList);
	}

}
