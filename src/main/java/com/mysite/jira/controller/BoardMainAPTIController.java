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
	
	@PostMapping("/delete_label_data")
	public void deleteLabelData(@RequestBody DeleteLabelDataDTO deleteLabelDataDTO) {
		boardMainService.deleteIssueLabelData(deleteLabelDataDTO.getLabelDataIdx());
	}
	
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
	
	@PostMapping("/get_ai_answer")
	public AIQuestionDTO getAiAnswer(@RequestBody AIQuestionDTO aiQuestionDTO) {
		String answer = aiService.send(aiQuestionDTO.getQuestion());
		AIQuestionDTO answerDTO = AIQuestionDTO.builder()
									.answer(answer)
									.build();
		return answerDTO;
	}
	
	@PostMapping("/create_projects_status")
	public void createStatus(@RequestBody CreateStatusDTO createStatusDTO) {
		String name = createStatusDTO.getName();
		Integer status = createStatusDTO.getStatus();
		Integer projectIdx = createStatusDTO.getProjectIdx();
		
		boardMainService.createIssueStatus(name, status, projectIdx);
	}
	
	@PostMapping("/issue_name_check")
	public boolean duplicateCheck(@RequestBody CreateIssueDTO createIssueDTO) {
		Integer projectIdx = createIssueDTO.getProjectIdx();
		String name = createIssueDTO.getIssueName();
		
		return boardMainService.IssueNameCheck(projectIdx, name);
	}
	
	@PostMapping("/create_issue")
	public CreateIssueDTO createissue(@RequestBody CreateIssueDTO createIssueDTO) {
		String issueName = createIssueDTO.getIssueName();
		String jiraName = createIssueDTO.getJiraName();
		Integer issueTypeIdx = createIssueDTO.getIssueTypeIdx();
		Integer projectIdx = createIssueDTO.getProjectIdx();
		Integer reporterIdx = createIssueDTO.getReporterIdx();
		Integer StatusIdx = createIssueDTO.getStatusIdx();
//		System.out.println(createIssueDTO.getIssueName() + " " + createIssueDTO.getJiraName() + " " + 
//				createIssueDTO.getIssueTypeIdx() + " " + createIssueDTO.getProjectIdx() + " " + 
//				createIssueDTO.getReporterIdx() + " " + createIssueDTO.getStatusIdx());
		Integer idx = boardMainService.createIssue(issueName, jiraName, projectIdx, issueTypeIdx, StatusIdx, reporterIdx);
		CreateIssueDTO dto = CreateIssueDTO.builder()
										.issueIdx(idx)
										.build();
		return dto;
	}
	
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
	
	@PostMapping("/delete_issue_data")
	public void deleteIssueData(@RequestBody DeleteIssueDTO deleteIssueDTO) {
		List<IssueExtends> extendsList = boardMainService.getIssueExtendsByParentIdx(deleteIssueDTO.getIssueIdx());
		for(int i = 0; i < extendsList.size(); i++) {
			boardMainService.deleteIssueData(extendsList.get(i).getChild().getIdx());
		}
		boardMainService.deleteIssueData(deleteIssueDTO.getIssueIdx());
		
	}
	
	@PostMapping("/update_issue_name")
	public void updateIssueName(@RequestBody UpdateIssueNameDTO updateIssueNameDTO) {
		Integer idx = updateIssueNameDTO.getIssueIdx();
		String name = updateIssueNameDTO.getName();
		Issue issue = boardMainService.getIssueByIdx(idx);
		boardMainService.updateIssueName(issue, name);
	}
	
	@PostMapping("/update_issue_content")
	public void updateIssueExarea(@RequestBody UpdateIssueExareaDTO updateIssueExareaDTO) {
		Integer idx = updateIssueExareaDTO.getIssueIdx();
		String content = updateIssueExareaDTO.getContent();
		Issue issue = boardMainService.getIssueByIdx(idx);
		boardMainService.updateIssueContent(issue, content);
	}
	
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
	
	@PostMapping("/get_all_issue_log_list")
	public List<IssueLogDTO> getAllIssueLogList(@RequestBody IssueLogDTO issueLogDTO){
		Integer issueIdx = issueLogDTO.getIssueIdx();
		String order = issueLogDTO.getOrder();
		List<ProjectLogData> getLogList = boardMainService.getAllLogDataList(issueIdx, order);
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

	@PostMapping("/update_status_title")
	public void updateStatusTitle(@RequestBody StatusTitleDTO statusTitleDTO) {
		Integer idx = statusTitleDTO.getStatusIdx();
		IssueStatus currentStatus = boardMainService.getOnceIssueStatus(idx);
		String name = statusTitleDTO.getName();
		boardMainService.updateStatusTitle(currentStatus, name);
	}
	
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
	
	@PostMapping("/update_reply")
	public void updateReply(@RequestBody UpdateReplyDTO updateReplyDTO) {
		IssueReply reply = boardMainService.getIssueReplyByIdx(updateReplyDTO.getReplyIdx());
		String content = updateReplyDTO.getContent();
		boardMainService.updateIssueReply(reply, content);
	}
	
	@PostMapping("/delete_reply")
	public void deleteReply(@RequestBody UpdateReplyDTO updateReplyDTO) {
		Integer replyIdx = updateReplyDTO.getReplyIdx();
		boardMainService.deleteIssueReply(replyIdx);
	}
	
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
	
	@PostMapping("/create_sub_issue")
	public CreateSubIssueDTO createSubIssue(@RequestBody CreateSubIssueDTO createSubIssueDTO) {
		Project project = boardMainService.getProjectByIdx(createSubIssueDTO.getProjectIdx());
		Jira jira = boardMainService.getJiraByJiraName(createSubIssueDTO.getJiraName());
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
	
	@PostMapping("/create_project_log")
	public void createProjectLog(@RequestBody ProjectLogDTO projectLogDTO) {
		ProjectLogStatus status = boardMainService.getLogStatusByIdx(projectLogDTO.getType());
		Account creator = boardMainService.getAccountById(projectLogDTO.getUserIdx());
		Issue issue = boardMainService.getIssueByIdx(projectLogDTO.getIssueIdx());
		
		boardMainService.createProjectLogData(issue, creator, status);
	}
	
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
	
	@PostMapping("/create_vote_data")
	public void createVoteData(@RequestBody VoterListDTO voterListDTO) {
		Account user = boardMainService.getAccountById(voterListDTO.getUserIdx());
		Issue issue = boardMainService.getIssueByIdx(voterListDTO.getIssueIdx());
		boardMainService.createVoteData(user, issue);
	}
	
	@PostMapping("/delete_vote_data")
	public void deleteVoteData(@RequestBody VoterListDTO voterListDTO) {
		IssueLikeMembers voteData = boardMainService.getOnceVoteData(voterListDTO.getUserIdx(), voterListDTO.getIssueIdx());
		boardMainService.deleteVoteData(voteData.getIdx());
	}
	
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
