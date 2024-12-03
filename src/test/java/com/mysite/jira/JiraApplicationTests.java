package com.mysite.jira;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.jira.email.EmailClient;

@SpringBootTest
class JiraApplicationTests {
	@Autowired
	private EmailClient emailClient;
	
	@Test
	void test() {
		// emailClient.sendEmail("ksgkdw78@naver.com", "DLFK");
		// emailClient.sendEmail("kritac.ndh0521@gmail.com", "DLFK");
		
		// emailClient.testMailConnection();
	}
}

