package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.IssueStatusRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final IssueStatusRepository issueStatusRepository;

	public List<Project> getProjectByJiraIdx(Integer jiraIdx) {
		return projectRepository.findByJiraIdx(jiraIdx);
	}

	public List<Object[]> getDistinctStatusAndNameByJiraIdx(Integer jiraIdx) {
		return projectRepository.findDistinctStatusAndNameByJiraIdx(jiraIdx);
	}

	public List<Project> filterProjects(List<String> projectKeys) {
        List<Project> filteredProjects = new ArrayList<>();

        // projectKeys에 포함된 프로젝트들만 필터링
        for (String key : projectKeys) {
            // ProjectRepository에서 각 key에 해당하는 프로젝트를 찾음
            Project project = new Project();
            if (project != null) {
                filteredProjects.add(project);  // 필터링된 프로젝트 리스트에 추가
            }
        }

        return filteredProjects;
    }
}
