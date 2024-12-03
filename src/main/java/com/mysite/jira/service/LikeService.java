package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.LikeContentDTO;
import com.mysite.jira.entity.DashboardLikeMembers;
import com.mysite.jira.entity.FilterLikeMembers;
import com.mysite.jira.entity.ProjectLikeMembers;
import com.mysite.jira.repository.DashboardLikeMembersRepository;
import com.mysite.jira.repository.FilterLikeMembersRepository;
import com.mysite.jira.repository.ProjectLikeMembersRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
	
	private final ProjectRepository projectRepository;
	
	private final ProjectLikeMembersRepository projectLikeMembersRepository;
	
	private final DashboardLikeMembersRepository dashboardLikeMembersRepository;
	
	private final FilterLikeMembersRepository filterLikeMembersRepository;
	
	// 별표 표시한 프로젝트, 대시보드, 필터 kdw
	public List<LikeContentDTO> getAllLikeList(Integer accountIdx, Integer jiraIdx){
		List<LikeContentDTO> result = new ArrayList<>();
		List<Map<String, Object>> allLikeList = projectRepository.findLikeMembers(accountIdx, jiraIdx);
		try {
			for(int i = 0; i < allLikeList.size(); i++) {
				String name = "";
				String iconFilename = "";
				String projectKey = "";
				if(allLikeList.get(i).get("name") != null) {
					name = allLikeList.get(i).get("name").toString();
				}
				if(allLikeList.get(i).get("iconFilename") != null) {
					iconFilename = allLikeList.get(i).get("iconFilename").toString();
				}
				if(allLikeList.get(i).get("projectKey") != null) {
					projectKey = allLikeList.get(i).get("projectKey").toString();
				}
				LikeContentDTO dto = LikeContentDTO.builder()
						   .name(name)
						   .iconFilename(iconFilename)
						   .projectKey(projectKey)
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
		List<ProjectLikeMembers> projectLikeList = projectLikeMembersRepository.findByProject_jiraIdxAndAccountIdx(accountIdx, jiraIdx);
		
		for(int i = 0; i < projectLikeList.size(); i++) {
			String projectName = projectLikeList.get(i).getProject().getName();
			String iconFileName = projectLikeList.get(i).getProject().getIconFilename();
			
			LikeContentDTO dto = LikeContentDTO.builder()
											 .name(projectName)
											 .iconFilename(iconFileName)
											 .build();
			result.add(dto);
		}
				
		return result;
	}
	
	// 별표 표시한 대시보드 kdw
	public List<LikeContentDTO> getDashboardLikeList(Integer accountIdx, Integer jiraIdx){
		List<LikeContentDTO> result = new ArrayList<>();
		List<DashboardLikeMembers> dashboardLikeList = dashboardLikeMembersRepository.findByDashboard_jiraIdxAndAccountIdx(accountIdx, jiraIdx);
		
		for(int i = 0; i < dashboardLikeList.size(); i++) {
			String projectName = dashboardLikeList.get(i).getDashboard().getName();
			String iconFileName = "dashboard_icon.svg";
			
			LikeContentDTO dto = LikeContentDTO.builder()
											 .name(projectName)
											 .iconFilename(iconFileName)
											 .build();
			result.add(dto);
		}
		return result;
	}
		
	// 별표 표시한 필터 kdw
	public List<LikeContentDTO> getFilterLikeList(Integer accountIdx, Integer jiraIdx){
			List<LikeContentDTO> result = new ArrayList<>();
			List<FilterLikeMembers> filterLikeList = filterLikeMembersRepository.findByFilter_jiraIdxAndAccountIdx(accountIdx, jiraIdx);

			for(int i = 0; i < filterLikeList.size(); i++) {
				String projectName = filterLikeList.get(i).getFilter().getName();
				String iconFileName = "filter_icon.svg";
				LikeContentDTO dto = LikeContentDTO.builder()
												 .name(projectName)
												 .iconFilename(iconFileName)
												 .build();
				result.add(dto);
			}
					
			return result;
		}
}
