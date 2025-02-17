package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.jira.dto.dashboard.AuthTypeDTO;
import com.mysite.jira.dto.dashboard.DashboardListDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.entity.DashboardAllot;
import com.mysite.jira.entity.DashboardAuth;
import com.mysite.jira.entity.DashboardCol;
import com.mysite.jira.entity.DashboardIssueComplete;
import com.mysite.jira.entity.DashboardIssueFilter;
import com.mysite.jira.entity.DashboardIssueRecent;
import com.mysite.jira.entity.DashboardIssueStatistics;
import com.mysite.jira.entity.DashboardItem;
import com.mysite.jira.entity.DashboardPieChart;
import com.mysite.jira.entity.DashboardRecentClicked;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.Team;
import com.mysite.jira.repository.DashboardAllotRepository;
import com.mysite.jira.repository.DashboardAuthRepository;
import com.mysite.jira.repository.DashboardColRepository;
import com.mysite.jira.repository.DashboardIssueCompleteRepository;
import com.mysite.jira.repository.DashboardIssueFilterRepository;
import com.mysite.jira.repository.DashboardIssueRecentRepository;
import com.mysite.jira.repository.DashboardIssueStatisticsRepository;
import com.mysite.jira.repository.DashboardPieChartRepository;
import com.mysite.jira.repository.DashboardRecentClickedRepository;
import com.mysite.jira.repository.DashboardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final DashboardRepository dashboardRepository;
	
	private final DashboardRecentClickedRepository dashboardRecentClickedRepository;
	
	private final DashboardAllotRepository dashboardAllotRepository;
	
	private final DashboardIssueCompleteRepository dashboardIssueCompleteRepository;

	private final DashboardIssueFilterRepository dashboardIssueFilterRepository;
	
	private final DashboardIssueRecentRepository dashboardIssueRecentRepository;
	
	private final DashboardIssueStatisticsRepository dashboardIssueStatisticsRepository;
	
	private final DashboardPieChartRepository dashboardPieChartRepository;
	
	private final DashboardColRepository dashboardColRepository;
	
	private final DashboardAuthRepository dashboardAuthRepository;
	
	private final ProjectService projectService;
	
	private final TeamService teamService;
	
	private final AccountService accountService;
	
	public Dashboard getByJiraIdxAndNameDashboard(Integer jiraIdx, String dashboardName) {
		return dashboardRepository.findByJiraIdxAndName(jiraIdx, dashboardName);
	}
	
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
	
	public DashboardPieChart getDashboardPieChart(Integer idx) {
		Optional<DashboardPieChart> dashboardPieChart = dashboardPieChartRepository.findById(idx);
		if(!dashboardPieChart.isEmpty()) {
			return dashboardPieChart.get();
		}
		return null;
	}
	
	public DashboardAllot getDashboardAllot(Integer idx) {
		Optional<DashboardAllot> dashboardAllot = dashboardAllotRepository.findById(idx);
		if(!dashboardAllot.isEmpty()) {
			return dashboardAllot.get();
		}
		return null;
	}
	
	public DashboardIssueComplete getDashboardIssueComplete(Integer idx) {
		Optional<DashboardIssueComplete> dashboardIssueComplete = dashboardIssueCompleteRepository.findById(idx);
		if(!dashboardIssueComplete.isEmpty()) {
			return dashboardIssueComplete.get();
		}
		return null;
	}
	
	public DashboardIssueRecent getDashboardIssueRecent(Integer idx) {
		Optional<DashboardIssueRecent> dashboardIssueRecent = dashboardIssueRecentRepository.findById(idx);
		if(!dashboardIssueRecent.isEmpty()) {
			return dashboardIssueRecent.get();
		}
		return null;
	}
	
	public DashboardIssueStatistics getDashboardIssueStatistics(Integer idx) {
		Optional<DashboardIssueStatistics> dashboardIssueStatistics = dashboardIssueStatisticsRepository.findById(idx);
		if(!dashboardIssueStatistics.isEmpty()) {
			return dashboardIssueStatistics.get();
		}
		return null;
	}
	
	public void addDashboardRecentClicked(Jira jira, Account account, Dashboard dashboard) {
		DashboardRecentClicked dashboardRecentClicked = dashboardRecentClickedRepository.findByDashboard_IdxAndAccount_Idx(dashboard.getIdx(), account.getIdx());
		if(dashboardRecentClicked != null) {
			dashboardRecentClicked.updateDate();
		}else {
			dashboardRecentClicked = DashboardRecentClicked.builder()
														   .dashboard(dashboard)
														   .jira(jira)
														   .account(account)
														   .build();
		}
		dashboardRecentClickedRepository.save(dashboardRecentClicked);
	}
	
	@Transactional
	public Integer createDashboard(String name, String explain, Jira jira, Account account, List<AuthTypeDTO> authItems) {
		// 대시보드 추가
		Dashboard dashboard = Dashboard.builder()
									   .name(name)
									   .explain(explain)
									   .jira(jira)
									   .account(account)
									   .build();
		dashboardRepository.save(dashboard);
		
		// 대시보드 최근 방문 추가
		this.addDashboardRecentClicked(jira, account, dashboard);
		
		// 대시보드 보기, 편집 권한 추가
		List<DashboardAuth> authList = new ArrayList<>();
		for(int i = 0; i < authItems.size(); i++) {
			String itemType = authItems.get(i).getItemType();
			Integer itemIdx = authItems.get(i).getIdx();
			Integer type = authItems.get(i).getAuthType();
			Integer projectRole = authItems.get(i).getRole();
			
			Project project = null;
			Team team = null;
			Account authAccount = null;
			
			if(itemType.equals("비공개")) continue;
			if(itemType.equals("프로젝트")) {
				project = projectService.getProjectByIdx(itemIdx);
			}else if(itemType.equals("그룹")) {
				team = teamService.getTeamByIdx(itemIdx);
			}else if(itemType.equals("사용자")) {
				authAccount = accountService.getAccountByIdx(itemIdx);
			}
			
			DashboardAuth dashboardAuth = DashboardAuth.builder()
													   .dashboard(dashboard)
													   .project(project)
													   .type(type)
													   .projectRole(projectRole)
													   .team(team)
													   .account(authAccount)
													   .build();
			authList.add(dashboardAuth);
		}
		dashboardAuthRepository.saveAll(authList);
		
		int idx = dashboard.getIdx();
		return idx;
	}
	
	// 대시보드 삭제
	public void deleteDashboard(Integer idx) {
		dashboardRepository.deleteById(idx);
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
	public void editOrder(Integer dashboardIdx, Integer divOrderX, Integer divOrderY, int step) {
		
		List<DashboardPieChart> pieChartList = dashboardPieChartRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		List<DashboardAllot> allotList = dashboardAllotRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		List<DashboardIssueComplete> issueCompleteList = dashboardIssueCompleteRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		List<DashboardIssueRecent> issueRecentList = dashboardIssueRecentRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		List<DashboardIssueStatistics> issueStatisticsList = dashboardIssueStatisticsRepository.findByDashboardIdxAndDivOrderXAndDivOrderYGreaterThanEqual(dashboardIdx, divOrderX, divOrderY);
		
		for(int i = 0; i < pieChartList.size(); i++) {
			DashboardPieChart pieChart = pieChartList.get(i);
			pieChart.updateOrder(divOrderX, pieChart.getDivOrderY()+step);
			
			dashboardPieChartRepository.save(pieChart);
		}
		for(int i = 0; i < allotList.size(); i++) {
			DashboardAllot allot = allotList.get(i);
			allot.updateOrder(divOrderX, allot.getDivOrderY()+step);
			
			dashboardAllotRepository.save(allot);
		}
		for(int i = 0; i < issueCompleteList.size(); i++) {
			DashboardIssueComplete issueComplete = issueCompleteList.get(i);
			issueComplete.updateOrder(divOrderX, issueComplete.getDivOrderY()+step);
			
			dashboardIssueCompleteRepository.save(issueComplete);
		}
		for(int i = 0; i < issueRecentList.size(); i++) {
			DashboardIssueRecent issueRecent = issueRecentList.get(i);
			issueRecent.updateOrder(divOrderX, issueRecent.getDivOrderY()+step);
			
			dashboardIssueRecentRepository.save(issueRecent);
		}
		for(int i = 0; i < issueStatisticsList.size(); i++) {
			DashboardIssueStatistics issueStatistics = issueStatisticsList.get(i);
			issueStatistics.updateOrder(divOrderX, issueStatistics.getDivOrderY()+step);
			
			dashboardIssueStatisticsRepository.save(issueStatistics);
		}
	}
	
	// 대시보드 요소 추상화
	public void updateDashboardItemOtherOrder(DashboardItem item, int orderX, int orderY) {
		int dashboardIdx = 0;
		int prevOrderX = 0;
		int prevOrderY = 0;
		
		dashboardIdx = item.getDashboard().getIdx();
		this.editOrder(dashboardIdx, orderX, orderY, 1);
		
		prevOrderX = item.getDivOrderX();
		prevOrderY = item.getDivOrderY();
		this.editOrder(dashboardIdx, prevOrderX, prevOrderY, -1);
	}
	
	public void updateDashboardItemOrder(String type, Integer orderX, Integer orderY, Integer dashboardItemIdx) {
		if(type.equals("pieChart")) {
			DashboardPieChart pieChart =  this.getDashboardPieChart(dashboardItemIdx);
			this.updateDashboardItemOtherOrder(pieChart, orderX, orderY);
			
			pieChart.updateOrder(orderX, orderY);
			dashboardPieChartRepository.save(pieChart);
		}else if(type.equals("allot")) {
			DashboardAllot allot =  this.getDashboardAllot(dashboardItemIdx);
			this.updateDashboardItemOtherOrder(allot, orderX, orderY);
			
			allot.updateOrder(orderX, orderY);
			dashboardAllotRepository.save(allot);
		}else if(type.equals("issueComplete")) {
			DashboardIssueComplete issueComplete =  this.getDashboardIssueComplete(dashboardItemIdx);
			this.updateDashboardItemOtherOrder(issueComplete, orderX, orderY);
			
			issueComplete.updateOrder(orderX, orderY);
			dashboardIssueCompleteRepository.save(issueComplete);
		}else if(type.equals("issueRecent")) {
			DashboardIssueRecent issueRecent =  this.getDashboardIssueRecent(dashboardItemIdx);
			this.updateDashboardItemOtherOrder(issueRecent, orderX, orderY);
			
			issueRecent.updateOrder(orderX, orderY);
			dashboardIssueRecentRepository.save(issueRecent);
		}else if(type.equals("issueStatistics")) {
			DashboardIssueStatistics issueStatistics =  this.getDashboardIssueStatistics(dashboardItemIdx);
			this.updateDashboardItemOtherOrder(issueStatistics, orderX, orderY);
			
			issueStatistics.updateOrder(orderX, orderY);
			dashboardIssueStatisticsRepository.save(issueStatistics);
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
		this.editOrder(dashboardIdx, 1, 1, 1);
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
		this.editOrder(dashboardIdx, 1, 1, 1);
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
		this.editOrder(dashboardIdx, 1, 1, 1);
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
		this.editOrder(dashboardIdx, 1, 1, 1);
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
		this.editOrder(dashboardIdx, 1, 1, 1);
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
		this.editOrder(dashboardIdx, 1, 1, 1);
		dashboardIssueFilterRepository.save(issueFilter);
		return issueFilter.getIdx();
	}
	
	// 대시보드 파이 차트 업데이트
	public void updatePieChart(Integer idx, Integer projectIdx, Integer dashboardColIdx) {
		Project project = projectService.getProjectByIdx(projectIdx);
		DashboardCol dashboardCol = this.getDashboardColByIdx(dashboardColIdx);
		DashboardPieChart pieChart = this.getDashboardPieChart(idx);

		pieChart.updatePieChart(project, dashboardCol);
		dashboardPieChartRepository.save(pieChart);
	}
	
	// 대시보드 나에게 할당됨 업데이트
	public void updateAllot(Integer idx, Integer rowNum) {
		DashboardAllot allot = this.getDashboardAllot(idx);
		
		allot.updateAllot(rowNum);
		dashboardAllotRepository.save(allot);
	}
	
	// 대시보드 만듦 대비 완료된 이슈 차트 업데이트
	public void updateIssueComplete(Integer idx, Integer projectIdx, Integer viewDate, String unitPeriod) {
		Project project = projectService.getProjectByIdx(projectIdx);
		DashboardIssueComplete issueComplete = this.getDashboardIssueComplete(idx);
		
		issueComplete.updateIssueComplete(project, viewDate, unitPeriod);
		dashboardIssueCompleteRepository.save(issueComplete);
	}
	
	// 대시보드 최근의 생성된 이슈 차트 업데이트
	public void updateIssueRecent(Integer idx, Integer projectIdx, Integer viewDate, String unitPeriod) {
		Project project = projectService.getProjectByIdx(projectIdx);
		DashboardIssueRecent issueRecent = this.getDashboardIssueRecent(idx);
		
		issueRecent.updateIssueRecent(project, viewDate, unitPeriod);
		dashboardIssueRecentRepository.save(issueRecent);
	}
	
	// 대시보드 이슈 통계 업데이트
	public void updateIssueStatistics(Integer idx, Integer projectIdx, Integer dashboardColIdx, Integer rowNum) {
		Project project = projectService.getProjectByIdx(projectIdx);
		DashboardCol dashboardCol = this.getDashboardColByIdx(dashboardColIdx);
		DashboardIssueStatistics issueStatistics = this.getDashboardIssueStatistics(idx);
		
		issueStatistics.updateIssueStatistics(project, dashboardCol, rowNum);
		dashboardIssueStatisticsRepository.save(issueStatistics);
	}
	
	// 대시보드 파이 차트 삭제
	public void deletePieChart(Integer idx) {
		DashboardPieChart pieChart = this.getDashboardPieChart(idx);
		int dashboardIdx = pieChart.getDashboard().getIdx();
		int orderX = pieChart.getDivOrderX();
		int ordery = pieChart.getDivOrderY();
		
		this.editOrder(dashboardIdx, orderX, ordery, -1);
		
		dashboardPieChartRepository.deleteById(idx);
	}
	
	// 대시보드 나에게 할당됨 삭제
	public void deleteAllot(Integer idx) {
		DashboardAllot allot = this.getDashboardAllot(idx);
		int dashboardIdx = allot.getDashboard().getIdx();
		int orderX = allot.getDivOrderX();
		int ordery = allot.getDivOrderY();
		
		this.editOrder(dashboardIdx, orderX, ordery, -1);
		
		dashboardAllotRepository.deleteById(idx);
	}
	
	// 대시보드 만듦 대비 최근 생성 차트 삭제
	public void deleteIssueComplete(Integer idx) {
		DashboardIssueComplete issueComplete = this.getDashboardIssueComplete(idx);
		int dashboardIdx = issueComplete.getDashboard().getIdx();
		int orderX = issueComplete.getDivOrderX();
		int ordery = issueComplete.getDivOrderY();
		
		this.editOrder(dashboardIdx, orderX, ordery, -1);
		
		dashboardIssueCompleteRepository.deleteById(idx);
	}
	
	// 대시보드 최근에 생성된 이슈 차트 삭제
	public void deleteIssueRecent(Integer idx) {
		DashboardIssueRecent issueRecent = this.getDashboardIssueRecent(idx);
		int dashboardIdx = issueRecent.getDashboard().getIdx();
		int orderX = issueRecent.getDivOrderX();
		int ordery = issueRecent.getDivOrderY();
		
		this.editOrder(dashboardIdx, orderX, ordery, -1);
		
		dashboardIssueRecentRepository.deleteById(idx);
	}
	
	// 대시보드 이슈 통계 삭제
	public void deleteIssueStatistics(Integer idx) {
		DashboardIssueStatistics issueStatistics = this.getDashboardIssueStatistics(idx);
		int dashboardIdx = issueStatistics.getDashboard().getIdx();
		int orderX = issueStatistics.getDivOrderX();
		int ordery = issueStatistics.getDivOrderY();
		
		this.editOrder(dashboardIdx, orderX, ordery, -1);
		
		dashboardIssueStatisticsRepository.deleteById(idx);
	}
	
	// 대시보드 필터링된 이슈 삭제
	public void deleteIssueFilter(Integer idx) {
		dashboardIssueFilterRepository.deleteById(idx);
	}
	
}
