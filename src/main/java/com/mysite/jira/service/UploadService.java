package com.mysite.jira.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.words.Document;
import com.mysite.jira.dto.project.upload.FileRequestDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueFile;
import com.mysite.jira.repository.IssueFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadService {
	private final IssueFileRepository issueFileRepository;
	
	@Value("${upload.path}") 
    private String uploadPath;
	
    public FileRequestDTO saveFile(MultipartFile file, Account user, Issue issue){
        // 파일 저장
        String originalFilename = file.getOriginalFilename();
        Path filePath = Paths.get(uploadPath, originalFilename);
        String[] splitedFilename = originalFilename.split("\\.");
        
        try {
        	if(splitedFilename.length > 2) {
        		return null;
        	}
        	Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        if(splitedFilename[splitedFilename.length - 1].equals("txt")) {
        	this.convertTxtToImg(filePath.toAbsolutePath().toString(), splitedFilename[0]);
        }
        
        if(originalFilename.equals("error")) {
        	System.out.println("텍스트 파일 변환 중 에러 발생");
        	return null;
        }
        
        // 데이터베이스에 저장
        IssueFile issueFile = IssueFile.builder()
            .name(originalFilename)
            .uploadDate(LocalDateTime.now())
            .account(user)
            .issue(issue)
            .build();
        this.issueFileRepository.save(issueFile);
        
        FileRequestDTO dto = FileRequestDTO.builder()
						        		.userIdx(user.getIdx())
						        		.fileIdx(issueFile.getIdx())
						        		.issueIdx(issue.getIdx())
        								.name(originalFilename)
        								.createDate(issueFile.getUploadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        								.build();
        return dto;
    }
    
    public String convertTxtToImg(String path, String filename) {
    	try {
			Document doc = new Document(path);
			for (int page = 0; page < doc.getPageCount(); page++){
				Document extractedPage = doc.extractPages(page, 1);
				extractedPage.save(String.format(uploadPath + "%s_converted.png", filename));
			}
			return filename + "_converted.png";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
    }
    
    public IssueFile getFileInfoByIdx(Integer idx) {
    	Optional<IssueFile> optFile = this.issueFileRepository.findById(idx);
    	return optFile.get();
    }
    
    public void deleteFile(IssueFile file) {
    	Path filePath = Paths.get(uploadPath, file.getName());
    	String[] fileType = file.getName().split("\\.");
    	if(fileType[1].equals("txt")) {
    		Path imgPath = Paths.get(uploadPath, fileType[0] + "_converted.png");
    		try {
        		if (Files.exists(imgPath)) {
        			Files.delete(imgPath);
        			System.out.println("이미지 파일 삭제됨: " + imgPath.toAbsolutePath());
        		} else {
        			throw new FileNotFoundException("파일을 찾을 수 없습니다: " + imgPath.toAbsolutePath());
        		}
        	}catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	try {
    		if (Files.exists(filePath)) {
    			Files.delete(filePath);
    			System.out.println("파일 삭제됨: " + filePath.toAbsolutePath());
    			this.issueFileRepository.delete(file);
    		} else {
    			throw new FileNotFoundException("파일을 찾을 수 없습니다: " + filePath.toAbsolutePath());
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
}
