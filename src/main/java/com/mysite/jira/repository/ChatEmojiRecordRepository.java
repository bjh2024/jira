package com.mysite.jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.jira.entity.ChatEmojiRecord;

public interface ChatEmojiRecordRepository extends JpaRepository<ChatEmojiRecord, Integer> {

}
