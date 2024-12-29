package com.mysite.jira;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.ProjectMembers;
import com.mysite.jira.entity.ProjectRecentClicked;
import com.mysite.jira.repository.ProjectMembersRepository;
import com.mysite.jira.repository.ProjectRecentClickedRepository;
import com.mysite.jira.repository.ProjectRepository;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.JiraService;

@SpringBootTest
class JiraApplicationTests {
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectMembersRepository projectMembersRepository;
	
	@Autowired
	private ProjectRecentClickedRepository projectRecentClickedRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private JiraService jiraService;
	
	@Test
	void test() {
		/*
		 * try { Document doc = new Document(uploadPath + "7계층.txt"); for (int page = 0;
		 * page < doc.getPageCount(); page++){ Document extractedPage =
		 * doc.extractPages(page, 1); extractedPage.save(String.format(uploadPath +
		 * "%s_converted.png", "7계층")); } } catch (Exception e) { e.printStackTrace(); }
		 */
		
		for(int i = 0; i < 100; i++) {
			Account account = accountService.getAccountByIdx(1);
			Jira jira = jiraService.getByIdx(1);
			
			Project project = Project.builder()
									 .key("QWER")
									 .color("#C6EDFB")
									 .iconFilename("project_icon_file1.png")
									 .name("테스트" + i)
									 .sequence(0)
									 .account(account)
									 .jira(jira)
									 .build();
			projectRepository.save(project);
			
			ProjectMembers projectMembers = ProjectMembers.builder()
														  .project(project)
														  .account(account)
														  .auth_type(3)
														  .build();
			projectMembersRepository.save(projectMembers);
			
			ProjectRecentClicked projectRecentClicked = ProjectRecentClicked.builder()
																		    .account(account)
																		    .project(project)
																		    .jira(jira)
																		    .build();
			projectRecentClickedRepository.save(projectRecentClicked);
		}
	}
}

