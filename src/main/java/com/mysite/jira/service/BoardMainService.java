package com.mysite.jira.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueExtends;
import com.mysite.jira.entity.IssueFile;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.IssueReply;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.ProjectMembers;
import com.mysite.jira.entity.Team;
import com.mysite.jira.repository.IssueExtendsRepository;
import com.mysite.jira.repository.IssueFileRepository;
import com.mysite.jira.repository.IssueLabelDataRepository;
import com.mysite.jira.repository.IssuePriorityRepository;
import com.mysite.jira.repository.IssueReplyRepository;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.IssueStatusRepository;
import com.mysite.jira.repository.IssueTypeRepository;
import com.mysite.jira.repository.ProjectMembersRepository;
import com.mysite.jira.repository.ProjectRepository;
import com.mysite.jira.repository.TeamRepository;

import jakarta.transaction.Transactional;
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
	private final IssueReplyRepository issueReplyRepository;
	private final IssuePriorityRepository issuePriorityRepository;
	private final TeamRepository teamRepository;
	private final IssueFileRepository issueFileRepository;
	private final ProjectMembersRepository projectMembersRepository;
	
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
	
	public Issue getIssueByIdx(Integer idx){
		Optional<Issue> issueDetail = this.issueRepository.findById(idx);
		if(issueDetail.isPresent()) {
			return issueDetail.get();
		}
		return null;
	}
	
	public List<Object[]> getIssueStatusByProjectIdx(Integer idx){
		return this.issueStatusRepository.findGroupByIssueStatusWithJPQL(idx);
	}
	
	public IssueStatus getOnceIssueStatus(Integer idx) {
		Optional<IssueStatus> issueStatus = this.issueStatusRepository.findById(idx);
		if(issueStatus.isPresent()) {
			return issueStatus.get();
		}
		
		return null;
	}
	
	public List<IssueStatus> getIssueStatusByProjectIdxOrderByStatusAsc(Integer idx){
		return this.issueStatusRepository.findAllByProjectIdxOrderByStatusAsc(idx);
	}
	
	public List<IssueExtends> getIssueExtendsByProjectIdx(Integer projectIdx){
		return this.issueExtendsRepository.findAllByProjectIdx(projectIdx);
	}
	
	public List<IssueType> getIssueTypesByProjectIdxAndGrade(Integer projectIdx, Integer grade){
		return this.issueTypeRepository.findByProjectIdxAndGradeGreaterThan(projectIdx, grade);
	}
	
	public List<IssueLabelData> getLabelData(){
		return this.issueLabelDataRepository.findAll();
	}
	
	public List<IssueLabelData> getAlterLabelData(Integer[] idx){
		return this.issueLabelDataRepository.findDistinctByIssueLabelIdxNotIn(idx);
	}
	
	public List<IssueReply> getIssueReply(){
		return this.issueReplyRepository.findAll();
	}
	
	public List<IssuePriority> getIssuePriority(){
		return this.issuePriorityRepository.findAllByOrderByIdxDesc();
	}
	
	public List<Team> getTeamList(){
		return this.teamRepository.findAll();
	}
	
	public List<IssueFile> getFiles(){
		return this.issueFileRepository.findAll();
	}
	
	public List<ProjectMembers> getPrjMembers(Integer idx){
		return this.projectMembersRepository.findAllByProjectIdx(idx);
	}
	
	public void updateStatus(Issue issue, IssueStatus issueStatus) {
		issue.updateIssueStatus(issueStatus);
		this.issueRepository.save(issue);
	}

	public List<IssueStatus> getSortedIssueStatus(Integer projectIdx, Integer issueIdx){
		List<IssueStatus> updatedStatusList = issueStatusRepository.findAllByProjectIdxAndIdxNotOrderByStatusAsc(projectIdx, issueIdx);
		return updatedStatusList;
	}
}
