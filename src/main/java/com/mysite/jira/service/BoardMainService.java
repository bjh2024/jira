package com.mysite.jira.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueExtends;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.IssueExtendsRepository;
import com.mysite.jira.repository.IssueLabelDataRepository;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.IssueStatusRepository;
import com.mysite.jira.repository.IssueTypeRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardMainService {
	private final IssueRepository issueRepository;
	private final ProjectRepository projectRepository; 
	private final IssueStatusRepository issueStatusRepository;
	private final IssueExtendsRepository issueExtendsRepository;
	private final IssueTypeRepository issueTypeRepository;
	private final IssueLabelDataRepository issueLabelDataRepository;
	
	// project_header 프로젝트명 불러오기
	public Project getProjectNameById(Integer idx) {
		Optional<Project> projectName = this.projectRepository.findById(idx);
		if(projectName.isPresent()) {
			return projectName.get();
		}else { 
			return null;
		}
	}
	
	// board_main 
	public List<Issue> getIssuesByProjectIdx(Integer idx){
		return this.issueRepository.findIssuesByProjectIdx(idx);
	}
	
	public List<Object[]> getIssueStatusByProjectIdx(Integer idx){
		return this.issueStatusRepository.findGroupByIssueStatusWithJPQL(idx);
	}
	
	public List<IssueExtends> getIssueExtendsByProjectIdxAndParentIdx(Integer projectIdx, Integer parentIdx){
		return this.issueExtendsRepository.findByProjectIdxAndParentIdx(projectIdx, parentIdx);
	}
	
	public List<IssueType> getIssueTypesByProjectIdxAndGrade(Integer projectIdx, Integer grade){
		return this.issueTypeRepository.findByProjectIdxAndGradeGreaterThan(projectIdx, grade);
	}
	
	public List<IssueLabelData> getLabelData(){
		return this.issueLabelDataRepository.findAll();
	}
}
