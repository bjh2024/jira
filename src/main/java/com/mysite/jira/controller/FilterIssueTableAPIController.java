package com.mysite.jira.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.FilterIssueDTO;
import com.mysite.jira.dto.FilterIssueRequestDTO; // 추가된 DTO 임포트
import com.mysite.jira.dto.IssueStatusListDTO;
import com.mysite.jira.dto.IssueTypeListDTO;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.FilterIssueService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.IssueStatusService;
import com.mysite.jira.service.IssueTypeService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filter_issue_table")
public class FilterIssueTableAPIController {

    private final FilterIssueService filterIssueService; 
    private final IssueService issueService;
    private final ProjectService projectService;
    private final IssueTypeService issueTypeService;
    private final IssueStatusService issueStatusService;
    
    @PostMapping("/project_filter")
    public List<FilterIssueDTO> getInputDatas(@RequestBody FilterIssueRequestDTO filterRequest) {

        Integer[] projectIdx = filterRequest.getProjectIdx();
        List<Project> projectList = projectService.getProjectByJiraIdx(1);
        
        // projectIdx가 비어 있으면, 모든 프로젝트를 가져옵니다.
        if (projectIdx == null || projectIdx.length == 0) {
        	projectIdx = new Integer[projectList.size()];
        	for (int i = 0; i < projectList.size(); i++) {
        		projectIdx[i] = projectList.get(i).getIdx();
        	}
        }
        
        String[] issueTypes = filterRequest.getIssueTypes();
        List<IssueTypeListDTO> issueTypeList = issueTypeService.getDistinctIssueTypes(1);
        
        // issueTypes가 비어 있으면, 모든 이슈 타입을 가져옵니다.
        if (issueTypes == null || issueTypes.length == 0) {
        	issueTypes = new String[issueTypeList.size()];
        	for (int i = 0; i < issueTypeList.size(); i++) {
        		issueTypes[i] = issueTypeList.get(i).getName();
        	}
        }
        
        String[] issueStatus = filterRequest.getIssueStatus();
        List<IssueStatusListDTO> issueStatusList = issueStatusService.getDistinctIssueStatus(1);
        
        // issueStatus가 비어있으면 모든 status를 가지고 옴
        if (issueStatus == null || issueStatus.length == 0) {
        	issueStatus = new String[issueStatusList.size()];
        	for (int i = 0; i < issueStatusList.size(); i++) {
        		issueStatus[i] = issueStatusList.get(i).getName();
        	}
        }

        List<Issue> issueByProjectIdx = filterIssueService.getIssueByProjectIdxIn(projectIdx);
        List<Issue> issueByIssueTypeName = issueService.getIssuesByIssueTypeName(issueTypes);
        List<Issue> issueByIssueStatusName = issueService.getIssuesByIssueStatusName(issueStatus);
        
        // 두 리스트에서 공통된 아이템만 필터링
        issueByProjectIdx.retainAll(issueByIssueTypeName);
        issueByProjectIdx.retainAll(issueByIssueStatusName);

        // FilterIssueDTO로 변환하여 반환
        List<FilterIssueDTO> filterIssue = new ArrayList<>();
        for (Issue issue : issueByProjectIdx) {
            FilterIssueDTO dto = FilterIssueDTO.builder()
                .issueIconFilename(issue.getIssueType().getIconFilename())
                .issueKey(issue.getKey())
                .issueName(issue.getName())
                .issueManagerIconFilename(issue.getManager() != null ? 
                                            issue.getManager().getIconFilename() : "default_icon_file.png")
                .issueManagerName(issue.getManager() != null ? 
                                  issue.getManager().getName() : "할당되지 않음")
                .issueReporterIconFilename(issue.getReporter().getIconFilename())
                .issueReporterName(issue.getReporter().getName())
                .issuePriorityIconFilename(issue.getIssuePriority().getIconFilename())
                .issuePriorityName(issue.getIssuePriority().getName())
                .issueStatusName(issue.getIssueStatus().getName())
                .issueStatus(issue.getIssueStatus().getStatus())
                .createDate(issue.getCreateDate())
                .editDate(issue.getEditDate())
                .deadlineDate(issue.getDeadlineDate()) // 기본값을 현재 날짜로 설정
                .build();
            filterIssue.add(dto);
        }
        return filterIssue;
    }
}
