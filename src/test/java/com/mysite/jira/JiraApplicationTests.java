package com.mysite.jira;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.ChatMembers;
import com.mysite.jira.entity.ChatMessage;
import com.mysite.jira.entity.ChatRoom;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.ChatMembersRepository;
import com.mysite.jira.repository.ChatMessageRepository;
import com.mysite.jira.repository.ChatRoomRepository;

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

	// Account 값 INSERT
	public void createAccount() {
		Account account = Account.builder()
				.email("ksgkdw78@gmail.com")
				.pw("1234")
				.name("김대완")
				.iconFileName("user_icon_file1")
				.authCode("ABCD")
				.build();
		
		this.accountRepository.save(account);
	}
	
	public void createChatRoom() {
		ChatRoom chatRoom = ChatRoom.builder()
				.name("chat1")
				.build();
		this.chatRoomRepository.save(chatRoom);
	}
	
	public void createChatMessage() {
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
	
	public void createChatMembers() {
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
	
	@Test
	void test() {
		
		// createAccount();
		
		// ChatRoom 값 INSERT
		// createChatRoom();
		
		// createChatMessage();
		
		createChatMembers();
	}

}
