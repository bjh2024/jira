package com.mysite.jira.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.header.HeaderRecentIssueDTO;
import com.mysite.jira.dto.header.HeaderRequestFilterDTO;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.RecentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/header")
public class HeaderAPIController {

    private final RecentService recentService;
    
    private final JiraService jiraService;
    
    // List<Issue> 일경우 엔티티는 json으로 변환시 문제가 발생함
    @PostMapping("/filter")
    public List<HeaderRecentIssueDTO> getInputData(@RequestBody HeaderRequestFilterDTO IssueInputFilterDTO,
    											   HttpServletRequest request,
    											   Principal principal) {
    	String uri = IssueInputFilterDTO.getCurrentUrl(); 
    	// 지라 idx
    	Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Integer jiraIdx = jira.getIdx();
		
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        // startDate와 endDate 처리
        if(IssueInputFilterDTO.getStartDate() != null && IssueInputFilterDTO.getEndDate() != null) {
        	String StrStartDate = String.valueOf(IssueInputFilterDTO.getStartDate());
            String StrEndDate = String.valueOf(IssueInputFilterDTO.getEndDate());
            startDate = LocalDateTime.parse(StrStartDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            endDate = LocalDateTime.parse(StrEndDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        Integer[] projectIdxArr = IssueInputFilterDTO.getProjectIdxArr();
        Integer[] managerIdxArr = IssueInputFilterDTO.getManagerIdxArr();
        Boolean isReporter = IssueInputFilterDTO.getIsReporter();
        Integer[] statusArr = IssueInputFilterDTO.getStatusArr();
        List<HeaderRecentIssueDTO> issueList = recentService.getRecentIssueList(jiraIdx, startDate, endDate, projectIdxArr, managerIdxArr, isReporter, statusArr, principal);
        return issueList;
    }

}