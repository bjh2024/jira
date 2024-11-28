package com.mysite.jira.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilityService {
	
	private final ObjectMapper objectMapper;

	public String getElapsedComment(LocalDateTime date) {
		Duration duration = Duration.between(date, LocalDateTime.now());
		String elapsedComment = duration.getSeconds() + "초 전";
		if(duration.toDays() > 365) {
			elapsedComment = duration.toDays() / 365 + "년 전";
		}else if(duration.toDays() > 30) {
			elapsedComment = duration.toDays() / 30 + "달 전";
		}else if (duration.toHours() > 24) {
			elapsedComment = duration.toDays() + "일 전";
		} else if (duration.toMinutes() > 60) {
			elapsedComment = duration.toHours() + "시간 전";
		} else if (duration.getSeconds() > 60) {
			elapsedComment = duration.toMinutes() + "분 전";
		}
		return elapsedComment;
	}
	
	public Timestamp isTimestamp(Object obj) {
		Timestamp result = null;
		if(obj != null && obj instanceof Timestamp) {
			result = objectMapper.convertValue(obj, Timestamp.class);
		}
		return result;
	}
	
	public BigDecimal isBigDecimal(Object obj) {
		BigDecimal result = null;
		if(obj != null && obj instanceof BigDecimal) {
			result = objectMapper.convertValue(obj, BigDecimal.class);
		}
		return result;
	}
	
	
}
