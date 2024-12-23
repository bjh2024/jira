package com.mysite.jira.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.chatroom.ChatMessageListDTO;
import com.mysite.jira.dto.chatroom.ChatRoomListAccountDTO;
import com.mysite.jira.dto.chatroom.ChatRoomListChatRoomDTO;
import com.mysite.jira.dto.chatroom.ChatRoomListDTO;
import com.mysite.jira.dto.chatroom.RequestChatMessageDTO;
import com.mysite.jira.dto.chatroom.RequestChatRoomCreateDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.ChatMembers;
import com.mysite.jira.entity.ChatMessage;
import com.mysite.jira.entity.ChatRoom;
import com.mysite.jira.repository.ChatMembersRepository;
import com.mysite.jira.repository.ChatMessageRepository;
import com.mysite.jira.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final AccountService accountService;
	
	private final ChatMembersRepository chatMembersRepository;
	
	private final ChatMessageRepository chatMessageRepository;
	
	private final ChatRoomRepository chatRoomRepository;
	
	public ChatRoom getChatRoomByIdx(Integer idx) {
		Optional<ChatRoom> opChatRoom = chatRoomRepository.findById(idx);
		ChatRoom chatRoom = null;
		if(!opChatRoom.isEmpty()) {
			chatRoom = opChatRoom.get();
		}
		return chatRoom;
	}
	
	public List<ChatRoomListDTO> getChatRoomList(Integer accountIdx){
		List<ChatMembers> accountIdxChatMemberList = chatMembersRepository.findByAccount_Idx(accountIdx);
		List<ChatRoomListDTO> result = new ArrayList<>();
		for(int i = 0; i < accountIdxChatMemberList.size(); i++) {
			ChatRoom chatRoom = accountIdxChatMemberList.get(i).getChatRoom();
			
			ChatRoomListChatRoomDTO chatRoomListChatRoomDTO = ChatRoomListChatRoomDTO.builder()
																					 .idx(chatRoom.getIdx())
																					 .name(chatRoom.getName())
																					 .build();
			
			Integer chatRoomIdx = accountIdxChatMemberList.get(i).getChatRoom().getIdx();
			List<ChatMembers> chatRoomIdxChatMemberList =  chatMembersRepository.findByChatRoom_Idx(chatRoomIdx);
			
			List<ChatRoomListAccountDTO> accountList = new ArrayList<>();
			int end = chatRoomIdxChatMemberList.size() < 4 ? chatRoomIdxChatMemberList.size() : 4;
			for(int j = 0; j < end; j++) {
				Account account = chatRoomIdxChatMemberList.get(j).getAccount();
				ChatRoomListAccountDTO dto = ChatRoomListAccountDTO.builder()
																   .idx(account.getIdx())
																   .name(account.getName())
																   .iconFilename(account.getIconFilename())
																   .build();
				accountList.add(dto);
			}
			
			ChatMessage chatMessage = chatMessageRepository.findTop1ByChatRoomIdx(chatRoomIdx);
			String lastContent = chatMessage != null ?  chatMessage.getContent() : "대화를 시작하세요";
			LocalDateTime lastSendDate = chatMessage != null ? chatMessage.getSendDate() : null;
			
			ChatRoomListDTO dto = ChatRoomListDTO.builder()
												 .accountList(accountList)
												 .chatRoom(chatRoomListChatRoomDTO)
												 .lastContent(lastContent)
												 .lastSendDate(lastSendDate)
												 .build();
			result.add(dto);
		}
		
		result.sort(new Comparator<ChatRoomListDTO>() {
			@Override
			public int compare(ChatRoomListDTO d1, ChatRoomListDTO d2) {
				if(d1.getLastSendDate() == null) {
					return 1;
				}
				if(d1.getLastSendDate().isAfter(d2.getLastSendDate())) {
					return -1;
				}else {
					return 1;
				}
			}
		});
		
		return result;
	}
	
	public List<ChatMessageListDTO> getChatMessageList(Integer ChatRoomIdx){
		List<ChatMessage> chatMessageList = chatMessageRepository.findByChatRoom_IdxOrderBySendDate(ChatRoomIdx);
		List<ChatMessageListDTO> result = new ArrayList<>();
		for(int i = 0; i < chatMessageList.size(); i++) {
			ChatMessage message = chatMessageList.get(i);
			
			ChatMessageListDTO dto = ChatMessageListDTO.builder()
													   .accountIdx(message.getAccount().getIdx())
													   .accountIconFilename(message.getAccount().getIconFilename())
													   .content(message.getContent())
													   .sendDate(message.getSendDate())
													   .build();
			result.add(dto);
		}
		return result;
	}
	
	public ChatMessage createMessage(RequestChatMessageDTO chatMessageDTO) {
		Account account = accountService.getAccountByIdx(chatMessageDTO.getAccountIdx());
		ChatRoom chatRoom = this.getChatRoomByIdx(chatMessageDTO.getChatRoomIdx());
		
		ChatMessage chatMessage = ChatMessage.builder()
											 .content(chatMessageDTO.getContent())
											 .account(account)
											 .chatRoom(chatRoom)
											 .build();
		chatMessageRepository.save(chatMessage);
		return chatMessage;
	}
	
	public Integer createChatRoom(RequestChatRoomCreateDTO requestChatRoomCreateDTO) {
		String chatRoomName = requestChatRoomCreateDTO.getName();
		ChatRoom chatRoom = ChatRoom.builder()
								    .name(chatRoomName)
								    .build();
		
		chatRoomRepository.save(chatRoom);
		
		Integer[] chatAccountIdxList = requestChatRoomCreateDTO.getChatAccountIdxList();
		List<ChatMembers> chatMembersList = new ArrayList<>();
		for(int i = 0; i < chatAccountIdxList.length; i++) {
			Account account = accountService.getAccountByIdx(chatAccountIdxList[i]);
			ChatMembers chatMember = ChatMembers.builder()
												 .chatRoom(chatRoom)
												 .account(account)
												 .build();
			chatMembersList.add(chatMember);
		}
		chatMembersRepository.saveAll(chatMembersList);
		
		return 1;
	}
}
