package com.mysite.jira.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.mysite.jira.dto.chatroom.ChatMessageDTO;
import com.mysite.jira.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
	
	private final ChatService chatService;
	
	@MessageMapping("/chat/{chatRoomIdx}")
    @SendTo("/topic/chat/{chatRoomIdx}")
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO, @DestinationVariable("chatRoomIdx") Integer chatRoomIdx) throws Exception {
		chatService.createMessage(chatMessageDTO);
        return chatMessageDTO;
    }
	
}
