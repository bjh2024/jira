package com.mysite.jira.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueExtends;
import com.mysite.jira.entity.IssueFile;
import com.mysite.jira.entity.IssueLabel;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssueLikeMembers;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.IssueRecentClicked;
import com.mysite.jira.entity.IssueReply;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.ProjectLogData;
import com.mysite.jira.entity.ProjectLogStatus;
import com.mysite.jira.entity.ProjectMembers;
import com.mysite.jira.entity.Team;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.IssueExtendsRepository;
import com.mysite.jira.repository.IssueFileRepository;
import com.mysite.jira.repository.IssueLabelDataRepository;
import com.mysite.jira.repository.IssueLabelRepository;
import com.mysite.jira.repository.IssueLikeMembersRepository;
import com.mysite.jira.repository.IssuePriorityRepository;
import com.mysite.jira.repository.IssueRecentClickedRepository;
import com.mysite.jira.repository.IssueReplyRepository;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.IssueStatusRepository;
import com.mysite.jira.repository.IssueTypeRepository;
import com.mysite.jira.repository.ProjectLogDataRepository;
import com.mysite.jira.repository.ProjectLogStatusRepository;
import com.mysite.jira.repository.ProjectMembersRepository;
import com.mysite.jira.repository.ProjectRepository;
import com.mysite.jira.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardMainService {
	private final IssueRepository issueRepository;
	private final ProjectRepository projectRepository; 
	private final IssueStatusRepository issueStatusRepository;
	private final IssueExtendsRepository issueExtendsRepository;
	private final IssueTypeRepository issueTypeRepository;
	private final IssueLabelRepository issueLabelRepository;
	private final IssueLabelDataRepository issueLabelDataRepository;
	private final IssueReplyRepository issueReplyRepository;
	private final IssuePriorityRepository issuePriorityRepository;
	private final TeamRepository teamRepository;
	private final IssueFileRepository issueFileRepository;
	private final ProjectMembersRepository projectMembersRepository;
	private final ProjectLogDataRepository projectLogDataRepository;
	private final ProjectLogStatusRepository projectLogStatusRepository;
	private final AccountRepository accountRepository;
	private final IssueLikeMembersRepository issueLikeMembersRepository;
	private final IssueRecentClickedRepository issueRecentClickedRepository;
	
	
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
		return this.issueRepository.findIssuesByProjectIdxAndIssueTypeGradeGreaterThanOrderByDivOrder(idx, 1);
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
	
	public List<IssueExtends> getIssueExtendsByParentIdx(Integer parentIdx){
		return this.issueExtendsRepository.findByParentIdx(parentIdx);
	}
	
	public List<IssueType> getIssueTypesByProjectIdxAndGradeGreaterThan(Integer projectIdx, Integer grade){
		return this.issueTypeRepository.findByProjectIdxAndGradeGreaterThanOrderByGradeDesc(projectIdx, grade);
	}
	
	public List<IssueType> getIssueTypeByProjectIdxAndGrade(Integer projectIdx, Integer grade){
		return this.issueTypeRepository.findByProjectIdxAndGrade(projectIdx, grade);
	}
	
	public List<IssueLabel> getIssueLabel(Integer idx){
		return this.issueLabelRepository.findByJiraIdx(idx);
	}
	
	public List<IssueLabelData> getLabelData(Integer idx){
		return this.issueLabelDataRepository.findByIssueProjectJiraIdx(idx);
	}
	
	public List<IssueLabelData> getAlterLabelData(Integer[] idx){
		return this.issueLabelDataRepository.findDistinctByIssueLabelIdxNotIn(idx);
	}
	
	public List<IssueReply> getIssueReply(Integer idx){
		return this.issueReplyRepository.findByIssueProjectIdxOrderByCreateDateDesc(idx);
	}
	
	public List<IssueReply> getFilterIssueReply(){
		return this.issueReplyRepository.findAll();
	}
	
	public IssuePriority getOnceIssuePriority(Integer idx) {
		Optional<IssuePriority> optPriority = this.issuePriorityRepository.findById(idx);
		IssuePriority priority = null;
		if(optPriority.isPresent()) {
			priority = optPriority.get();
		}
		return priority;
	}
	
	public List<IssuePriority> getIssuePriority(){
		return this.issuePriorityRepository.findAllByOrderByIdxDesc();
	}
	
	public List<IssuePriority> getAlterIssuePriority(Integer idx){
		return this.issuePriorityRepository.findAllByIdxNotOrderByIdxDesc(idx);
	}
	
	public Team getOnceTeam(Integer idx) {
		Optional<Team> optTeam = this.teamRepository.findById(idx);
		return optTeam.get();
	}
	
	public List<Team> getTeamList(){
		return this.teamRepository.findAll();
	}
	
	public List<IssueFile> getFilesbyProjectIdx(Integer idx){
		return this.issueFileRepository.findByIssueProjectIdx(idx);
	}
	
	public List<ProjectMembers> getPrjMembers(Integer idx){
		return this.projectMembersRepository.findAllByProjectIdx(idx);
	}
	
	public void updateStatus(Issue issue, IssueStatus issueStatus) {
		issue.updateIssueStatus(issueStatus);
		this.issueRepository.save(issue);
	}
	
	public void updateStartDate(Issue issue, LocalDateTime date) {
		issue.updateStartDate(date);
		this.issueRepository.save(issue);
	}
	
	public void updateDeadlineDate(Issue issue, LocalDateTime date) {
		issue.updateDeadlineDate(date);
		this.issueRepository.save(issue);
	}

	public List<IssueStatus> getSortedIssueStatus(Integer projectIdx, Integer idx){
		List<IssueStatus> updatedStatusList = this.issueStatusRepository.findAllByProjectIdxAndIdxNotOrderByStatusAsc(projectIdx, idx);
		return updatedStatusList;
	}
	
	public void updatePriority(Issue issue, IssuePriority priority) {
		issue.updatePriority(priority);
		this.issueRepository.save(issue);
	}
	
	public List<Team> getJiraTeamList(Integer jiraIdx){
		List<Team> teamList = this.teamRepository.findByJiraIdx(jiraIdx);
		return teamList;
	}
	
	public void updateTeam(Issue issue, Team team) {
		issue.updateTeam(team);
		this.issueRepository.save(issue);
	}
	
	public List<ProjectMembers> getReporterList(Integer projectIdx, Integer accountIdx){
		List<ProjectMembers> membersList = this.projectMembersRepository.findAllByProjectIdxAndAccountIdxNot(projectIdx, accountIdx);
		return membersList;
	}
	
	public Account getAccountById(Integer idx) {
		Optional<Account> account = this.accountRepository.findById(idx);
		return account.get();
	}
	
	public void updateReporter(Issue issue, Account reporter) {
		issue.updateReporter(reporter);
		this.issueRepository.save(issue);
	}
	
	public void updateManager(Issue issue, Account manager) {
		issue.updateManager(manager);
		this.issueRepository.save(issue);
	}
	
	public boolean statusNameCheck(Integer projectIdx, String name) {
		Optional<IssueStatus> status = this.issueStatusRepository.findByProjectIdxAndName(projectIdx, name);
		if(status.isEmpty()) {
			return true;
		}
		return false;
	}
	
	
	public void createIssueStatus(String name, Integer status, Integer projectIdx) {
		Optional<Project> optProject = this.projectRepository.findById(projectIdx);
		Project project = optProject.get();
		
		List<IssueStatus> statusList = this.issueStatusRepository.findAllByProjectIdxOrderByStatusAsc(projectIdx);
		Integer max = 0;
		for(int i = 0; i < statusList.size(); i++) {
			if(statusList.get(i).getDivOrder() > max) {
				max = statusList.get(i).getDivOrder();
			}
		}
		
		IssueStatus issueStatus = IssueStatus.builder()
									.name(name)
									.status(status)
									.divOrder(max + 1)
									.project(project)
									.build();
		this.issueStatusRepository.save(issueStatus);
	}
	
	public IssueType getIssueTypeByIdx(Integer idx) {
		Optional<IssueType> type = this.issueTypeRepository.findById(idx);
		return type.get();
	}
	
	public Project getProjectByIdx(Integer idx) {
		Optional<Project> project = this.projectRepository.findById(idx);
		return project.get();
	}
	
	public boolean IssueNameCheck(Integer projectIdx, String issueName) {
		Optional<Issue> issue = this.issueRepository.findByProjectIdxAndName(projectIdx, issueName);
		if(issue.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public Issue createIssue(String issueName, Integer projectIdx, Integer issueTypeIdx, 
			Integer statusIdx, Integer reporteridx) {
		Optional<Project> optProject = this.projectRepository.findById(projectIdx);
		Optional<IssueType> optIssueType = this.issueTypeRepository.findById(issueTypeIdx);
		Optional<IssueStatus> optIssueStatus = this.issueStatusRepository.findById(statusIdx);
		Optional<IssuePriority> optIssuePriority = this.issuePriorityRepository.findById(3);
		Optional<Account> optUser = this.accountRepository.findById(reporteridx);
		
		Project project = optProject.get();
		Jira jira = project.getJira();
		project.updateSeq(project.getSequence());
		this.projectRepository.save(project);
		IssueType issueType = optIssueType.get();
		IssueStatus issueStatus = optIssueStatus.get();
		IssuePriority issuePriority = optIssuePriority.get();
		Account user = optUser.get();
		String key = project.getKey() + "-" + project.getSequence();
		
		List<Issue> issueList = this.getIssuesByProjectIdx(projectIdx);
		Integer max = 0;
		for(int i = 0; i < issueList.size(); i++) {
			if(issueList.get(i).getIssueStatus().getIdx() == statusIdx &&
					issueList.get(i).getDivOrder() > max) {
				max = issueList.get(i).getDivOrder();
			}
		}
		
		if(issueType.getGrade() == 1) {
			max = -1;
		}
		
		Issue issue = Issue.builder()
							.key(key)
							.name(issueName)
							.createDate(LocalDateTime.now())
							.editDate(LocalDateTime.now())
							.jira(jira)
							.project(project)
							.issueType(issueType)
							.issueStatus(issueStatus)
							.issuePriority(issuePriority)
							.manager(user)
							.reporter(user)
							.divOrder(max + 1)
							.build();
		this.issueRepository.save(issue);
		
		this.createIssueRecentClicked(jira, user, issue);
		return issue;
	}
	
	public void updateIssueBoxOrder(Integer oldIdx, Integer newIdx, Integer projectIdx, boolean reverse) {
		List<IssueStatus> issueList = this.issueStatusRepository.findByDivOrderBetweenAndProjectIdx(oldIdx, newIdx, projectIdx);
		for(int i = 0; i < issueList.size(); i++) {
			IssueStatus issue = issueList.get(i);
			if(reverse) {
				if(issueList.get(i).getDivOrder() == newIdx) {
					Integer newOrder = oldIdx;
					issue.updateDivOrder(newOrder);
					this.issueStatusRepository.save(issue);
				}else {
					Integer newOrder = issue.getDivOrder() + 1;
					issue.updateDivOrder(newOrder);
					this.issueStatusRepository.save(issue);
				}
			}else {
				if(issueList.get(i).getDivOrder() == oldIdx) {
					Integer newOrder = newIdx;
					issue.updateDivOrder(newOrder);
					this.issueStatusRepository.save(issue);
				}else {
					Integer newOrder = issue.getDivOrder() - 1;
					issue.updateDivOrder(newOrder);
					this.issueStatusRepository.save(issue);
				}
			}
			
		}
	}
	
	public void updateIssueOrder(Integer currentIssueIdx, Integer newIdx, Integer statusIdx) {
		List<Issue> issueList = this.issueRepository.findByDivOrderGreaterThanEqualAndIssueStatusIdxOrderByDivOrder(newIdx, statusIdx);
		Optional<Issue> optCurrentIssue = this.issueRepository.findById(currentIssueIdx);
		Issue currentIssue = optCurrentIssue.get();
		currentIssue.updateDivOrder(newIdx);
		this.issueRepository.save(currentIssue);
		for(int i = 0; i < issueList.size(); i++) {
			Issue issue = issueList.get(i);
			if(issue.getIdx() != currentIssueIdx && issue.getIssueType().getGrade() != 1) {
				Integer newOrder = issue.getDivOrder() + 1;
				issue.updateDivOrder(newOrder);
				this.issueRepository.save(issue);
			}
		}
	}
	
	public void updatePrevIssueOrder(Integer currentIssueidx, Integer oldIdx, Integer oldStatusIdx) {
		List<Issue> issueList = this.issueRepository.findByDivOrderGreaterThanEqualAndIssueStatusIdxOrderByDivOrder(oldIdx, oldStatusIdx);
		for(int i = 0; i < issueList.size(); i++) {
			if(issueList.get(i).getIssueType().getGrade() != 1) {
				Issue issue = issueList.get(i);
				Integer newOrder = issue.getDivOrder() - 1;
				issue.updateDivOrder(newOrder);
				this.issueRepository.save(issue);
			}
		}
	}
	
	public IssueLabel getIssueLabelByIdx(Integer idx) {
		Optional<IssueLabel> optLabel = this.issueLabelRepository.findById(idx);
		return optLabel.get();
	}
	
	public IssueLabelData createIssueLabelData(Issue issue, IssueLabel issueLabel) {
		IssueLabelData newLabelData = IssueLabelData.builder()
												.issue(issue)
												.issueLabel(issueLabel)
												.build();
		this.issueLabelDataRepository.save(newLabelData);
		return newLabelData;
	}
	
	public void deleteIssueLabelData(Integer idx) {
		this.issueLabelDataRepository.deleteById(idx);
	}
	
	public void deleteIssueData(Integer idx) {
		this.issueRepository.deleteById(idx);
	}
	
	public void updateIssueName(Issue issue, String name) {
		issue.updateName(name);
		this.issueRepository.save(issue);
	}
	
	public void updateIssueContent(Issue issue, String content) {
		issue.updatecontent(content);
		this.issueRepository.save(issue);
	}
	
	public List<ProjectLogData> getLogDataList(Integer issueIdx, String order){
		List<ProjectLogData> logList = new ArrayList<>();
		if(order.equals("ASC")) {
			logList = this.projectLogDataRepository.findByIssueIdxOrderByCreateDateAsc(issueIdx);
		}else {
			logList = this.projectLogDataRepository.findByIssueIdxOrderByCreateDateDesc(issueIdx);
		}
		return logList;
	}
	
	public List<ProjectLogData> getAllLogDataList(Integer issueIdx, String order){
		List<ProjectLogData> logList = new ArrayList<>();
		if(order.equals("ASC")) {
			logList = this.projectLogDataRepository.findByIssueIdxOrderByCreateDateAsc(issueIdx);
		}else {
			logList = this.projectLogDataRepository.findByIssueIdxOrderByCreateDateDesc(issueIdx);
		}
		return logList;
	}
	
	public void updateStatusTitle(IssueStatus currentStatus, String name) {
		currentStatus.updateName(name);
		this.issueStatusRepository.save(currentStatus);
	}
	
	public List<Issue> getIssueListByIssueStatus(Integer projectIdx, Integer statusIdx){
		List<Issue> issueList = this.issueRepository.findByProjectIdxAndIssueStatusIdx(projectIdx, statusIdx);
		return issueList;
	}
	
	public void deleteIssueStatus(Integer idx) {
		this.issueStatusRepository.deleteById(idx);
	}
	
	public IssueReply createReply(Issue issue, Account writer, String content) {
		IssueReply reply = IssueReply.builder()
									.content(content)
									.createDate(LocalDateTime.now())
									.editDate(null)
									.issue(issue)
									.account(writer)
									.build();
		this.issueReplyRepository.save(reply);
		return reply;
	}
	
	public IssueReply getIssueReplyByIdx(Integer idx) {
		Optional<IssueReply> reply = this.issueReplyRepository.findById(idx);
		return reply.get();
	}
	
	public void updateIssueReply(IssueReply reply, String content) {
		LocalDateTime date = LocalDateTime.now();
		reply.updateReplyContent(content);
		reply.updateEditDate(date);
		this.issueReplyRepository.save(reply);
	}
	
	public void deleteIssueReply(Integer idx) {
		this.issueReplyRepository.deleteById(idx);
	}
	
	public List<IssueType> getGeneralIssueTypeList(Integer projectIdx, Integer grade){
		List<IssueType> list = this.issueTypeRepository.findByProjectIdxAndGrade(projectIdx, grade);
		return list;
	}
	
	public IssueType getSubIssueTypeList(Integer projectIdx) {
		List<IssueType> type = this.issueTypeRepository.findByProjectIdxAndGrade(projectIdx, 3);
		return type.get(0);
	}
	
	public Issue createSubIssue(Jira jira, Project project, IssueType issueType, IssueStatus issueStatus,
								IssuePriority issuePriority, Account user, String issueName) {
		project.updateSeq(project.getSequence());
		this.projectRepository.save(project);
		String key = project.getKey() + "-" + project.getSequence();
		
		List<Issue> issueList = this.getIssuesByProjectIdx(project.getIdx());
		Integer max = 0;
		for(int i = 0; i < issueList.size(); i++) {
			if(issueList.get(i).getIssueStatus().getIdx() == issueStatus.getIdx() &&
					issueList.get(i).getDivOrder() > max) {
				max = issueList.get(i).getDivOrder();
			}
		}
		
		Issue issue = Issue.builder()
				.key(key)
				.name(issueName)
				.createDate(LocalDateTime.now())
				.editDate(LocalDateTime.now())
				.jira(jira)
				.project(project)
				.issueType(issueType)
				.issueStatus(issueStatus)
				.issuePriority(issuePriority)
				.manager(user)
				.reporter(user)
				.divOrder(max + 1)
				.build();

		this.issueRepository.save(issue);
		
		return issue;
	}
	
	public void createIssueExtends(Issue parent, Issue child, Project project) {
		IssueExtends issueExtends = IssueExtends.builder()
												.parent(parent)
												.child(child)
												.project(project)
												.build();
		this.issueExtendsRepository.save(issueExtends);
	}
	
	public void deleteIssueExtends(Integer extendsIdx) {
		this.issueExtendsRepository.deleteById(extendsIdx);
	}
	
	public List<Issue> getIssueByProjectIdxAndIssueTypeGrade(Integer projectIdx, Integer grade){
		List<Issue> issueList = this.issueRepository.findByProjectIdxAndIssueTypeGrade(projectIdx, grade);
		return issueList;
	}
	
	public List<Issue> getIssueByProjectIdxAndIssueTypeGradeAndIdxNot(Integer projcetIdx, Integer grade, Integer idx){
		List<Issue> issueList = this.issueRepository.findByProjectIdxAndIssueTypeGradeAndIdxNot(projcetIdx, grade, idx);
		return issueList;
	}
	
	public IssueExtends getOnceIssueExtends(Integer projectIdx, Integer childIdx, Integer parentIdx) {
		Optional<IssueExtends> extend = this.issueExtendsRepository.findByProjectIdxAndParentIdxAndChildIdx(projectIdx, parentIdx, childIdx);
		return extend.get();
	}
	
	public void updateEpikIssuePath(IssueExtends extend, Issue newParent) {
		extend.updateParent(newParent);
		this.issueExtendsRepository.save(extend);
	}
	
	public void createIssuePath(Project project, Issue parent, Issue child) {
		IssueExtends path = IssueExtends.builder()
										.project(project)
										.parent(parent)
										.child(child)
										.build();
		this.issueExtendsRepository.save(path);
	}
	
	public ProjectLogStatus getLogStatusByIdx(Integer idx) {
		Optional<ProjectLogStatus> status = this.projectLogStatusRepository.findById(idx);
		return status.get();
	}
	
	public void createProjectLogData(Issue issue, Account creator, ProjectLogStatus status) {
		ProjectLogData data = ProjectLogData.builder()
											.issue(issue)
											.account(creator)
											.projectLogStatus(status)
											.build();
		this.projectLogDataRepository.save(data);
	}
	
	public List<IssueType> getUpdateIssueTypeList(Integer projectIdx, Integer typeIdx){
		List<IssueType> list = this.issueTypeRepository.findByProjectIdxAndGradeAndIdxNot(projectIdx, 2, typeIdx);
		return list;
	}
	
	public void updateIssueType(Issue issue, IssueType issueType) {
		issue.updateIssueType(issueType);
		this.issueRepository.save(issue);
	}
	
	public List<IssueLikeMembers> getVoterListByIssueIdx(Integer issueIdx){
		List<IssueLikeMembers> voterList = this.issueLikeMembersRepository.findByIssueIdx(issueIdx);
		return voterList;
	}
	
	public IssueLikeMembers getOnceVoteData(Integer userIdx, Integer issueIdx) {
		Optional<IssueLikeMembers> voteData = this.issueLikeMembersRepository.findByAccountIdxAndIssueIdx(userIdx, issueIdx);
		return voteData.get();
	}
	
	public IssueLikeMembers createVoteData(Account account, Issue issue) {
		IssueLikeMembers data = IssueLikeMembers.builder()
												.account(account)
												.issue(issue)
												.build();
		this.issueLikeMembersRepository.save(data);
		return data;
	}
	
	public void deleteVoteData(Integer idx) {
		this.issueLikeMembersRepository.deleteById(idx);
	}
	
	public List<IssueFile> getFileListByIssueIdx(Integer idx){
		List<IssueFile> fileList = this.issueFileRepository.findByIssueIdx(idx);
		return fileList;
	}
	
	public Optional<IssueRecentClicked> verificationIssueRecentClicked(Integer jiraIdx, Integer accountIdx, Integer issueIdx) {
		Optional<IssueRecentClicked> recentClicked = this.issueRecentClickedRepository.findByJiraIdxAndAccountIdxAndIssueIdx(jiraIdx, accountIdx, issueIdx);
		return recentClicked;
	}
	
	public void createIssueRecentClicked(Jira jira, Account user, Issue issue) {
		IssueRecentClicked clicked = IssueRecentClicked.builder()
													.jira(jira)
													.account(user)
													.issue(issue)
													.build();
		this.issueRecentClickedRepository.save(clicked);
	}
	
	public void updateIssueRecentClicked(IssueRecentClicked clicked) {
		clicked.updateClickedDate();
		this.issueRecentClickedRepository.save(clicked);
	}
}
