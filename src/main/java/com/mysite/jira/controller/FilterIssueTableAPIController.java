package com.mysite.jira.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        String[] reporterName = filterRequest.getIssueReporter();
        String[] issuePriority = filterRequest.getIssuePriority();
        LocalDateTime updateStartDate = filterRequest.getUpdateStartDate();
        LocalDateTime updateLastDate = filterRequest.getUpdateLastDate();
        LocalDateTime updateBeforeDate = filterRequest.getUpdateBeforeDate();
        LocalDateTime createStartDate = filterRequest.getCreateStartDate();
        LocalDateTime createLastDate = filterRequest.getCreateLastDate();
        LocalDateTime createBeforeDate = filterRequest.getCreateBeforeDate();
        LocalDateTime doneStartDate = filterRequest.getDoneStartDate();
        LocalDateTime doneLastDate = filterRequest.getDoneLastDate();
        LocalDateTime doneBeforeDate = filterRequest.getDoneBeforeDate();
        String searchContent = filterRequest.getSearchContent();
        Integer doneCheck = filterRequest.getDoneCheck();
        Integer notDoneCheck = filterRequest.getNotDoneCheck();
 
        List<Issue> issueByProjectIdx = filterIssueService.getIssueByProjectIdxIn(projectIdx);
        List<Issue> issueByIssueTypeName = issueService.getIssuesByIssueTypeName(issueTypes);
        List<Issue> issueByIssueStatusName = issueService.getIssuesByIssueStatusName(issueStatus);
        List<Issue> issueByManagerName = issueService.getByManagerNameIn(managerName);
        List<Issue> issueByReporterName = issueService.getReporterNameIn(reporterName);
        List<Issue> issueByissuePriority = issueService.getIssuePriorityNameIn(issuePriority);
        
        List<Issue> updateStartDateOver = issueService.getStartDateGreaterThanEqual(updateStartDate);
        List<Issue> updateLastDateOver = issueService.getLastDateLessThanEqual(updateLastDate);
        List<Issue> updateBeforeDateOver = issueService.getStartDateGreaterThanEqual(updateBeforeDate);
        
        List<Issue> createStartDateOver = issueService.getcreateStartDateGreaterThanEqual(createStartDate);
        List<Issue> createLastDateOver = issueService.getcreateLastDateLessThanEqual(createLastDate);
        List<Issue> createBeforeDateOver = issueService.getcreateStartDateGreaterThanEqual(createBeforeDate);
        
        List<Issue> doneStartDateOver = issueService.getfinishStartDateGreaterThanEqual(doneStartDate);
        List<Issue> doneLastDateOver = issueService.getfinishLastDateLessThanEqual(doneLastDate);
        List<Issue> doneBeforeDateOver = issueService.getfinishStartDateGreaterThanEqual(doneBeforeDate);
        
        List<Issue> searchIssue = issueService.getIssueByNameLike(searchContent);
        List<Issue> doneCheck1 = issueService.getIssueByStatus(doneCheck);
        List<Issue> notDoneCheck2 = issueService.getIssueByStatusNot(notDoneCheck);
        // 두 리스트에서 공통된 아이템만 필터링
        if(managerName.length > 0) {
        	issueList.retainAll(issueByManagerName);
        	if (Arrays.asList(managerName).contains("할당되지 않음")) {
        		List<Issue> issueByManagerNull = issueService.getByManagerNull();
        		// manager가 없는 이슈도 포함
        		issueList.addAll(issueByManagerNull);
        	}
        }
        // 업데이트 필터
        if(updateStartDate != null) {
        	issueList.retainAll(updateStartDateOver);
        }
        if(updateLastDate != null) {
        	issueList.retainAll(updateLastDateOver);
        }
        if(updateBeforeDate != null) {
        	issueList.retainAll(updateBeforeDateOver);
        }
        // 업데이트 필터 끝
        // 해결
        if(!(notDoneCheck != null && doneCheck != null)) {
        	if(notDoneCheck != null) {
        		issueList.retainAll(notDoneCheck2);
        	}
        	if(doneCheck != null) {
        		issueList.retainAll(doneCheck1);
        	}
        }
        // 해결 끝
        // 생성 날짜 필터
        if(createStartDate != null) {
        	issueList.retainAll(createStartDateOver);
        }
        if(createLastDate != null) {
        	issueList.retainAll(createLastDateOver);
        }
        if(createBeforeDate != null) {
        	issueList.retainAll(createBeforeDateOver);
        }
        // 생성 날짜 필터 끝
        // 해결 날짜 필터
        if(doneStartDate != null) {
        	issueList.retainAll(doneStartDateOver);
        }
        if(doneLastDate != null) {
        	issueList.retainAll(doneLastDateOver);
        }
        if(doneBeforeDate != null) {
        	issueList.retainAll(doneBeforeDateOver);
        }
        // 해결 날짜 필터 끝
        if(projectIdx.length > 0) {
        	issueList.retainAll(issueByProjectIdx);
        }
        if(issueTypes.length > 0) {
        	issueList.retainAll(issueByIssueTypeName);
        }
        if(issueStatus.length > 0) {
        	issueList.retainAll(issueByIssueStatusName);
        }
        if(reporterName.length > 0) {
        	issueList.retainAll(issueByReporterName);
        }
        if(issuePriority.length > 0) {
        	issueList.retainAll(issueByissuePriority);
        }
        
        if(searchContent != null) {
        	issueList.retainAll(searchIssue);
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
                .finishDate(issue.getFinishDate())
                .createDate(issue.getCreateDate())
                .editDate(issue.getEditDate())
                .deadlineDate(issue.getDeadlineDate()) // 기본값을 현재 날짜로 설정
                .build();
            filterIssue.add(dto);
        }
        return filterIssue;
    }
}
