package com.mysite.jira.service;

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

}
