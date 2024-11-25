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
		
	}
}

