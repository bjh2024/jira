package com.mysite.jira.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.IssueTypeRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardMainService {
	private final IssueRepository issueRepository;
	private final IssueTypeRepository issueTypeRepository;
	private final ProjectRepository projectRepository; 
	
	
	public List<Issue> getIssuesByProjectIdx(Integer idx){
		return this.issueRepository.findIssuesByProjectIdx(idx);
	}
	
	public IssueType getIssueTypeById(Integer idx){
		Optional<IssueType> issueType = this.issueTypeRepository.findById(idx);
		if(issueType.isPresent()) {
			return issueType.get();
		}else {
			return null;
		}
	}
	
	public Project getProjectNameById(Integer idx) {
		Optional<Project> projectName = this.projectRepository.findById(idx);
		if(projectName.isPresent()) {
			return projectName.get();
		}else {
			return null;
		}
	}
}
