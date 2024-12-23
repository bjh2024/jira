package com.mysite.jira.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.mysite.jira.dto.chatroom.RequestChatMessageDTO;
import com.mysite.jira.dto.chatroom.ResponseChatMessageDTO;
import com.mysite.jira.entity.ChatMessage;
import com.mysite.jira.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
	
	private final ChatService chatService;
	
	@MessageMapping("/chat/{chatRoomIdx}") 
    @SendTo("/topic/chat/{chatRoomIdx}")
    public ResponseChatMessageDTO sendMessage(RequestChatMessageDTO requestChatMessageDTO, @DestinationVariable("chatRoomIdx") Integer chatRoomIdx) throws Exception {
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
