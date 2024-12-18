package com.mysite.jira.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.jira.dto.project.upload.FileRequestDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueFile;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.UploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issue_files")
@RequiredArgsConstructor
public class UploadController {
	private final UploadService uploadService;
	private final BoardMainService boardMainService;
	
	@PostMapping
    public FileRequestDTO uploadFile(@RequestParam("file") MultipartFile file,
    									@RequestParam("issueIdx") Integer issueIdx,
    									@RequestParam("userIdx") Integer userIdx) {
		Account user = boardMainService.getAccountById(userIdx);
		Issue issue = boardMainService.getIssueByIdx(issueIdx);
        try {
        	FileRequestDTO dto = uploadService.saveFile(file, user, issue);
            return dto;
        } catch (Exception e) {
            return null;
        }
    }
	
	@DeleteMapping("/delete/{fileIdx}")
	public void deleteFile(@PathVariable("fileIdx") Integer fileIdx) {
		IssueFile file = uploadService.getFileInfoByIdx(fileIdx);
		uploadService.deleteFile(file);
	}
}
