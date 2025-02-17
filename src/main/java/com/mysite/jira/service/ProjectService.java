package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.jira.dto.dashboard.create.ProjectListDTO;
import com.mysite.jira.dto.mywork.RecentProjectDTO;
import com.mysite.jira.dto.project.SearchDTO;
import com.mysite.jira.dto.project.list.ProjectListIsLikeDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.ProjectLikeMembers;
import com.mysite.jira.entity.ProjectMembers;
import com.mysite.jira.entity.ProjectRecentClicked;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.IssueStatusRepository;
import com.mysite.jira.repository.IssueTypeRepository;
import com.mysite.jira.repository.ProjectLikeMembersRepository;
import com.mysite.jira.repository.ProjectMembersRepository;
import com.mysite.jira.repository.ProjectRecentClickedRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;

	private final ProjectRecentClickedRepository projectRecentClickedRepository;

	private final ProjectMembersRepository projectMembersRepository;

	private final IssueTypeRepository issueTypeRepository;

	private final IssueStatusRepository issueStatusRepository;
	
	private final IssueRepository issueRepository;

	private final UtilityService utilityService;
	
	private final ProjectLikeMembersRepository projectLikeMembersRepository;

	public List<Project> getByKey(String key){
		return projectRepository.findByKey(key);
	}
	
	public Project getByIdx(Integer idx){
		return projectRepository.findById(idx).get();
	}
	
	public Integer getProjectAllCount(Integer jiraIdx) {
		return projectRepository.findByJira_idx(jiraIdx).size();
	}
	
	public Project getProjectByIdx(Integer idx) {
		Optional<Project> project = projectRepository.findById(idx);
		if (!project.isEmpty()) {
			return project.get();
		}
		return null;
	}
	
	public List<Project> getByJiraIdxProject(Integer jiraIdx){
		return projectRepository.findByJira_idx(jiraIdx);
	}
	
	public List<ProjectListDTO> getByJiraIdxProjectListDTO(Integer jiraIdx){
		List<Project> projectList = this.getByJiraIdxProject(jiraIdx);
		List<ProjectListDTO> result = new ArrayList<>();
		
		for(int i = 0; i < projectList.size(); i++) {
			Integer idx = projectList.get(i).getIdx();
			String name = projectList.get(i).getName();
			String iconFilename = projectList.get(i).getIconFilename();
			ProjectListDTO dto = ProjectListDTO.builder()
											   .idx(idx)
											   .name(name)
											   .iconFilename(iconFilename)
											   .build();
			result.add(dto);
		}
		return result;
	}
	
	public void addProjectRecentClicked(Account account, Jira jira, Project project) {
		ProjectRecentClicked projectRecentClicked = projectRecentClickedRepository.findByProject_idxAndAccount_idx(project.getIdx(), account.getIdx());
		if(projectRecentClicked != null) {
			projectRecentClicked.updateDate();
		}else {
			projectRecentClicked = ProjectRecentClicked.builder()
													   .account(account)
													   .jira(jira)
													   .project(project)
													   .build();
		}
		projectRecentClickedRepository.save(projectRecentClicked);
	}
	
	// kdw 프로젝트 추가(기본 필터, 기본 이슈유형, 기본 이슈상태, 프로젝트 클릭 로그 추가)
	@Transactional
	public void createProject(String name, String key, Jira jira, Account account) {
		int idx = (int) (Math.random() * 6);
		int sequence = 0;
		String[] colorArr = {"#C6EDFB", "#FCE4A6", "#FFD5D2","#FFD5D2", "#EED7FC", "#EED7FC"};
		String[] iconArr = { "project_icon_file1.svg", "project_icon_file2.svg", "project_icon_file3.svg",
				"project_icon_file4.svg", "project_icon_file5.svg", "project_icon_file6.svg"};
		Project project = Project.builder().key(key).name(name).color(colorArr[idx]).iconFilename(iconArr[idx])
				.jira(jira).account(account).sequence(sequence).build();
		projectRepository.save(project);
		// 프로젝트 클릭 로그
		this.addProjectRecentClicked(account, jira, project);

		// 프로젝트 멤버,권한 추가
		ProjectMembers projectMembers = ProjectMembers.builder().project(project).account(account).auth_type(3).build();
		projectMembersRepository.save(projectMembers);

		// 기본 이슈유형
		List<IssueType> issueTypes = Arrays.asList(
				IssueType.builder().name("작업").content("A small, distinct piece of work.").subContent("")
						.iconFilename("issue_task.svg").grade(2).project(project).build(),
				IssueType.builder().name("하위 작업").content("A small piece of work that''s part of a larger task.")
						.subContent("").iconFilename("issue_sub_task.svg").grade(1).project(project).build(),
				IssueType.builder().name("에픽").content("에픽은 작업의 큰 부분을 추적합니다.")
				.subContent("").iconFilename("issue_epik.svg").grade(3).project(project).build());
		issueTypeRepository.saveAll(issueTypes);
		// 기본 이슈상태
		List<IssueStatus> issueStatuses = Arrays.asList(
				IssueStatus.builder().status(1).name("해야 할 일").divOrder(1).project(project).build(),
				IssueStatus.builder().status(2).name("진행중").divOrder(2).project(project).build(),
				IssueStatus.builder().status(3).name("완료").divOrder(3).project(project).build());
		issueStatusRepository.saveAll(issueStatuses);
	}

	public void updateProject(Integer projectIdx, String name, String key, Account account) {
		
		Optional<Project> opProject = projectRepository.findById(projectIdx);
		Project project = null;
		if(!opProject.isEmpty()) {
			project = opProject.get();
		}
		
		project.updateProject(name, key, account);
		projectRepository.save(project);
	}
	
	public void deleteProject(Integer projectIdx) {
		projectRepository.deleteById(projectIdx);
	}
	
	public List<Project> getProjectByJiraIdx(Integer jiraIdx) {
		return projectRepository.findByJiraIdx(jiraIdx);
	}

	// kdw 최근 프로젝트
	public List<Object[]> getDistinctStatusAndNameByJiraIdx(Integer jiraIdx) {
		return projectRepository.findDistinctStatusAndNameByJiraIdx(jiraIdx);
	}

	// kdw 최근 프로젝트 리스트(이슈의 개수 포함)
	public List<RecentProjectDTO> getProjectList(Integer accountIdx, Integer jiraIdx) {
		List<RecentProjectDTO> result = new ArrayList<>();
		try {
			List<Map<String, Object>> projectList = projectRepository.findProjectIssueCounts(accountIdx, jiraIdx);
			for (int i = 0; i < projectList.size(); i++) {
				String name = projectList.get(i).get("name").toString();
				String iconFilename = projectList.get(i).get("iconFilename").toString();
				String color = projectList.get(i).get("color").toString();
				String key = projectList.get(i).get("key").toString();
				Integer issueCount = utilityService.isBigDecimal(projectList.get(i).get("issueCount")).intValue();

				RecentProjectDTO dto = RecentProjectDTO.builder().name(name).iconFilename(iconFilename).color(color)
						.key(key).issueCount(issueCount).build();
				result.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// kdw 프로젝트 리스트(project/list, 즐겨찾기인지 확인한 프로젝트)
	public List<ProjectListIsLikeDTO> getProjectListIsLike(Integer accountIdx, Integer jiraIdx, int page) {
		int startRow = page * 10 + 1;
		int endRow = (page + 1) * 10;
		
		List<Map<String, Object>> projectPage = projectRepository.findByProjectListIsLike(accountIdx, jiraIdx, startRow, endRow);
		List<ProjectListIsLikeDTO> result = new ArrayList<>();
		
		for (Map<String, Object> project : projectPage) {
			Integer projectIdx = (Integer) project.get("projectIdx");
			String projectName = project.get("projectName").toString();
			String projectIconFilename = project.get("projectIconFilename").toString();
			String projectKey = project.get("projectKey").toString();
			String accountName = project.get("accountName").toString();
			String accountIconFilename = project.get("accountIconFilename").toString();
			boolean isLike = project.get("isLike").toString().equals("true") ? true : false;

			ProjectListIsLikeDTO dto = ProjectListIsLikeDTO.builder().projectIdx(projectIdx).projectName(projectName)
					.projectIconFilename(projectIconFilename).projectKey(projectKey).accountName(accountName)
					.accountIconFilename(accountIconFilename).isLike(isLike).build();
			result.add(dto);
		}
		return result;
	}

	public Project getRecentTop1Project(Integer accountIdx, Integer jiraIdx) {
		List<Project> projectList = projectRepository.findByJira_IdxAndProjectClickedList_AccountIdxOrderByProjectClickedList_ClickedDateDesc(jiraIdx,accountIdx);
		if(projectList.size() != 0) {
			return projectRepository.findByJira_IdxAndProjectClickedList_AccountIdxOrderByProjectClickedList_ClickedDateDesc(jiraIdx,accountIdx).get(0);
		}
		return null;
		
	}

	public Project getByJiraIdxAndKeyProject(Integer jiraIdx, String key) {
		return projectRepository.findByJira_IdxAndKey(jiraIdx, key);
	}

	public Project getByJiraIdxAndNameProject(Integer jiraIdx, String name) {
		return projectRepository.findByJira_idxAndName(jiraIdx, name);
	}

	public List<SearchDTO> getByJiraIdxAndNameLikeProject(Integer jiraIdx, String searchName) {
		searchName = searchName + "%";
		if (searchName.equals("%"))
			searchName = "";

		List<Project> projectList = projectRepository.findByJira_idxAndNameLike(jiraIdx, searchName);
		List<SearchDTO> result = new ArrayList<>();
		for (int i = 0; i < projectList.size(); i++) {
			String name = projectList.get(i).getName();
			String iconFilename = projectList.get(i).getIconFilename();
			String key = projectList.get(i).getKey();
			SearchDTO dto = SearchDTO.builder().name(name).iconFilename(iconFilename).key(key).build();
			result.add(dto);
		}
		return result;
	}
	
	public List<ProjectMembers> getProjectMembersByProjectIdx(Integer projectIdx){
		return projectMembersRepository.findAllByProjectIdx(projectIdx);
	}
	
	public void updateProjectName(Project project, String name) {
		project.updateProjectName(name);
		projectRepository.save(project);
	}
	
	public void updateIssueTypeName(IssueType issueType, String name) {
		issueType.updateName(name);
		this.issueTypeRepository.save(issueType);
	}
	
	public void updateIssueTypeContent(IssueType issueType, String content) {
		issueType.updateContent(content);
		this.issueTypeRepository.save(issueType);
	}
	
	public List<Issue> getIssueListByIssueType(Integer projectIdx, Integer issueTypeIdx){
		return this.issueRepository.findByProjectIdxAndIssueTypeIdx(projectIdx, issueTypeIdx);
	}
	
	public void updateIssueListType(Issue issue, IssueType issueType) {
        issue.updateIssueType(issueType);
        this.issueRepository.save(issue);
	}
	
	public void deleteIssueType(Integer issueTypeIdx) {
		this.issueTypeRepository.deleteById(issueTypeIdx);
	}
	
	public boolean verificationIssueType(Integer projectIdx, String name) {
		Optional<IssueType> issueType = this.issueTypeRepository.findByProjectIdxAndName(projectIdx, name);
		if(issueType.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public void createIssueType(Project project, String name, String content, String iconFilename) {
		IssueType issueType = IssueType.builder()
									.name(name)
									.content(content)
									.iconFilename(iconFilename)
									.project(project)
									.grade(2)
									.build();
		this.issueTypeRepository.save(issueType);
	}
	
	public List<Account> getProjectMemberListByProjectIdx(Integer projectIdx){
		List<ProjectMembers> prjMemberList = this.projectMembersRepository.findAllByProjectIdx(projectIdx);
		List<Account> userList = new ArrayList<>();
		for(int i = 0; i < prjMemberList.size(); i++) {
			userList.add(prjMemberList.get(i).getAccount());
		}
		return userList;
	}
	
	public void createProjectMember(Account user, Project project) {
		ProjectMembers member = ProjectMembers.builder()
											.auth_type(2)
											.account(user)
											.project(project)
											.build();
		this.projectMembersRepository.save(member);
	}
	
	public Integer isProjectLiked(Integer userIdx, Integer projectIdx) {
		ProjectLikeMembers liked = this.projectLikeMembersRepository.findByAccountIdxAndProjectIdx(userIdx, projectIdx);
		if(liked == null) {
			return 0;
		}
		return liked.getIdx();
	}
	
	public void createProjectLikeData(Project project, Account user) {
		ProjectLikeMembers liked = ProjectLikeMembers.builder()
													.project(project)
													.account(user)
													.build();
		this.projectLikeMembersRepository.save(liked);
	}
	
	public void deleteProjectLikeData(Integer likeIdx) {
		this.projectLikeMembersRepository.deleteById(likeIdx);
	}
}
