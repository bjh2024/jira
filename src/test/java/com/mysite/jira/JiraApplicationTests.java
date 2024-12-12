package com.mysite.jira;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.jira.email.EmailClient;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.repository.IssueStatusRepository;

@SpringBootTest
class JiraApplicationTests {
	@Autowired
	private EmailClient emailClient;
	
	@Autowired
	private IssueStatusRepository statusRepository;
	
	@Test
	void test() {
		// emailClient.sendEmail("ksgkdw78@naver.com", "DLFK");
		// emailClient.sendEmail("kritac.ndh0521@gmail.com", "DLFK");
		
		// emailClient.testMailConnection();
		List<IssueStatus> list = statusRepository.findByDivOrderBetweenAndProjectIdx(3, 3, 1);
		System.out.println(list.size());
	}
}

