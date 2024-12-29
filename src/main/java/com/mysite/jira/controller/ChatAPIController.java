package com.mysite.jira.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.chatroom.ChatMessageListDTO;
import com.mysite.jira.dto.chatroom.ChatRoomListDTO;
import com.mysite.jira.dto.chatroom.RequestChatRoomCreateDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatAPIController {

	private final ChatService chatService;
	
	private final AccountService accountService;
	
	@GetMapping("room/list")
	public List<ChatRoomListDTO> getChatRoomList(Principal principal){
		Account account = accountService.getAccountByEmail(principal.getName());
		return chatService.getChatRoomList(account.getIdx());
	}
	
	@GetMapping("room/detail")
	public List<ChatMessageListDTO> getChatMessageList(@RequestParam("chatRoomIdx") Integer chatRoomIdx){
		return chatService.getChatMessageList(chatRoomIdx);
	}
	
	@PostMapping("room/create")
	public ChatRoomListDTO createChatRoom(@RequestBody RequestChatRoomCreateDTO requestChatRoomCreateDTO){
		return chatService.createChatRoom(requestChatRoomCreateDTO);
	}
	
}
