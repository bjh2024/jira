package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.dashboard.DashboardListDTO;
import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.entity.DashboardAllot;
import com.mysite.jira.entity.DashboardIssueComplete;
import com.mysite.jira.entity.DashboardIssueFilter;
import com.mysite.jira.entity.DashboardIssueRecent;
import com.mysite.jira.entity.DashboardIssueStatistics;
import com.mysite.jira.entity.DashboardPieChart;
import com.mysite.jira.repository.DashboardAllotRepository;
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
	
	public Dashboard getDashboard(Integer dashboardIdx) {
		Optional<Dashboard> dashboard = dashboardRepository.findById(dashboardIdx);
		if(!dashboard.isEmpty())
			return dashboard.get();
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
	
}
