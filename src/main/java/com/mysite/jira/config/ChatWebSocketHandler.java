package com.mysite.jira.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebSocketMessageBroker
public class ChatWebSocketHandler extends TextWebSocketHandler{

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		
		session.sendMessage(new TextMessage("서버에서 받은 메세지"+payload));
	}
	
}
