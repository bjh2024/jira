package com.mysite.jira.dto;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.jira.service.UtilityService;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AllRecentDTO {
	
	private String iconFilename;
	private String name;
	private String projectName;
	private String key;
	private String elapsedTime;
	
	@Builder
	public AllRecentDTO(String iconFilename, String name, String projectName, String key, String elapsedTime) {
		this.iconFilename = iconFilename;
		this.name = name;
		this.projectName = projectName;
		this.key = key;
		this.elapsedTime = elapsedTime;
	}
	
}
