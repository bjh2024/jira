package com.mysite.jira;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.service.BoardMainService;

@SpringBootTest
class JiraApplicationTests {
	@Autowired
	private BoardMainService boardMainService;
	
	@Test
	void test() {
//		List<Object[]> countList = boardMainService.getIssueStatusByProjectIdx(1);
//		for(int i = 0; i < countList.size(); i++) {
//			System.out.println(countList.get(i)[0]);
//		}
	}
}

