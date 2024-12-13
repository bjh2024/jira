package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.mywork.RecentProjectDTO;
import com.mysite.jira.dto.project.ProjectSearchDTO;
import com.mysite.jira.dto.project.list.ProjectListIsLikeDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.ProjectRecentClicked;
import com.mysite.jira.repository.IssueStatusRepository;
import com.mysite.jira.repository.IssueTypeRepository;
import com.mysite.jira.repository.ProjectRecentClickedRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	
	private final ProjectRecentClickedRepository projectRecentClickedRepository;

	private final IssueTypeRepository issueTypeRepository;
	
	private final IssueStatusRepository issueStatusRepository;

	private final UtilityService utilityService;

	public Project getProjectByIdx(Integer idx) {
		Optional<Project> project = projectRepository.findById(idx);
		if(!project.isEmpty()) {
			return project.get();
		}
		return null;
	}
	
	// kdw 프로젝트 추가(기본 필터, 기본 이슈유형, 기본 이슈상태, 프로젝트 클릭 로그 추가!!!)
	public void createProject(String name, String key, Jira jira, Account account) {
		int idx = (int)(Math.random()*3);
		int sequence = 0;
		String[] colorArr = {"#FFD5D2", "#FCE4A6", "#C6EDFB", "#EED7FC"};
		String[] iconArr = {"project_icon_file1.png", "project_icon_file2.png", "project_icon_file3.png", "project_icon_file4.png"};
		Project project = Project.builder().key(key).name(name).color(colorArr[idx]).iconFilename(iconArr[idx])
				.jira(jira).account(account).sequence(sequence).build();
		projectRepository.save(project);
		// 프로젝트 클릭 로그
		ProjectRecentClicked projectRecentClicked = ProjectRecentClicked.builder()
																		.account(account)
																		.jira(jira)
																		.project(project)
																		.build();
		projectRecentClickedRepository.save(projectRecentClicked);
		// 기본 이슈유형
		List<IssueType> issueTypes = Arrays.asList(
			    IssueType.builder()
			    		 .name("작업")
			    		 .content("A small, distinct piece of work.")
			    		 .subContent("")
			    		 .iconFilename("issue_task.svg")
			    		 .grade(2)
			    		 .project(project).build(),
			    IssueType.builder()
			             .name("하위 작업")
			             .content("A small piece of work that''s part of a larger task.")
			             .subContent("")
			             .iconFilename("issue_sub_task.svg")
			    		 .grade(2)
			    		 .project(project).build()
			);
		issueTypeRepository.saveAll(issueTypes);
		// 기본 이슈상태
		List<IssueStatus> issueStatuses = Arrays.asList(
			    IssueStatus.builder().status(1).name("해야 할 일").divOrder(1).project(project).build(),
			    IssueStatus.builder().status(2).name("진행중").divOrder(2).project(project).build(),
			    IssueStatus.builder().status(3).name("완료").divOrder(3).project(project).build()
			);
		issueStatusRepository.saveAll(issueStatuses);
		// 기본 필터
//		List<Filter> issueStatuses = Arrays.asList(
//			    IssueStatus.builder().status(1).name("해야 할 일").divOrder(1).project(project).build(),
//			    IssueStatus.builder().status(2).name("진행중").divOrder(2).project(project).build(),
//			    IssueStatus.builder().status(3).name("완료").divOrder(3).project(project).build()
//		);
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
		int startRow = page * 10;
		int endRow = (startRow + (page + 1) * 10) - 1;
		List<Map<String, Object>> projectPage = projectRepository.findByProjectListIsLike(accountIdx, jiraIdx, startRow,
				endRow);
		List<ProjectListIsLikeDTO> result = new ArrayList<>();

		for (Map<String, Object> project : projectPage) {
			Integer projectIdx = (Integer)project.get("projectIdx");
			String projectName = project.get("projectName").toString();
			String projectIconFilename = project.get("projectIconFilename").toString();
			String projectKey = project.get("projectKey").toString();
			String accountName = project.get("accountName").toString();
			String accountIconFilename = project.get("accountIconFilename").toString();
			boolean isLike = project.get("isLike").toString().equals("true") ? true : false;

			ProjectListIsLikeDTO dto = ProjectListIsLikeDTO.builder()
					.projectIdx(projectIdx)
					.projectName(projectName)
					.projectIconFilename(projectIconFilename)
					.projectKey(projectKey).accountName(accountName)
					.accountIconFilename(accountIconFilename)
					.isLike(isLike).build();
			result.add(dto);
		}
		return result;
	}

	public Project getRecentTop1Project(Integer accountIdx, Integer jiraIdx) {
		return projectRepository.findByRecentClickedProject(accountIdx, jiraIdx);
	}

	public Project getByJiraIdxAndKeyProject(Integer jiraIdx, String key) {
		return projectRepository.findByJira_IdxAndKey(jiraIdx, key);
	}

	public Project getByJiraIdxAndNameProject(Integer jiraIdx, String name) {
		return projectRepository.findByJira_idxAndName(jiraIdx, name);
	}
	
	public List<ProjectSearchDTO> getByJiraIdxAndNameLikeProject(Integer jiraIdx, String searchName) {
		searchName = searchName + "%";
		if(searchName.equals("%")) searchName = "";
		
		List<Project> projectList = projectRepository.findByJira_idxAndNameLike(jiraIdx, searchName);
		List<ProjectSearchDTO> result = new ArrayList<>();
		for(int i = 0; i < projectList.size(); i++) {
			String name = projectList.get(i).getName();
			String iconFilename = projectList.get(i).getIconFilename();
			String key = projectList.get(i).getKey();
			ProjectSearchDTO dto = ProjectSearchDTO.builder()
												   .name(name)
												   .iconFilename(iconFilename)
												   .key(key)
												   .build();
			result.add(dto);
		}
		return result;
	}
}
