package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysite.jira.dto.mywork.RecentProjectDTO;
import com.mysite.jira.dto.project.create.ProjectListIsLikeDTO;
import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	
	private final UtilityService utilityService;
	
	public List<Project> getProjectByJiraIdx(Integer jiraIdx) {
		return projectRepository.findByJiraIdx(jiraIdx);
	}
	// kdw 최근 프로젝트
	public List<RecentProjectDTO> getProjectList(Integer accountIdx, Integer jiraIdx){
		List<RecentProjectDTO> result = new ArrayList<>();
		try {
			List<Map<String, Object>> projectList = projectRepository.findProjectIssueCounts(accountIdx, jiraIdx);
			for(int i = 0; i < projectList.size(); i++) {
				String name = projectList.get(i).get("name").toString();
				String iconFilename = projectList.get(i).get("iconFilename").toString();
				String color = projectList.get(i).get("color").toString();
				Integer issueCount = utilityService.isBigDecimal(projectList.get(i).get("issueCount")).intValue();
				RecentProjectDTO dto = RecentProjectDTO.builder()
											   .name(name)
											   .iconFilename(iconFilename)
											   .color(color)
											   .issueCount(issueCount)
											   .build();
				result.add(dto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	// kdw 프로젝트 리스트(project/create)
	public List<ProjectListIsLikeDTO> getProjectListIsLike(Integer accountIdx, Integer jiraIdx, int page){
		Pageable pageable = PageRequest.of(page, 10);
		Page<Map<String, Object>> projectPage = projectRepository.findByProjectListIsLike(accountIdx, jiraIdx, pageable);
		List<ProjectListIsLikeDTO> result = new ArrayList<>();
		
		for(Map<String, Object> project : projectPage.getContent()) {
			String projectName = project.get("projectName").toString();
			String projectIconFilename = project.get("projectIconFilename").toString();
			String projectKey = project.get("projectKey").toString();
			String accountName = project.get("accountName").toString();
			String accountIconFilename = project.get("accountIconFilename").toString();
			boolean isLike = project.get("isLike").toString().equals("true") ? true : false;
			
			ProjectListIsLikeDTO dto = ProjectListIsLikeDTO.builder()
														   .projectName(projectName)
														   .projectIconFilename(projectIconFilename)
														   .projectKey(projectKey)
														   .accountName(accountName)
														   .accountIconFilename(accountIconFilename)
														   .isLike(isLike)
														   .build();
			System.out.println("프로젝트 이름" + projectName);
			System.out.println("프로젝트아이콘 이름" + projectIconFilename);
			System.out.println("프로젝트 키" + projectKey);
			System.out.println("유저 이름" + accountName);
			System.out.println("유저아이콘 이름" + accountIconFilename);
			System.out.println("즐겨찾기 유무" + isLike);
			result.add(dto);
		}
		return result;
	}
	
	public List<Object[]> getDistinctStatusAndNameByJiraIdx(Integer jiraIdx) {
		return projectRepository.findDistinctStatusAndNameByJiraIdx(jiraIdx);
	}

}
