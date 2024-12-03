package com.mysite.jira.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.FilterIssueDTO;
import com.mysite.jira.dto.FilterIssueRequestDTO; // 추가된 DTO 임포트
import com.mysite.jira.entity.Issue;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.FilterIssueService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.IssueStatusService;
import com.mysite.jira.service.IssueTypeService;
import com.mysite.jira.service.JiraMembersService;
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
    private final JiraMembersService jiraMembersService;
    private final AccountService accountService;
    
    @PostMapping("/project_filter")
    public List<FilterIssueDTO> getInputDatas(@RequestBody FilterIssueRequestDTO filterRequest) {

    	List<Issue> issueList = issueService.getIssuesByJiraIdx(1);
    	
        Integer[] projectIdx = filterRequest.getProjectIdx();
        String[] issueTypes = filterRequest.getIssueTypes();
        String[] issueStatus = filterRequest.getIssueStatus();
        String[] managerName = filterRequest.getIssueManager();
       
        List<Issue> issueByProjectIdx = filterIssueService.getIssueByProjectIdxIn(projectIdx);
        List<Issue> issueByIssueTypeName = issueService.getIssuesByIssueTypeName(issueTypes);
        List<Issue> issueByIssueStatusName = issueService.getIssuesByIssueStatusName(issueStatus);
        List<Issue> issueByManagerName = issueService.getByManagerNameIn(managerName);
        // 두 리스트에서 공통된 아이템만 필터링
        if(managerName.length > 0) {
        	issueList.retainAll(issueByManagerName);
        	if (Arrays.asList(managerName).contains("할당되지 않음")) {
        		List<Issue> issueByManagerNull = issueService.getByManagerNull();
        		// manager가 없는 이슈도 포함
        		issueList.addAll(issueByManagerNull);
        	}
        }
        if(projectIdx.length > 0) {
        	issueList.retainAll(issueByProjectIdx);
        }
        if(issueTypes.length > 0) {
        	issueList.retainAll(issueByIssueTypeName);
        }
        if(issueStatus.length > 0) {
        	issueList.retainAll(issueByIssueStatusName);
        }
        
        // FilterIssueDTO로 변환하여 반환
        List<FilterIssueDTO> filterIssue = new ArrayList<>();
        for (Issue issue : issueList) {
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
