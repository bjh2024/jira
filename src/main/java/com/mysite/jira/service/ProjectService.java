package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.mywork.RecentProjectDTO;
import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.IssueStatusRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final IssueStatusRepository issueStatusRepository;

	
	private final UtilityService utilityService;
	
	public List<Project> getProjectByJiraIdx(Integer jiraIdx){
		return projectRepository.findByJiraIdx(jiraIdx);
	}

	public List<Object[]> getDistinctStatusAndNameByJiraIdx(Integer jiraIdx) {
		return projectRepository.findDistinctStatusAndNameByJiraIdx(jiraIdx);
	}

	
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
}
