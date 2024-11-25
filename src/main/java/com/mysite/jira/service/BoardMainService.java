package com.mysite.jira.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.IssueStatusRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardMainService {
	private final IssueRepository issueRepository;
	private final ProjectRepository projectRepository; 
	private final IssueStatusRepository issueStatusRepository;
	
	// board_main 
	public List<Issue> getIssuesByProjectIdx(Integer idx){
		return this.issueRepository.findIssuesByProjectIdx(idx);
	}
	
	public List<Object[]> getIssueStatusByProjectIdx(Integer idx){
		return this.issueStatusRepository.findGroupByIssueStatusWithJPQL(idx);
	}
	
	// project_header 프로젝트명 불러오기
	public Project getProjectNameById(Integer idx) {
		Optional<Project> projectName = this.projectRepository.findById(idx);
		if(projectName.isPresent()) {
			return projectName.get();
		}else { 
			return null;
		}
	}
}
