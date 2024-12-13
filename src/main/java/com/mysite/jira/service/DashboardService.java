package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.dashboard.DashboardListDTO;
import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.entity.DashboardAllot;
import com.mysite.jira.entity.DashboardCol;
import com.mysite.jira.entity.DashboardIssueComplete;
import com.mysite.jira.entity.DashboardIssueFilter;
import com.mysite.jira.entity.DashboardIssueRecent;
import com.mysite.jira.entity.DashboardIssueStatistics;
import com.mysite.jira.entity.DashboardPieChart;
import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.DashboardAllotRepository;
import com.mysite.jira.repository.DashboardColRepository;
import com.mysite.jira.repository.DashboardIssueCompleteRepository;
import com.mysite.jira.repository.DashboardIssueFilterRepository;
import com.mysite.jira.repository.DashboardIssueRecentRepository;
import com.mysite.jira.repository.DashboardIssueStatisticsRepository;
import com.mysite.jira.repository.DashboardPieChartRepository;
import com.mysite.jira.repository.DashboardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final DashboardRepository dashboardRepository;
	
	private final DashboardAllotRepository dashboardAllotRepository;
	
	private final DashboardIssueCompleteRepository dashboardIssueCompleteRepository;

	private final DashboardIssueFilterRepository dashboardIssueFilterRepository;
	
	private final DashboardIssueRecentRepository dashboardIssueRecentRepository;
	
	private final DashboardIssueStatisticsRepository dashboardIssueStatisticsRepository;
	
	private final DashboardPieChartRepository dashboardPieChartRepository;
	
	private final DashboardColRepository dashboardColRepository;
	
	private final ProjectService projectService;
	
	public Dashboard getDashboardByIdx(Integer idx) {
		Optional<Dashboard> dashboard = dashboardRepository.findById(idx);
		if(!dashboard.isEmpty())
			return dashboard.get();
		return null;
	}
	
	public DashboardCol getDashboardColByIdx(Integer idx) {
		Optional<DashboardCol> dashboardCol = dashboardColRepository.findById(idx);
		if(!dashboardCol.isEmpty())
			return dashboardCol.get();
		return null;
	}
	
	// 대시보드 리스트
	public List<DashboardListDTO> getDashboardList(Integer accountIdx, Integer jiraIdx){
		List<Map<String, Object>> dashboardList = dashboardRepository.findByDashboardList(accountIdx, jiraIdx);
		List<DashboardListDTO> result = new ArrayList<>();
		
		for(int i = 0; i < dashboardList.size(); i++) {
			Dashboard dashboard = null;
			if(dashboardList.get(i).get("dashboard") instanceof Dashboard) {
				dashboard = (Dashboard)dashboardList.get(i).get("dashboard");
			}
			boolean isLike = dashboardList.get(i).get("isLike").toString().equals("true") ? true : false;
			Long likeCount = (Long)dashboardList.get(i).get("likeCount");
			DashboardListDTO dto = DashboardListDTO.builder()
												   .dashboard(dashboard)
												   .isLike(isLike)
												   .likeCount(likeCount)
												   .build();
			result.add(dto);
		}
		return result;
	}
	
	// 대시보드 디테일
	public List<List<Object>> getDashboardDetail(Integer dashboardIdx){
		List<Map<String, Object>> dashboardDetail = dashboardRepository.findByDashboardDetail(dashboardIdx);
		List<List<Object>> result = new ArrayList<>();
		List<Object> divOrder1List = new ArrayList<>();
		List<Object> divOrder2List = new ArrayList<>();
		
		for(int i = 0; i < dashboardDetail.size(); i++) {
			String type = dashboardDetail.get(i).get("type").toString();
			Integer idx = (Integer)dashboardDetail.get(i).get("idx");
			Integer divOrderX = (Integer)dashboardDetail.get(i).get("divOrderx");
			Object choiceDashboardItem = new Object();
			switch(type) {
				case "pie_chart":
					Optional<DashboardPieChart> pieChart = dashboardPieChartRepository.findById(idx);
					if(!pieChart.isEmpty())
						choiceDashboardItem = pieChart.get();
					break;
				case "allot":
					Optional<DashboardAllot> allot = dashboardAllotRepository.findById(idx);
					if(!allot.isEmpty())
						choiceDashboardItem = allot.get();
					break;
				case "issue_complete":
					Optional<DashboardIssueComplete> issueComplete = dashboardIssueCompleteRepository.findById(idx);
					if(!issueComplete.isEmpty())
						choiceDashboardItem = issueComplete.get();
					break;
				case "issue_recent":
					Optional<DashboardIssueRecent> issueRecent = dashboardIssueRecentRepository.findById(idx);
					if(!issueRecent.isEmpty())
						choiceDashboardItem = issueRecent.get();
					break;
				case "issue_statistics":
					Optional<DashboardIssueStatistics> issueStatistics = dashboardIssueStatisticsRepository.findById(idx);
					if(!issueStatistics.isEmpty())
						choiceDashboardItem = issueStatistics.get();
					break;
				case "issue_filter":
					Optional<DashboardIssueFilter> issueFilter = dashboardIssueFilterRepository.findById(idx);
					if(!issueFilter.isEmpty())
						choiceDashboardItem = issueFilter.get();
					break;
			}
			
			if(divOrderX == 1) {
				divOrder1List.add(choiceDashboardItem);
			}else {
				divOrder2List.add(choiceDashboardItem);
			}
		}
		result.add(divOrder1List);
		result.add(divOrder2List);
		return result;
	}
	
	// 대시보드 item 순서 바꾸기
	public void editOrder(Integer dashboardIdx, Integer divOrderX, Integer divOrderY) {
		List<DashboardPieChart> pieChartList = dashboardPieChartRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		List<DashboardAllot> allotList = dashboardAllotRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		List<DashboardIssueComplete> issueCompleteList = dashboardIssueCompleteRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		List<DashboardIssueRecent> issueRecentList = dashboardIssueRecentRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		List<DashboardIssueStatistics> issueStatisticsList = dashboardIssueStatisticsRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		List<DashboardIssueFilter> issueFilterList = dashboardIssueFilterRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		
		for(int i = 0; i < pieChartList.size(); i++) {
			DashboardPieChart pieChart = pieChartList.get(i);
			pieChart.updateOrder(divOrderX, pieChart.getDivOrderY()+1);
			
			dashboardPieChartRepository.save(pieChart);
		}
		for(int i = 0; i < allotList.size(); i++) {
			DashboardAllot allot = allotList.get(i);
			allot.updateOrder(divOrderX, allot.getDivOrderY()+1);
			
			dashboardAllotRepository.save(allot);
		}
		for(int i = 0; i < issueCompleteList.size(); i++) {
			DashboardIssueComplete issueComplete = issueCompleteList.get(i);
			issueComplete.updateOrder(divOrderX, issueComplete.getDivOrderY()+1);
			
			dashboardIssueCompleteRepository.save(issueComplete);
		}
		for(int i = 0; i < issueRecentList.size(); i++) {
			DashboardIssueRecent issueRecent = issueRecentList.get(i);
			issueRecent.updateOrder(divOrderX, issueRecent.getDivOrderY()+1);
			
			dashboardIssueRecentRepository.save(issueRecent);
		}
		for(int i = 0; i < issueStatisticsList.size(); i++) {
			DashboardIssueStatistics issueStatistics = issueStatisticsList.get(i);
			issueStatistics.updateOrder(divOrderX, issueStatistics.getDivOrderY()+1);
			
			dashboardIssueStatisticsRepository.save(issueStatistics);
		}
		for(int i = 0; i < issueFilterList.size(); i++) {
			DashboardIssueFilter issueFilter = issueFilterList.get(i);
			issueFilter.updateOrder(divOrderX, issueFilter.getDivOrderY()+1);
			
			dashboardIssueFilterRepository.save(issueFilter);
		}
	}
	
	// 대시보트 파이 차트 추가(순서 고려)
	public Integer createPieChart(Integer dashboardIdx) {
		Dashboard dashboard = this.getDashboardByIdx(dashboardIdx);
		DashboardPieChart pieChart = DashboardPieChart.builder()
													  .dashboard(dashboard)
													  .divOrderX(1)
													  .divOrderY(1)
													  .isSave(0)
													  .build();
		this.editOrder(dashboardIdx, 1, 1);
		dashboardPieChartRepository.save(pieChart);
		return pieChart.getIdx();
	}
	
	// 대시보트 나에게 할당 추가
	public Integer createAllot(Integer dashboardIdx) {
		Dashboard dashboard = this.getDashboardByIdx(dashboardIdx);
		DashboardAllot allot = DashboardAllot.builder()
											 .dashboard(dashboard)
											 .divOrderX(1)
											 .divOrderY(1)
											 .isSave(0)
											 .build();
		this.editOrder(dashboardIdx, 1, 1);
		dashboardAllotRepository.save(allot);
		return allot.getIdx();
	}
	
	// 대시보트 만듦 대비 완료된 이슈 차트 추가
	public Integer createIssueComplete(Integer dashboardIdx) {
		Dashboard dashboard = this.getDashboardByIdx(dashboardIdx);
		DashboardIssueComplete issueComplete = DashboardIssueComplete.builder()
																	 .dashboard(dashboard)
																	 .divOrderX(1)
																	 .divOrderY(1)
																	 .isSave(0)
																	 .build();
		this.editOrder(dashboardIdx, 1, 1);
		dashboardIssueCompleteRepository.save(issueComplete);
		return issueComplete.getIdx();
	}
	
	// 대시보트 최근의 생성된 이슈 차트 추가
	public Integer createIssueRecent(Integer dashboardIdx) {
		Dashboard dashboard = this.getDashboardByIdx(dashboardIdx);
		DashboardIssueRecent issueRecent = DashboardIssueRecent.builder()
															   .dashboard(dashboard)
															   .divOrderX(1)
															   .divOrderY(1)
															   .isSave(0)
															   .build();
		this.editOrder(dashboardIdx, 1, 1);
		dashboardIssueRecentRepository.save(issueRecent);
		return issueRecent.getIdx();
	}
	
	// 대시보트 이슈 통계 추가
	public Integer createIssueStatistics(Integer dashboardIdx) {
		Dashboard dashboard = this.getDashboardByIdx(dashboardIdx);
		DashboardIssueStatistics issueStatistics = DashboardIssueStatistics.builder()
																		   .dashboard(dashboard)
																		   .divOrderX(1)
																		   .divOrderY(1)
																		   .isSave(0)
																		   .build();
		this.editOrder(dashboardIdx, 1, 1);
		dashboardIssueStatisticsRepository.save(issueStatistics);
		return issueStatistics.getIdx();
	}
	
	// 대시보트 필터에 따른 이슈 추가
	public Integer createIssueFilter(Integer dashboardIdx) {
		Dashboard dashboard = this.getDashboardByIdx(dashboardIdx);
		DashboardIssueFilter issueFilter = DashboardIssueFilter.builder()
															   .dashboard(dashboard)
															   .divOrderX(1)
															   .divOrderY(1)
															   .isSave(0)
															   .build();
		this.editOrder(dashboardIdx, 1, 1);
		dashboardIssueFilterRepository.save(issueFilter);
		return issueFilter.getIdx();
	}
	
	// 대시보드 파이 차트 업데이트
	public void updatePieChart(Integer idx, Integer projectIdx, Integer dashboardColIdx) {
		Project project = projectService.getProjectByIdx(projectIdx);
		DashboardCol dashboardCol = this.getDashboardColByIdx(dashboardColIdx);
		Optional<DashboardPieChart> opPieChart = dashboardPieChartRepository.findById(idx);
		DashboardPieChart pieChart = null;
		if(!opPieChart.isEmpty()) {
			pieChart = opPieChart.get();
		}
		pieChart.updatePieChart(project, dashboardCol);
		dashboardPieChartRepository.save(pieChart);
	}
	
	// 대시보드 나에게 할당됨 업데이트
	public void updateAllot(Integer idx, Integer rowNum) {
		Optional<DashboardAllot> opAllot = dashboardAllotRepository.findById(idx);
		DashboardAllot allot = null;
		if(!opAllot.isEmpty()) {
			allot = opAllot.get();
		}
		allot.updateAllot(rowNum);
		dashboardAllotRepository.save(allot);
	}
	
	// 대시보드 만듦 대비 완료된 이슈 차트 업데이트
	public void updateIssueComplete(Integer idx, Integer projectIdx, Integer viewDate, String unitPeriod) {
		Project project = projectService.getProjectByIdx(projectIdx);
		Optional<DashboardIssueComplete> opIssueComplete = dashboardIssueCompleteRepository.findById(idx);
		DashboardIssueComplete issueComplete = null;
		
		if(!opIssueComplete.isEmpty()) {
			issueComplete = opIssueComplete.get();
		}
		
		issueComplete.updateIssueComplete(project, viewDate, unitPeriod);
		dashboardIssueCompleteRepository.save(issueComplete);
	}
	
	// 대시보드 최근의 생성된 이슈 차트 업데이트
	public void updateIssueRecent(Integer idx, Integer projectIdx, Integer viewDate, String unitPeriod) {
		Project project = projectService.getProjectByIdx(projectIdx);
		Optional<DashboardIssueRecent> opIssueRecent = dashboardIssueRecentRepository.findById(idx);
		DashboardIssueRecent issueRecent = null;
		
		if(!opIssueRecent.isEmpty()) {
			issueRecent = opIssueRecent.get();
		}
		
		issueRecent.updateIssueRecent(project, viewDate, unitPeriod);
		dashboardIssueRecentRepository.save(issueRecent);
	}
	
	// 대시보드 이슈 통계 업데이트
	public void updateIssueStatistics(Integer idx, Integer projectIdx, Integer dashboardColIdx, Integer rowNum) {
		Project project = projectService.getProjectByIdx(projectIdx);
		DashboardCol dashboardCol = this.getDashboardColByIdx(dashboardColIdx);
		Optional<DashboardIssueStatistics> opIssueStatistics = dashboardIssueStatisticsRepository.findById(idx);
		DashboardIssueStatistics issueStatistics = null;
		
		if(!opIssueStatistics.isEmpty()) {
			issueStatistics = opIssueStatistics.get();
		}
		
		issueStatistics.updateIssueStatistics(project, dashboardCol, rowNum);
		dashboardIssueStatisticsRepository.save(issueStatistics);
	}
	
}
