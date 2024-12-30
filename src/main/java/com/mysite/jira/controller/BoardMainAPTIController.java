package com.mysite.jira.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.board.AIQuestionDTO;
import com.mysite.jira.dto.board.CreateIssueDTO;
import com.mysite.jira.dto.board.CreateReplyDTO;
import com.mysite.jira.dto.board.CreateStatusDTO;
import com.mysite.jira.dto.board.CreateSubIssueDTO;
import com.mysite.jira.dto.board.DeleteIssueDTO;
import com.mysite.jira.dto.board.DeleteLabelDataDTO;
import com.mysite.jira.dto.board.DeleteStatusDTO;
import com.mysite.jira.dto.board.DragIssueBoxDTO;
import com.mysite.jira.dto.board.DragIssueDTO;
import com.mysite.jira.dto.board.EpikIssueDTO;
import com.mysite.jira.dto.board.GetCurrentStatusDTO;
import com.mysite.jira.dto.board.GetLabelDTO;
import com.mysite.jira.dto.board.GetNewLabelDataDTO;
import com.mysite.jira.dto.board.GetPriorityDTO;
import com.mysite.jira.dto.board.GetStatusDataDTO;
import com.mysite.jira.dto.board.GetTeamDTO;
import com.mysite.jira.dto.board.IssueLogDTO;
import com.mysite.jira.dto.board.IssueTypeDTO;
import com.mysite.jira.dto.board.LabelListDTO;
import com.mysite.jira.dto.board.ProjectLogDTO;
import com.mysite.jira.dto.board.ReporterDTO;
import com.mysite.jira.dto.board.StatusListDTO;
import com.mysite.jira.dto.board.StatusTitleDTO;
import com.mysite.jira.dto.board.UpdateDateDTO;
import com.mysite.jira.dto.board.UpdateIssueExareaDTO;
import com.mysite.jira.dto.board.UpdateIssueNameDTO;
import com.mysite.jira.dto.board.UpdateIssuePathDTO;
import com.mysite.jira.dto.board.UpdateIssueTypeDTO;
import com.mysite.jira.dto.board.UpdateReplyDTO;
import com.mysite.jira.dto.board.VoterListDTO;
import com.mysite.jira.dto.project.upload.FileRequestDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueExtends;
import com.mysite.jira.entity.IssueFile;
import com.mysite.jira.entity.IssueLabel;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssueLikeMembers;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.IssueReply;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.ProjectLogData;
import com.mysite.jira.entity.ProjectLogStatus;
import com.mysite.jira.entity.ProjectMembers;
import com.mysite.jira.entity.Team;
import com.mysite.jira.service.AiService;
import com.mysite.jira.service.BoardMainService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class BoardMainAPTIController {
	private final BoardMainService boardMainService;
	private final AiService aiService;
	
	// 해당 Jira 에 존재하는 레이블 리스트를 반환
	@PostMapping("/get_label_list")
	public List<LabelListDTO> getLabelList(@RequestBody GetLabelDTO labelListDTO){
		List<IssueLabelData> labelList = new ArrayList<>();
		if(labelListDTO.getIdx()[0] == -1) {
			labelList = boardMainService.getLabelData(labelListDTO.getJiraIdx());
		}else {
			labelList = boardMainService.getAlterLabelData(labelListDTO.getIdx());
		}
		List<LabelListDTO> alterLabelList = new ArrayList<>();
		for(int i = 0; i < labelList.size(); i++) {
			LabelListDTO dto = LabelListDTO.builder()
									.name(labelList.get(i).getIssueLabel().getName())
									.issueIdx(labelList.get(i).getIssue().getIdx())
									.labelIdx(labelList.get(i).getIssueLabel().getIdx())
									.build();
			alterLabelList.add(dto);
		}
		return alterLabelList;
	}
	
	// 해당 프로젝트에 존재하는 상태 리스트를 현재 상태 제외하여 반환
	@PostMapping("/get_status_list")
	public List<StatusListDTO> getStatusList(@RequestBody GetCurrentStatusDTO getCurrentStatusDTO){
		List<IssueStatus> statusList = boardMainService.getSortedIssueStatus(getCurrentStatusDTO.getProjectIdx(), getCurrentStatusDTO.getStatusIdx());
		List<StatusListDTO> alterStatusList = new ArrayList<>();
		for(int i = 0; i < statusList.size(); i++) {
			IssueStatus status = statusList.get(i);
			StatusListDTO dto = StatusListDTO.builder()
								.idx(status.getIdx())
								.name(status.getName())
								.status(status.getStatus())
								.build();
			alterStatusList.add(dto);
		}
		return alterStatusList;
	}
	
	// 레이블 데이터를 생성
	@PostMapping("/create_label_data")
	public LabelListDTO addLabelData(@RequestBody GetNewLabelDataDTO getNewLabelDataDTO) {
		Issue currentIssue = boardMainService.getIssueByIdx(getNewLabelDataDTO.getIssueIdx());
		IssueLabel label = boardMainService.getIssueLabelByIdx(getNewLabelDataDTO.getLabelIdx());
		IssueLabelData newLabelData =  boardMainService.createIssueLabelData(currentIssue, label);
		LabelListDTO dto = LabelListDTO.builder()
				.name(newLabelData.getIssueLabel().getName())
				.issueIdx(newLabelData.getIssue().getIdx())
				.labelIdx(newLabelData.getIssueLabel().getIdx())
				.labelDataIdx(newLabelData.getIdx())
				.build();
		return dto;
	}
	
	// 레이블 데이터를 삭제
	@PostMapping("/delete_label_data")
	public void deleteLabelData(@RequestBody DeleteLabelDataDTO deleteLabelDataDTO) {
		boardMainService.deleteIssueLabelData(deleteLabelDataDTO.getLabelDataIdx());
	}
	
	// 해당 이슈의 상태 정보를 업데이트
	@PostMapping("/update_current_status")
	public StatusListDTO updateIssueStatus(@RequestBody GetStatusDataDTO getStatusDTO){
		IssueStatus newStatus =	boardMainService.getOnceIssueStatus(getStatusDTO.getStatusIdx());
		Issue currentIssue = boardMainService.getIssueByIdx(getStatusDTO.getIssueIdx());
		boardMainService.updateStatus(currentIssue, newStatus);
		
		StatusListDTO dto = StatusListDTO.builder()
							.idx(newStatus.getIdx())
							.name(newStatus.getName())
							.status(newStatus.getStatus())
							.build();
		return dto;
	}
	
	// Start date, 기한 정보를 업데이트
	@PostMapping("/update_date")
	public void updateStartDate(@RequestBody UpdateDateDTO updateDateDTO) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Issue issue = boardMainService.getIssueByIdx(updateDateDTO.getIssueIdx());
		LocalDateTime date = LocalDateTime.parse(updateDateDTO.getDate() + " 00:00:00", formatter);
		if(updateDateDTO.getType().contains("start")) {
			boardMainService.updateStartDate(issue, date);
		}else {
			boardMainService.updateDeadlineDate(issue, date);
		}
	}

	// 현재 값이 아닌 이슈 중요도 리스트를 반환
	@PostMapping("/get_priority_list")
	public List<GetPriorityDTO> getPriorityList(@RequestBody GetPriorityDTO getPriorityDTO) {
		List<IssuePriority> arrList = boardMainService.getAlterIssuePriority(getPriorityDTO.getPriorityIdx());
		List<GetPriorityDTO> priorityList = new ArrayList<>();
		for(int i = 0; i < arrList.size(); i++) {
			GetPriorityDTO dto = GetPriorityDTO.builder()
											   .issueIdx(getPriorityDTO.getIssueIdx())
											   .priorityIdx(arrList.get(i).getIdx())
											   .iconFilename(arrList.get(i).getIconFilename())
											   .name(arrList.get(i).getName())
											   .build();
			priorityList.add(dto);
		}
		return priorityList;
	}
	
	// 해당 이슈의 중요도 정보를 업데이트
	@PostMapping("/update_priority")
	public GetPriorityDTO updatePriority(@RequestBody GetPriorityDTO getPriorityDTO) {
		IssuePriority priority = boardMainService.getOnceIssuePriority(getPriorityDTO.getPriorityIdx());
		Issue currentIssue = boardMainService.getIssueByIdx(getPriorityDTO.getIssueIdx());
		boardMainService.updatePriority(currentIssue, priority);
		GetPriorityDTO newPriority = GetPriorityDTO.builder()
											.issueIdx(getPriorityDTO.getIssueIdx())
											.priorityIdx(getPriorityDTO.getPriorityIdx())
											.iconFilename(getPriorityDTO.getIconFilename())
											.name(getPriorityDTO.getName())
											.build();
		
		return newPriority;
	}
	
	// 해당 Jira에 존재하는 팀 리스트를 반환
	@PostMapping("/get_team_list")
	public List<GetTeamDTO> getTeamList(@RequestBody GetTeamDTO getTeamDTO){
		List<Team> arrList = boardMainService.getJiraTeamList(getTeamDTO.getJiraIdx());
		List<GetTeamDTO> teamList = new ArrayList<>();
		for(int i = 0; i < arrList.size(); i++) {
			GetTeamDTO dto = GetTeamDTO.builder()
										.teamIdx(arrList.get(i).getIdx())
										.jiraIdx(arrList.get(i).getJira().getIdx())
										.name(arrList.get(i).getName())
										.iconFilename(arrList.get(i).getAccount().getIconFilename())
										.issueIdx(arrList.get(i).getAccount().getIdx())
										.build();
			teamList.add(dto);
		}
		
		return teamList;
	}
	
	// 해당 이슈의 팀 정보를 업데이트
	@PostMapping("/update_team")
	public GetTeamDTO updateTeam(@RequestBody GetTeamDTO getTeamDTO) {
		Team team = boardMainService.getOnceTeam(getTeamDTO.getTeamIdx());
		Issue currentIssue = boardMainService.getIssueByIdx(getTeamDTO.getIssueIdx());
		boardMainService.updateTeam(currentIssue, team);
		GetTeamDTO newTeam = GetTeamDTO.builder()
										.teamIdx(team.getIdx())
										.name(team.getName())
										.iconFilename(team.getAccount().getIconFilename())
										.build();
		return newTeam;
	}
	
	// 자신을 제외한 해당 프로젝트 구성원 리스트를 반환
	@PostMapping("/get_user_list")
	public List<ReporterDTO> getReporterList(@RequestBody ReporterDTO reporterDTO){
		List<ProjectMembers> arrList = boardMainService.getReporterList(reporterDTO.getProjectIdx(), reporterDTO.getUserIdx());
		List<ReporterDTO> reporterList = new ArrayList<>();
		for(int i = 0; i < arrList.size(); i++) {
			ReporterDTO dto = ReporterDTO.builder()
										.projectIdx(reporterDTO.getProjectIdx())
										.issueIdx(reporterDTO.getIssueIdx())
										.userIdx(arrList.get(i).getAccount().getIdx())
										.name(arrList.get(i).getAccount().getName())
										.iconFilename(arrList.get(i).getAccount().getIconFilename())
										.build();
			reporterList.add(dto);
		}
		return reporterList;
	}
	
	// 해당 이슈의 담당자, 보고자 정보를 업데이트
	@PostMapping("/update_user")
	public ReporterDTO updateReporter(@RequestBody ReporterDTO reporterDTO) {
		Account account = boardMainService.getAccountById(reporterDTO.getUserIdx());
		Issue currentIssue = boardMainService.getIssueByIdx(reporterDTO.getIssueIdx());
		if(reporterDTO.getType().equals("reporter")) {
			boardMainService.updateReporter(currentIssue, account);
		}else {
			boardMainService.updateManager(currentIssue, account);
		}
		
		ReporterDTO newReporter = ReporterDTO.builder()
											.projectIdx(currentIssue.getProject().getIdx())
											.issueIdx(currentIssue.getIdx())
											.userIdx(account.getIdx())
											.name(account.getName())
											.iconFilename(account.getIconFilename())
											.build();

		return newReporter;
	}
	
	// 요청한 질문에 대한 ai의 답변 값을 반환
	@PostMapping("/get_ai_answer")
	public AIQuestionDTO getAiAnswer(@RequestBody AIQuestionDTO aiQuestionDTO) {
		String answer = aiService.send(aiQuestionDTO.getQuestion());
		AIQuestionDTO answerDTO = AIQuestionDTO.builder()
									.answer(answer)
									.build();
		return answerDTO;
	}
	
	// 상태 생성 시의 중복 확인
	@PostMapping("/status_name_check")
	public boolean statusNameCheck(@RequestBody CreateStatusDTO createStatusDTO) {
		Integer projectIdx = createStatusDTO.getProjectIdx();
		String name = createStatusDTO.getName();
		
		return boardMainService.statusNameCheck(projectIdx, name);
	}
	
	// 해당 프로젝트에 상태 생성
	@PostMapping("/create_projects_status")
	public void createStatus(@RequestBody CreateStatusDTO createStatusDTO) {
		String name = createStatusDTO.getName();
		Integer status = createStatusDTO.getStatus();
		Integer projectIdx = createStatusDTO.getProjectIdx();
		
		boardMainService.createIssueStatus(name, status, projectIdx);
	}
	
	// 이슈 생성 시의 중복 확인
	@PostMapping("/issue_name_check")
	public boolean duplicateCheck(@RequestBody CreateIssueDTO createIssueDTO) {
		Integer projectIdx = createIssueDTO.getProjectIdx();
		String name = createIssueDTO.getIssueName();
		
		return boardMainService.IssueNameCheck(projectIdx, name);
	}
	
	// 해당 프로젝트에 이슈 생성
	@PostMapping("/create_issue")
	public CreateIssueDTO createissue(@RequestBody CreateIssueDTO createIssueDTO) {
		String issueName = createIssueDTO.getIssueName();
		Integer issueTypeIdx = createIssueDTO.getIssueTypeIdx();
		Integer projectIdx = createIssueDTO.getProjectIdx();
		Integer reporterIdx = createIssueDTO.getReporterIdx();
		Integer StatusIdx = createIssueDTO.getStatusIdx();
		Integer idx = boardMainService.createIssue(issueName, projectIdx, issueTypeIdx, StatusIdx, reporterIdx);
		CreateIssueDTO dto = CreateIssueDTO.builder()
										.issueIdx(idx)
										.build();
		return dto;
	}
	
	// 프로젝트 - 보드 탭의 이슈 상태 정렬 순서를 업데이트
	@PostMapping("/update_issue_box_order")
	public void updateIssueBoxOrder(@RequestBody DragIssueBoxDTO dragIssueBoxDTO) {
		Integer oldIdx = dragIssueBoxDTO.getOldIdx();
		Integer newIdx = dragIssueBoxDTO.getNewIdx();
		Integer projectIdx = dragIssueBoxDTO.getProjectIdx();
		if(oldIdx > newIdx) {
			boardMainService.updateIssueBoxOrder(newIdx, oldIdx, projectIdx, true);
		}else {
			boardMainService.updateIssueBoxOrder(oldIdx, newIdx, projectIdx, false);
		}
	}
	
	// 프로젝트 - 보드 탭의 이슈 정렬 순서를 업데이트
	@PostMapping("/update_issue_order")
	public void updateIssueOrder(@RequestBody DragIssueDTO dragIssueDTO) {
		Integer newIdx = dragIssueDTO.getNewIdx();
		Integer oldIdx = dragIssueDTO.getOldIdx();
		Integer currentIssueIdx = dragIssueDTO.getIssueIdx();
		Integer oldStatusIdx = dragIssueDTO.getOldStatusIdx();
		Integer issueStatusIdx = dragIssueDTO.getStatusIdx();
		if(oldStatusIdx != issueStatusIdx || (oldStatusIdx == issueStatusIdx && newIdx > oldIdx)) {
			boardMainService.updatePrevIssueOrder(currentIssueIdx, oldIdx, oldStatusIdx);
		}
		boardMainService.updateIssueOrder(currentIssueIdx, newIdx, issueStatusIdx);
	}
	
	// 해당 이슈를 삭제
	@PostMapping("/delete_issue_data")
	public void deleteIssueData(@RequestBody DeleteIssueDTO deleteIssueDTO) {
		List<IssueExtends> extendsList = boardMainService.getIssueExtendsByParentIdx(deleteIssueDTO.getIssueIdx());
		for(int i = 0; i < extendsList.size(); i++) {
			boardMainService.deleteIssueData(extendsList.get(i).getChild().getIdx());
		}
		boardMainService.deleteIssueData(deleteIssueDTO.getIssueIdx());
		
	}
	
	// 해당 이슈의 이름을 업데이트
	@PostMapping("/update_issue_name")
	public void updateIssueName(@RequestBody UpdateIssueNameDTO updateIssueNameDTO) {
		Integer idx = updateIssueNameDTO.getIssueIdx();
		String name = updateIssueNameDTO.getName();
		Issue issue = boardMainService.getIssueByIdx(idx);
		boardMainService.updateIssueName(issue, name);
	}
	
	// 해당 이슈의 설명을 업데이트
	@PostMapping("/update_issue_content")
	public void updateIssueExarea(@RequestBody UpdateIssueExareaDTO updateIssueExareaDTO) {
		Integer idx = updateIssueExareaDTO.getIssueIdx();
		String content = updateIssueExareaDTO.getContent();
		Issue issue = boardMainService.getIssueByIdx(idx);
		boardMainService.updateIssueContent(issue, content);
	}
	
	// 해당 이슈에 대한 생성 / 수정 / 변경 내역 리스트를 반환
	@PostMapping("/get_issue_log_list")
	public List<IssueLogDTO> getIssueLogList(@RequestBody IssueLogDTO issueLogDTO){
		Integer issueIdx = issueLogDTO.getIssueIdx();
		String order = issueLogDTO.getOrder();
		List<ProjectLogData> getLogList = boardMainService.getLogDataList(issueIdx, order);
		List<IssueLogDTO> logList = new ArrayList<>();
		for(int i = 0; i < getLogList.size(); i++) {
			LocalDateTime dateTime = getLogList.get(i).getCreateDate();
			String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			IssueLogDTO dto = IssueLogDTO.builder()
									.username(getLogList.get(i).getAccount().getName())
									.iconFilename(getLogList.get(i).getAccount().getIconFilename())
									.logType(getLogList.get(i).getProjectLogStatus().getContent())
									.date(date)
									.build();
			logList.add(dto);
		}
		return logList;
	}
	
	// 해당 상태의 이름을 업데이트
	@PostMapping("/update_status_title")
	public void updateStatusTitle(@RequestBody StatusTitleDTO statusTitleDTO) {
		Integer idx = statusTitleDTO.getStatusIdx();
		IssueStatus currentStatus = boardMainService.getOnceIssueStatus(idx);
		String name = statusTitleDTO.getName();
		boardMainService.updateStatusTitle(currentStatus, name);
	}
	
	// 이슈 상태 데이터를 삭제
	@PostMapping("/delete_issue_status")
	public void deleteIssueStatus(@RequestBody DeleteStatusDTO deleteStatusDTO) {
		Integer projectIdx = deleteStatusDTO.getProjectIdx();
		Integer oldIdx = deleteStatusDTO.getStatusIdx();
		Integer newIdx = deleteStatusDTO.getNewStatusIdx();
		List<Issue> issueList = boardMainService.getIssueListByIssueStatus(projectIdx, oldIdx);
		IssueStatus newStatus = boardMainService.getOnceIssueStatus(newIdx);
		for(int i = 0; i < issueList.size(); i++) {
			boardMainService.updateStatus(issueList.get(i), newStatus);
		}
		boardMainService.deleteIssueStatus(oldIdx);
	}
	
	// 해당 이슈에 댓글 작성
	@PostMapping("/create_reply")
	public CreateReplyDTO createReply(@RequestBody CreateReplyDTO createReplyDTO) {
		Issue issue = boardMainService.getIssueByIdx(createReplyDTO.getIssueIdx());
		Account writer = boardMainService.getAccountById(createReplyDTO.getWriterIdx());
		String content = createReplyDTO.getContent();
		IssueReply reply = boardMainService.createReply(issue, writer, content);
		LocalDateTime dateTime = reply.getCreateDate();
		String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		CreateReplyDTO result = CreateReplyDTO.builder()
											.replyIdx(reply.getIdx())
											.name(reply.getAccount().getName())
											.iconFilename(reply.getAccount().getIconFilename())
											.content(reply.getContent())
											.date(date)
											.build();
											
		return result;
	}
	
	// 해당 댓글 내용을 업데이트
	@PostMapping("/update_reply")
	public void updateReply(@RequestBody UpdateReplyDTO updateReplyDTO) {
		IssueReply reply = boardMainService.getIssueReplyByIdx(updateReplyDTO.getReplyIdx());
		String content = updateReplyDTO.getContent();
		boardMainService.updateIssueReply(reply, content);
	}
	
	// 해당 댓글을 삭제
	@PostMapping("/delete_reply")
	public void deleteReply(@RequestBody UpdateReplyDTO updateReplyDTO) {
		Integer replyIdx = updateReplyDTO.getReplyIdx();
		boardMainService.deleteIssueReply(replyIdx);
	}
	
	// 서브 이슈 추가 시 새로운 이슈를 생성할 때 지정할 유형 리스트 반환
	@PostMapping("/get_subissue_type")
	public List<IssueTypeDTO> getSubissueTypeList(@RequestBody IssueTypeDTO issueTypeDTO){
		Integer projectIdx = issueTypeDTO.getProjectIdx();
		List<IssueType> typeList = boardMainService.getGeneralIssueTypeList(projectIdx, 2);
		List<IssueTypeDTO> dtoList = new ArrayList<>();
		for(int i = 0; i < typeList.size(); i++) {
			IssueTypeDTO dto = IssueTypeDTO.builder()
										.projectIdx(projectIdx)
										.idx(typeList.get(i).getIdx())
										.name(typeList.get(i).getName())
										.iconFilename(typeList.get(i).getIconFilename())
										.build();
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	// 해당 프로젝트의 고유한 서브 이슈 정보를 반환
	@PostMapping("/get_sub_task_idx")
	public IssueTypeDTO getSubTaskIDx(@RequestBody IssueTypeDTO issueTypeDTO) {
		Integer projectIdx = issueTypeDTO.getProjectIdx();
		List<IssueType> typeList = boardMainService.getGeneralIssueTypeList(projectIdx, 1);
		IssueTypeDTO dto = IssueTypeDTO.builder()
				.projectIdx(projectIdx)
				.idx(typeList.get(0).getIdx())
				.name(typeList.get(0).getName())
				.iconFilename(typeList.get(0).getIconFilename())
				.build();
		return dto;
	}
	
	// 서브 이슈 생성
	@PostMapping("/create_sub_issue")
	public CreateSubIssueDTO createSubIssue(@RequestBody CreateSubIssueDTO createSubIssueDTO) {
		Project project = boardMainService.getProjectByIdx(createSubIssueDTO.getProjectIdx());
		Jira jira = project.getJira();
		IssueType issueType = boardMainService.getIssueTypeByIdx(createSubIssueDTO.getIssueTypeIdx());
		IssueStatus issueStatus = boardMainService.getOnceIssueStatus(createSubIssueDTO.getStatusIdx());
		IssuePriority issuePriority = boardMainService.getOnceIssuePriority(3);
		Account user = boardMainService.getAccountById(createSubIssueDTO.getReporterIdx());
		String issueName = createSubIssueDTO.getName();
		
		Issue childIssue = boardMainService.createSubIssue(jira, project, issueType, issueStatus, issuePriority, user, issueName);
		Issue parentIssue = boardMainService.getIssueByIdx(createSubIssueDTO.getParentIdx());
		boardMainService.createIssueExtends(parentIssue, childIssue, project);
		
		CreateSubIssueDTO dto = CreateSubIssueDTO.builder()
												.name(issueName)
												.statusIdx(childIssue.getIssueStatus().getIdx())
												.reporterIconFilename(user.getIconFilename())
												.priorityIconFilename(issuePriority.getIconFilename())
												.issueTypeIconFilename(issueType.getIconFilename())
												.statusName(childIssue.getIssueStatus().getName())
												.issueKey(childIssue.getKey())
												.status(childIssue.getIssueStatus().getStatus())
												.build();
		return dto;
	}
	
	// 부모 이슈로 지정할 에픽 이슈 리스트를 반환
	@PostMapping("/get_epik_issue_list")
	public List<EpikIssueDTO> getEpikIssueList(@RequestBody EpikIssueDTO epikIssueDTO){
		Integer projectIdx = epikIssueDTO.getProjectIdx();
		List<Issue> list = boardMainService.getIssueByProjectIdxAndIssueTypeGrade(projectIdx, 3);
		List<EpikIssueDTO> epikList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			EpikIssueDTO dto = EpikIssueDTO.builder()
										.issueIdx(list.get(i).getIdx())
										.iconFilename(list.get(i).getIssueType().getIconFilename())
										.name(list.get(i).getName())
										.issueKey(list.get(i).getKey())
										.build();
			epikList.add(dto);
		}
		return epikList;
	}
	
	// 현재 부모로 지정된 이슈가 아닌 에픽 이슈 리스트를 반환
	@PostMapping("/get_other_epik_list")
	public List<EpikIssueDTO> getUpdateEpikIssueList(@RequestBody EpikIssueDTO epikIssueDTO){
		Integer projectIdx = epikIssueDTO.getProjectIdx();
		Integer currentIssue = epikIssueDTO.getCurrentIssue();
		List<Issue> list = boardMainService.getIssueByProjectIdxAndIssueTypeGradeAndIdxNot(projectIdx, 3, currentIssue);
		List<EpikIssueDTO> epikList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			EpikIssueDTO dto = EpikIssueDTO.builder()
										.issueIdx(list.get(i).getIdx())
										.iconFilename(list.get(i).getIssueType().getIconFilename())
										.name(list.get(i).getName())
										.issueKey(list.get(i).getKey())
										.build();
			epikList.add(dto);
		}
		return epikList;
	}
	
	// 해당 이슈의 부모 경로를 업데이트
	@PostMapping("/update_epik_issuepath")
	public UpdateIssuePathDTO updateEpikIssuePath(@RequestBody UpdateIssuePathDTO updateIssuePathDTO) {
		Integer projectIdx = updateIssuePathDTO.getProjectIdx();
		Integer childIdx = updateIssuePathDTO.getChildIdx();
		Integer oldParentIdx = updateIssuePathDTO.getOldParentIdx();
		Integer newParentIdx = updateIssuePathDTO.getNewParentIdx();
		Issue newParent = boardMainService.getIssueByIdx(newParentIdx);
		IssueExtends extend = boardMainService.getOnceIssueExtends(projectIdx, childIdx, oldParentIdx);
		boardMainService.updateEpikIssuePath(extend, newParent);
		
		UpdateIssuePathDTO dto = UpdateIssuePathDTO.builder()
												.projectIdx(projectIdx)
												.currentIdx(newParentIdx)
												.issueKey(newParent.getKey())
												.build();
		return dto;
	}
	
	// 이슈 상속 관계 생성
	@PostMapping("/create_issue_path")
	public EpikIssueDTO createIssuePath(@RequestBody EpikIssueDTO epikIssueDTO) {
		Project project = boardMainService.getProjectByIdx(epikIssueDTO.getProjectIdx());
		Issue parent = boardMainService.getIssueByIdx(epikIssueDTO.getParentIdx());
		Issue child = boardMainService.getIssueByIdx(epikIssueDTO.getChildIdx());
		
		boardMainService.createIssuePath(project, parent, child);
		
		EpikIssueDTO epik = EpikIssueDTO.builder()
										.issueKey(parent.getKey())
										.iconFilename(parent.getIssueType().getIconFilename())
										.build();
		return epik;
	}
	
	// 해당 프로젝트의 이슈 변동 사항에 대한 로그 생성
	@PostMapping("/create_project_log")
	public void createProjectLog(@RequestBody ProjectLogDTO projectLogDTO) {
		ProjectLogStatus status = boardMainService.getLogStatusByIdx(projectLogDTO.getType());
		Account creator = boardMainService.getAccountById(projectLogDTO.getUserIdx());
		Issue issue = boardMainService.getIssueByIdx(projectLogDTO.getIssueIdx());
		
		boardMainService.createProjectLogData(issue, creator, status);
	}
	
	// 해당 프로젝트의 이슈 유형 리스트 반환
	@PostMapping("/get_issue_type_list")
	public List<UpdateIssueTypeDTO> getIssueTypeList(@RequestBody UpdateIssueTypeDTO updateIssueTypeDTO){
		Integer projectIdx = updateIssueTypeDTO.getProjectIdx();
		Integer typeIdx = updateIssueTypeDTO.getIssueTypeIdx();
		List<IssueType> typeList = boardMainService.getUpdateIssueTypeList(projectIdx, typeIdx);
		List<UpdateIssueTypeDTO> dtoList = new ArrayList<>();
		for(int i = 0; i < typeList.size(); i++) {
			UpdateIssueTypeDTO dto = UpdateIssueTypeDTO.builder()
													.issueTypeIdx(typeList.get(i).getIdx())
													.name(typeList.get(i).getName())
													.iconFilename(typeList.get(i).getIconFilename())
													.build();
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	// 해당 이슈의 이슈 유형 업데이트
	@PostMapping("/update_issue_type")
	public UpdateIssueTypeDTO updateIssueType(@RequestBody UpdateIssueTypeDTO updateIssueTypeDTO) {
		Issue issue = boardMainService.getIssueByIdx(updateIssueTypeDTO.getIssueIdx());
		IssueType issueType = boardMainService.getIssueTypeByIdx(updateIssueTypeDTO.getIssueTypeIdx());
		boardMainService.updateIssueType(issue, issueType);
		UpdateIssueTypeDTO dto = UpdateIssueTypeDTO.builder()
												.projectIdx(updateIssueTypeDTO.getProjectIdx())
												.issueIdx(updateIssueTypeDTO.getIssueIdx())
												.issueTypeIdx(issueType.getIdx())
												.iconFilename(issueType.getIconFilename())
												.build();
		return dto;
	}
	
	// 해당 이슈에 투표한 사용자 리스트 반환
	@PostMapping("/get_voter_list")
	public List<VoterListDTO> getVoterList(@RequestBody VoterListDTO voterListDTO){
		Integer issueIdx = voterListDTO.getIssueIdx();
		List<IssueLikeMembers> voterList = boardMainService.getVoterListByIssueIdx(issueIdx);
		List<VoterListDTO> dtoList = new ArrayList<>();
		for(int i = 0; i < voterList.size(); i++) {
			VoterListDTO dto = VoterListDTO.builder()
										.userIdx(voterList.get(i).getAccount().getIdx())
										.name(voterList.get(i).getAccount().getName())
										.iconFilename(voterList.get(i).getAccount().getIconFilename())
										.build();
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	// 투표 버튼 클릭 시 데이터 생성
	@PostMapping("/create_vote_data")
	public void createVoteData(@RequestBody VoterListDTO voterListDTO) {
		Account user = boardMainService.getAccountById(voterListDTO.getUserIdx());
		Issue issue = boardMainService.getIssueByIdx(voterListDTO.getIssueIdx());
		boardMainService.createVoteData(user, issue);
	}
	
	// 투표 기록 삭제
	@PostMapping("/delete_vote_data")
	public void deleteVoteData(@RequestBody VoterListDTO voterListDTO) {
		IssueLikeMembers voteData = boardMainService.getOnceVoteData(voterListDTO.getUserIdx(), voterListDTO.getIssueIdx());
		boardMainService.deleteVoteData(voteData.getIdx());
	}
	
	// 해당 이슈에 첨부된 파일 리스트 반환
	@PostMapping("/get_file_list")
	public List<FileRequestDTO> getFileList(@RequestBody FileRequestDTO fileRequestDTO){
		Integer issueIdx = fileRequestDTO.getIssueIdx();
		List<IssueFile> fileList = boardMainService.getFileListByIssueIdx(issueIdx);
		List<FileRequestDTO> dtoList = new ArrayList<>();
		for(int i = 0; i < fileList.size(); i++) {
			FileRequestDTO dto = FileRequestDTO.builder()
										.userIdx(fileList.get(i).getAccount().getIdx())
										.fileIdx(fileList.get(i).getIdx())
										.issueIdx(issueIdx)
										.name(fileList.get(i).getName())
										.createDate(fileList.get(i).getUploadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
										.build();
			dtoList.add(dto);
		}
		return dtoList;
	}
}
