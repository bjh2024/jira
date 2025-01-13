package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.LikeContentDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.entity.DashboardLikeMembers;
import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.FilterLikeMembers;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.ProjectLikeMembers;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.DashboardLikeMembersRepository;
import com.mysite.jira.repository.DashboardRepository;
import com.mysite.jira.repository.FilterLikeMembersRepository;
import com.mysite.jira.repository.FilterRepository;
import com.mysite.jira.repository.ProjectLikeMembersRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
	
	private final AccountRepository accountRepository;
	
	private final ProjectRepository projectRepository;
	private final ProjectLikeMembersRepository projectLikeMembersRepository;
	
	private final DashboardRepository dashboardRepository;
	private final DashboardLikeMembersRepository dashboardLikeMembersRepository;
	
	private final FilterRepository filterRepository;
	private final FilterLikeMembersRepository filterLikeMembersRepository;
	
	// 별표 표시한 프로젝트, 대시보드, 필터 kdw
	public List<LikeContentDTO> getAllLikeList(Integer accountIdx, Integer jiraIdx){
		List<LikeContentDTO> result = new ArrayList<>();
		List<Map<String, Object>> allLikeList = projectRepository.findLikeMembers(accountIdx, jiraIdx);
		try {
			for(int i = 0; i < allLikeList.size(); i++) {
				String type = allLikeList.get(i).get("type").toString();
				Integer idx = (Integer)allLikeList.get(i).get("idx");
				String name = allLikeList.get(i).get("name").toString();
				String iconFilename = allLikeList.get(i).get("iconFilename").toString();
				String projectKey = allLikeList.get(i).get("key") != null ? allLikeList.get(i).get("key").toString() : "";
				LikeContentDTO dto = LikeContentDTO.builder()
												   .type(type)
												   .idx(idx)
												   .name(name)
												   .iconFilename(iconFilename)
												   .key(projectKey)
												   .build();
				result.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 별표 표시한 프로젝트 kdw
	public List<LikeContentDTO> getProjectLikeList(Integer accountIdx, Integer jiraIdx){
		List<LikeContentDTO> result = new ArrayList<>();
		List<ProjectLikeMembers> projectLikeList = projectLikeMembersRepository.findByAccountIdxAndProject_jiraIdx(accountIdx, jiraIdx);
		
		for(int i = 0; i < projectLikeList.size(); i++) {
			String jiraName = projectLikeList.get(i).getProject().getJira().getName();
			Integer idx = projectLikeList.get(i).getProject().getIdx();
			String projectName = projectLikeList.get(i).getProject().getName();
			String iconFileName = projectLikeList.get(i).getProject().getIconFilename();
			String projectKey = projectLikeList.get(i).getProject().getKey();
			LikeContentDTO dto = LikeContentDTO.builder()
											   .jiraName(jiraName)
											   .idx(idx)
											   .name(projectName)
											   .iconFilename(iconFileName)
											   .key(projectKey)
											   .build();
			result.add(dto);
		}
				
		return result;
	}
	
	// 별표 표시한 대시보드 kdw
	public List<LikeContentDTO> getDashboardLikeList(Integer accountIdx, Integer jiraIdx){
		List<LikeContentDTO> result = new ArrayList<>();
		List<DashboardLikeMembers> dashboardLikeList = dashboardLikeMembersRepository.findByDashboard_jiraIdxAndAccountIdx(jiraIdx, accountIdx);
		
		for(int i = 0; i < dashboardLikeList.size(); i++) {
			Integer idx = dashboardLikeList.get(i).getDashboard().getIdx();
			String dashboardName = dashboardLikeList.get(i).getDashboard().getName();
			String iconFileName = "dashboard_icon.svg";
			
			LikeContentDTO dto = LikeContentDTO.builder()
											 .idx(idx)
											 .name(dashboardName)
											 .iconFilename(iconFileName)
											 .build();
			result.add(dto);
		}
		return result;
	}
		
	// 별표 표시한 필터 kdw
	public List<LikeContentDTO> getFilterLikeList(Integer accountIdx, Integer jiraIdx){
			List<LikeContentDTO> result = new ArrayList<>();
			List<FilterLikeMembers> filterLikeList = filterLikeMembersRepository.findByAccountIdxAndFilter_jiraIdx(accountIdx, jiraIdx);
			for(int i = 0; i < filterLikeList.size(); i++) {
				Integer idx = filterLikeList.get(i).getFilter().getIdx();
				String filterName = filterLikeList.get(i).getFilter().getName();
				String iconFileName = "filter_icon.svg";
				LikeContentDTO dto = LikeContentDTO.builder()
												 .idx(idx)
												 .name(filterName)
												 .iconFilename(iconFileName)
												 .build();
				result.add(dto);
			}
			return result;
	}
	
	// project 즐겨찾기 멤버 update
	public void updateLikeProjectMember(Integer accountIdx, Integer projectIdx, Boolean isLike) {
		ProjectLikeMembers projectLikeMember = null;
		if(isLike) {
			Optional<Account> account = accountRepository.findById(accountIdx);
			Optional<Project> project = projectRepository.findById(projectIdx);
			if(!account.isEmpty() && !project.isEmpty()) {
				projectLikeMember = ProjectLikeMembers.builder()
													  .account(account.get())
													  .project(project.get())
													  .build();
			}
			projectLikeMembersRepository.save(projectLikeMember);
		}else {
			projectLikeMember = projectLikeMembersRepository.findByAccountIdxAndProjectIdx(accountIdx, projectIdx);
			projectLikeMembersRepository.deleteById(projectLikeMember.getIdx());
		}
	}
	
	// dashboard 즐겨찾기 멤버 update
	public void updateLikeDashboardMember(Integer accountIdx, Integer dashboardIdx, Boolean isLike) {
		DashboardLikeMembers dashboardLikeMembers = null;
		if(isLike) {
			Optional<Account> account = accountRepository.findById(accountIdx);
			Optional<Dashboard> dashboard = dashboardRepository.findById(dashboardIdx);
			if(!account.isEmpty() && !dashboard.isEmpty()) {
				dashboardLikeMembers = DashboardLikeMembers.builder()
													  .account(account.get())
													  .dashboard(dashboard.get())
													  .build();
			}
			dashboardLikeMembersRepository.save(dashboardLikeMembers);
		}else {
			dashboardLikeMembers = dashboardLikeMembersRepository.findByAccountIdxAndDashboardIdx(accountIdx, dashboardIdx);
			dashboardLikeMembersRepository.deleteById(dashboardLikeMembers.getIdx());
		}
	}
		
	// filter 즐겨찾기 멤버 update
	public void updateLikeFilterMember(Integer accountIdx, Integer filterIdx, Boolean isLike) {
		FilterLikeMembers filterLikeMembers = null;
		if(isLike) {
			Optional<Account> account = accountRepository.findById(accountIdx);
			Optional<Filter> filter = filterRepository.findById(filterIdx);
			if(!account.isEmpty() && !filter.isEmpty()) {
				filterLikeMembers = FilterLikeMembers.builder()
													 .account(account.get())
													 .filter(filter.get())
													 .build();
			}
			filterLikeMembersRepository.save(filterLikeMembers);
		}else {
			filterLikeMembers = filterLikeMembersRepository.findByAccountIdxAndFilterIdx(accountIdx, filterIdx);
			filterLikeMembersRepository.deleteById(filterLikeMembers.getIdx());
		}
	}
	
}
