package com.mysite.jira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ChatUnreadList {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_unread_list_seq")
	@SequenceGenerator(name = "chat_unread_list_seq", sequenceName = "chat_unread_list_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private ChatMessage chatMessage;

	@Builder
	public ChatUnreadList(Account account, ChatMessage chatMessage) {
		this.account = account;
		this.chatMessage = chatMessage;
	}
}