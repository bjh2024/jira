package com.mysite.jira.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueTypeListDto {

	private String name;
    private String iconFilename;
    
    @Builder
    public IssueTypeListDto(String name, String iconFilename) {
    	  this.name = name;
          this.iconFilename = iconFilename;
    }
}
