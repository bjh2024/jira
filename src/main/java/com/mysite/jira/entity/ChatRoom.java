package com.mysite.jira.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
	@SequenceGenerator(name = "chat_seq", sequenceName = "chat_seq", allocationSize = 1)
	private Integer idx; 
	
	@Column(columnDefinition = "VARCHAR2(100)")
	@NotNull
	private String name;
	
	@Builder
	public ChatRoom(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE) 
	private List<ChatMembers> chatMembersList;
	
	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE) 
	private List<ChatMessage> chatMessageList;
	
	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE) 
	private List<ChatEmojiRecord> chatEmojiList;
}
