package com.mysite.jira.service;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.IssueType;
import com.mysite.jira.repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterIssueService {
	
	private final IssueRepository issueRepository;
 
	// email 값으로 account의 index값 추출
	// 추출한 index값으로 issue 테이블에서 이슈의 이름을 사용
	// issue_type_idx값으로 이슈의 icon_filename을 가져오기
	
}
