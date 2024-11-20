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
public class ChatMembers {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_members_seq")
	@SequenceGenerator(name = "chat_members_seq", sequenceName = "chat_members_seq", allocationSize = 1)
	private Integer idx;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private ChatRoom chatRoom;
	
	@Builder
	public ChatMembers(Account account, ChatRoom chatRoom){
		this.account = account;
		this.chatRoom = chatRoom;
	}
}