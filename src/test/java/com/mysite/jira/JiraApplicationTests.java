package com.mysite.jira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.ChatMembers;
import com.mysite.jira.entity.ChatMessage;
import com.mysite.jira.entity.ChatRoom;
import com.mysite.jira.entity.ChatUnreadList;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.ChatMembersRepository;
import com.mysite.jira.repository.ChatMessageRepository;
import com.mysite.jira.repository.ChatRoomRepository;
import com.mysite.jira.repository.ChatUnreadListRepository;
import com.mysite.jira.service.BoardMainService;

@SpringBootTest
class JiraApplicationTests {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;
	
	@Autowired
	private ChatMembersRepository chatMembersRepository;
	
	@Autowired
	private ChatUnreadListRepository chatUnreadListRepository;

	// Account 값 INSERT
	public void insertAccount() {
		Account account = Account.builder()
				.email("ksgkdw78@gmail.com")
				.pw("1234")
				.name("김대완")
				.iconFileName("user_icon_file1")
				.authCode("ABCD")
				.build();
		
		this.accountRepository.save(account);
	}
	
	// ChatRoom 값 INSERT
	public void insertChatRoom() {
		ChatRoom chatRoom = ChatRoom.builder()
				.name("chat1")
				.build();
		this.chatRoomRepository.save(chatRoom);
	}
	
	// ChatMessage 값 INSERT
	public void insertChatMessage() {
		Optional<Account> oa = this.accountRepository.findById(1);
		Account accountIdx = null;
		if(oa.isPresent()) {
			accountIdx = oa.get();
		}
		
		Optional<ChatRoom> or = this.chatRoomRepository.findById(1);
		ChatRoom chatRoomIdx = null;
		if(or.isPresent()) {
			chatRoomIdx = or.get();
		}
		
		ChatMessage chatMessage = ChatMessage.builder()
				.content("메시지22")
				.account(accountIdx)
				.chatRoom(chatRoomIdx)
				.build();
		
		this.chatMessageRepository.save(chatMessage);
	}
	
	// ChatMembers 값 INSERT
	public void insertChatMembers() {
		Optional<Account> oa = this.accountRepository.findById(1);
		Account accountIdx = null;
		if(oa.isPresent()) {
			accountIdx = oa.get();
		}
		
		Optional<ChatRoom> oc = this.chatRoomRepository.findById(1);
		ChatRoom chatRoomIdx = null;
		if(oa.isPresent()) {
			chatRoomIdx = oc.get();
		}
		
		ChatMembers chatMembers = ChatMembers.builder()
				.account(accountIdx)
				.chatRoom(chatRoomIdx)
				.build();
		
		this.chatMembersRepository.save(chatMembers);		
	}
	
	// ChatUnreadList 값 INSERT
	public void insertChatUnreadList() {
		Optional<Account> oa = this.accountRepository.findById(1);
		Account accountIdx = null;
		if(oa.isPresent()) {
			accountIdx = oa.get();
		}
		
		Optional<ChatMessage> oc = this.chatMessageRepository.findById(2);
		ChatMessage massageIdx = null;
		if(oc.isPresent()) {
			massageIdx = oc.get();
		}
		
		ChatUnreadList chatUnreadList = ChatUnreadList.builder()
				.account(accountIdx)
				.chatMessage(massageIdx)
				.build();
		this.chatUnreadListRepository.save(chatUnreadList);
	}
	
	@Autowired
	private BoardMainService boardMainService;
	
	@Test
	void test() {
		List<Issue> issueList = this.boardMainService.getIssuesByProjectIdx(1);
		for(int i = 0; i < issueList.size(); i++) {
			String issueType = this.boardMainService.getIssueTypeById(issueList.get(i).getIssueType().getIdx()).getName();
			System.out.println(issueList.get(i).getName() + " " + issueType);
		}
		
	}
}

