package com.mysite.jira.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.ChartDTO;
import com.mysite.jira.dto.dashboard.AllotDTO;
import com.mysite.jira.dto.dashboard.AuthTypeDTO;
import com.mysite.jira.dto.dashboard.IssueCompleteChartDTO;
import com.mysite.jira.dto.dashboard.RequestAllotDTO;
import com.mysite.jira.dto.dashboard.RequestCompleteRecentDTO;
import com.mysite.jira.dto.dashboard.RequestDashboardCreateDTO;
import com.mysite.jira.dto.dashboard.RequestDashboardItemOrderDTO;
import com.mysite.jira.dto.dashboard.RequestPieChartDTO;
import com.mysite.jira.dto.dashboard.RequestStatisticsDTO;
import com.mysite.jira.dto.dashboard.create.AccountListDTO;
import com.mysite.jira.dto.dashboard.create.ProjectListDTO;
import com.mysite.jira.dto.dashboard.create.TeamListDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.DashboardService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.TeamService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardAPIController {

	private final DashboardService dashboardService;
	
	private final TeamService teamService;
	
	private final AccountService accountService;
	
	private final JiraService jiraService;
	
	private final IssueService issueService;
	
	private final ProjectService projectService;
	
	private final HttpSession session;
	
	@PostMapping("create")
	public Integer dashboardCreate(@RequestBody RequestDashboardCreateDTO requestDashboardCreateDTO, Principal principal) {
		
		String name = requestDashboardCreateDTO.getName();
		String explain = requestDashboardCreateDTO.getExplain();
		
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Jira jira = jiraService.getByIdx(jiraIdx);
		Account account = accountService.getAccountByEmail(principal.getName());
		List<AuthTypeDTO> authItems = requestDashboardCreateDTO.getAuthItems();
		
		return dashboardService.createDashboard(name, explain, jira, account, authItems);
	}
	
	@GetMapping("create/project/list")
	public List<ProjectListDTO> getProjectList(){
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		return projectService.getByJiraIdxProjectListDTO(jiraIdx);
	}
	
	@GetMapping("create/team/list")
	public List<TeamListDTO> getTeamList(){
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		return teamService.getTeamListByJiraIdxDashboard(jiraIdx);
	}
	
	@GetMapping("create/account/list")
	public List<AccountListDTO> getAccountList(){
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		return accountService.getAccountListDashboard(jiraIdx);
	}
	
	@PostMapping("delete")
	public void dashboardDelete(@RequestBody Integer idx) {
		dashboardService.deleteDashboard(idx);
	}
	
	@GetMapping("duplication/name")
	public Integer getDuplicationDashboardName(@RequestParam("dashboardName") String dashboardName) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Jira jira = jiraService.getByIdx(jiraIdx);
		if(dashboardService.getByJiraIdxAndNameDashboard(jira.getIdx(), dashboardName) == null) {
			return 0;
		}
		return 1;
	}
	
	@GetMapping("allot")
	public List<AllotDTO> getAllot(@RequestParam("pageNum") Integer pageNum,
								   @RequestParam("col") Integer col,
								   Principal principal) {
		Account account = accountService.getAccountByEmail(principal.getName());
		
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Integer accountIdx = account.getIdx();
		
		List<AllotDTO> result = issueService.getManagerByIssueStatusIn(jiraIdx, accountIdx, pageNum, col);
		return result;
	}

	@GetMapping("issue_info_chart/issue_type")
	public List<ChartDTO> getIssueTypePieChart(@RequestParam("projectIdx") Integer projectIdx){
		return issueService.getTypeChartDTO(projectIdx);
	}
	
	@GetMapping("issue_info_chart/reporter")
	public List<ChartDTO> getReporterPieChart(@RequestParam("projectIdx") Integer projectIdx){
		return issueService.getReporterChartDTO(projectIdx);
	}
	
	@GetMapping("issue_info_chart/manager")
	public List<ChartDTO> getManagerPieChart(@RequestParam("projectIdx") Integer projectIdx){
		return issueService.getManagerChartDTO(projectIdx);
	}
	
	@GetMapping("issue_info_chart/issue_status")
	public List<ChartDTO> getIssueStatusPieChart(@RequestParam("projectIdx") Integer projectIdx){
		return issueService.getStatusChartDTO(projectIdx);
	}
	
	@GetMapping("issue_info_chart/label")
	public List<ChartDTO> getLabelPieChart(@RequestParam("projectIdx") Integer projectIdx){
		return issueService.getLabelChartDTO(projectIdx);
	}
	
	@GetMapping("issue_info_chart/priority")
	public List<ChartDTO> getPriorityPieChart(@RequestParam("projectIdx") Integer projectIdx){
		return issueService.getPriorityChartDTO(projectIdx);
	}
	
	@GetMapping("issue_complete/create")
	public List<IssueCompleteChartDTO> getCreateIssueCompleteChart(@RequestParam("projectIdx") Integer projectIdx,
													  			   @RequestParam("startDate") LocalDateTime startDate){
		return issueService.getIssueCountBetweenCreateDate(projectIdx, startDate);
	}
	
	@GetMapping("issue_complete/finish")
	public List<IssueCompleteChartDTO> getFinishIssueCompleteChart(@RequestParam("projectIdx") Integer projectIdx,
													  			   @RequestParam("startDate") LocalDateTime startDate){
		return issueService.getIssueCountBetweenFinishDate(projectIdx, startDate);
	}
	
	@GetMapping("issue_recent/begin")
	public List<IssueCompleteChartDTO> getNotCompleteIssueRecentChart(@RequestParam("projectIdx") Integer projectIdx,
													  			   @RequestParam("startDate") LocalDateTime startDate){
		return issueService.getIssueNotCompleteCountBetweenCreateDate(projectIdx, startDate);
	}
	
	@GetMapping("issue_recent/finish")
	public List<IssueCompleteChartDTO> getCompleteIssueRecentChart(@RequestParam("projectIdx") Integer projectIdx,
													  			      @RequestParam("startDate") LocalDateTime startDate){
		return issueService.getIssueCompleteCountBetweenCreateDate(projectIdx, startDate);
	}
	
	@PostMapping("create/pie_chart")
	public Integer createDashboardPieChart(@RequestBody Integer dashboardIdx) {
		return dashboardService.createPieChart(dashboardIdx);
	}
	
	@PostMapping("create/allot")
	public Integer createDashboardAllot(@RequestBody Integer dashboardIdx) {
		return dashboardService.createAllot(dashboardIdx);
	}
	
	@PostMapping("create/issue_complete")
	public Integer createDashboardIssueComplete(@RequestBody Integer dashboardIdx) {
		return dashboardService.createIssueComplete(dashboardIdx);
	}
	
	@PostMapping("create/issue_recent")
	public Integer createDashboardIssueRecent(@RequestBody Integer dashboardIdx) {
		return dashboardService.createIssueRecent(dashboardIdx);
	}
	
	@PostMapping("create/issue_statistics")
	public Integer createDashboardIssueStatistics(@RequestBody Integer dashboardIdx) {
		return dashboardService.createIssueStatistics(dashboardIdx);
	}
	
	@PostMapping("create/issue_filter")
	public Integer createDashboardIssueFilter(@RequestBody Integer dashboardIdx) {
		return dashboardService.createIssueFilter(dashboardIdx);
	}
	
	@PostMapping("update/pie_chart")
	public void updateDashboardPieChart(@RequestBody RequestPieChartDTO requestPieChartDTO) {
		Integer idx = requestPieChartDTO.getIdx();
		Integer projectIdx = requestPieChartDTO.getProjectIdx();
		Integer dashboardColIdx = requestPieChartDTO.getDashboardColIdx();
		
		dashboardService.updatePieChart(idx, projectIdx, dashboardColIdx);
	}
	
	@PostMapping("update/allot")
	public void updateDashboardAllot(@RequestBody RequestAllotDTO requestAllotDTO) {
		Integer idx = requestAllotDTO.getIdx();
		Integer rowNum = requestAllotDTO.getRowNum();
		
		dashboardService.updateAllot(idx, rowNum);
	}
	
	@PostMapping("update/issue_complete")
	public void updateDashboardIssueComplete(@RequestBody RequestCompleteRecentDTO requestCompleteRecentDTO) {
		Integer idx = requestCompleteRecentDTO.getIdx();
		Integer projectIdx = requestCompleteRecentDTO.getProjectIdx();
		Integer viewDate = requestCompleteRecentDTO.getViewDate();
		String unitPeriod = requestCompleteRecentDTO.getUnitPeriod();
		
		dashboardService.updateIssueComplete(idx, projectIdx, viewDate, unitPeriod);
	}
	
	@PostMapping("update/issue_recent")
	public void updateDashboardIssueRecent(@RequestBody RequestCompleteRecentDTO requestCompleteRecentDTO) {
		Integer idx = requestCompleteRecentDTO.getIdx();
		Integer projectIdx = requestCompleteRecentDTO.getProjectIdx();
		Integer viewDate = requestCompleteRecentDTO.getViewDate();
		String unitPeriod = requestCompleteRecentDTO.getUnitPeriod();
		
		dashboardService.updateIssueRecent(idx, projectIdx, viewDate, unitPeriod);
	}
	
	@PostMapping("update/issue_statistics")
	public void updateDashboardIssueStatistics(@RequestBody RequestStatisticsDTO requestStatisticsDTO) {
		Integer idx = requestStatisticsDTO.getIdx();
		Integer projectIdx = requestStatisticsDTO.getProjectIdx();
		Integer dashboardColIdx = requestStatisticsDTO.getDashboardColIdx();
		Integer rowNum = requestStatisticsDTO.getRowNum();
		dashboardService.updateIssueStatistics(idx, projectIdx, dashboardColIdx, rowNum);
	}
	
	@PostMapping("update/order")
	public void updateDashboardItemOrder(@RequestBody RequestDashboardItemOrderDTO requestDashboardItemOrderDTO) {
		String type = requestDashboardItemOrderDTO.getType();
		Integer orderX = requestDashboardItemOrderDTO.getOrderX();
		Integer orderY = requestDashboardItemOrderDTO.getOrderY();
		Integer dashboardItemIdx = requestDashboardItemOrderDTO.getDashboardItemIdx();
		
		dashboardService.updateDashboardItemOrder(type, orderX, orderY, dashboardItemIdx);
	}
	
	@PostMapping("update/issue_filter")
	public void updateDashboardIssueFilter(@RequestBody Integer idx) {
	}
	
	@PostMapping("delete/pie_chart")
	public void deleteDashboardPieChart(@RequestBody Integer idx) {
		dashboardService.deletePieChart(idx);
	}
	
	@PostMapping("delete/allot")
	public void deleteDashboardAllot(@RequestBody Integer idx) {
		dashboardService.deleteAllot(idx);
	}
	
	@PostMapping("delete/issue_complete")
	public void deleteDashboardIssueComplete(@RequestBody Integer idx) {
		dashboardService.deleteIssueComplete(idx);
	}
	
	@PostMapping("delete/issue_recent")
	public void deleteDashboardIssueRecent(@RequestBody Integer idx) {
		dashboardService.deleteIssueRecent(idx);
	}
	
	@PostMapping("delete/issue_statistics")
	public void deleteDashboardIssueStatistics(@RequestBody Integer idx) {
		dashboardService.deleteIssueStatistics(idx);
	}
	
	@PostMapping("delete/issue_filter")
	public void deleteDashboardIssueFilter(@RequestBody Integer idx) {
		dashboardService.deleteIssueFilter(idx);
	}
	
}
