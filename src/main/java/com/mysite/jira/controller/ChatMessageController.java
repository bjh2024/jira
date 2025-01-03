package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.mysite.jira.dto.chatroom.ChatRoomListAccountDTO;
import com.mysite.jira.dto.chatroom.ChatRoomListDTO;
import com.mysite.jira.dto.chatroom.RequestChatMessageDTO;
import com.mysite.jira.dto.chatroom.RequestChatRoomCreateDTO;
import com.mysite.jira.dto.chatroom.ResponseChatMessageDTO;
import com.mysite.jira.entity.ChatMessage;
import com.mysite.jira.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
	
	private final ChatService chatService;
	
	private final SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/chat/room/create")
	public void createChatRoom(Principal principal, @Payload RequestChatRoomCreateDTO requestChatRoomCreateDTO, SimpMessageHeaderAccessor headerAccessor) {
		Integer jiraIdx = (Integer) headerAccessor.getSessionAttributes().get("jiraIdx");
		ChatRoomListDTO chatRoomListDTO = chatService.createChatRoom(requestChatRoomCreateDTO);
		for (ChatRoomListAccountDTO account : chatRoomListDTO.getAccountList()) {
	        messagingTemplate.convertAndSendToUser(account.getEmail(),"/topic/chat/room/" + jiraIdx, chatRoomListDTO);
	    }
	}
	
	@MessageMapping("/chat/{chatRoomIdx}") 
    @SendTo("/topic/chat/{chatRoomIdx}")
    public ResponseChatMessageDTO sendMessage(@Payload RequestChatMessageDTO requestChatMessageDTO) throws Exception {
		ChatMessage chatMessage = chatService.createMessage(requestChatMessageDTO);
		ResponseChatMessageDTO responseChatMessageDTO = ResponseChatMessageDTO.builder()
																			  .accountIdx(chatMessage.getAccount().getIdx())
																			  .iconFilename(chatMessage.getAccount().getIconFilename())
																			  .content(chatMessage.getContent())
																			  .sendDate(chatMessage.getSendDate())
																			  .build();
        return responseChatMessageDTO;
    }
}
