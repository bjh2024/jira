package com.mysite.jira.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueTypeListDTO {

	private String name;
    private String iconFilename;
    
    @Builder
    public IssueTypeListDTO(String name, String iconFilename) {
    	  this.name = name;
          this.iconFilename = iconFilename;
    }
}
