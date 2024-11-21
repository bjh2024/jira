package com.mysite.jira;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.jira.entity.Jira;
import com.mysite.jira.repository.JiraMembersRepository;
import com.mysite.jira.repository.JiraRepository;

@SpringBootTest
class JiraApplicationTests {
	
	@Autowired
	private JiraRepository jiraRepository;
	
	@Autowired
	private JiraMembersRepository jiraMembersRepository;
	
	@Test
	void test() {
	}
}

