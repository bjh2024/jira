package com.mysite.jira;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.aspose.words.Document;
import com.mysite.jira.email.EmailClient;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.repository.IssueStatusRepository;

@SpringBootTest
class JiraApplicationTests {
	@Autowired
	private EmailClient emailClient;
	
	@Autowired
	private IssueStatusRepository statusRepository;
	
	@Value("${upload.path}") 
    private String uploadPath;
	
	@Test
	void test() {
		/*
		 * try { Document doc = new Document(uploadPath + "7계층.txt"); for (int page = 0;
		 * page < doc.getPageCount(); page++){ Document extractedPage =
		 * doc.extractPages(page, 1); extractedPage.save(String.format(uploadPath +
		 * "%s_converted.png", "7계층")); } } catch (Exception e) { e.printStackTrace(); }
		 */
	}
}

