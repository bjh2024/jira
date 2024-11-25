package com.mysite.jira;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.repository.IssueRepository;

@SpringBootTest
class JiraApplicationTests {
	
	@Autowired
	private IssueRepository issueRepository;
	
	@Test
	void test() {
		List<Issue> issueList = issueRepository.findByJiraIdx(1);
			System.out.println(issueList.get(0).getName());
			System.out.println(issueList.get(0).getKey());
			System.out.println(issueList.get(0).getIssueType().getIconFilename());
			System.out.println(issueList.get(0).getManager().getIconFileName());
	}
}

