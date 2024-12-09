package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.dashboard.DashboardListDTO;
import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.repository.DashboardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final DashboardRepository dashboardRepository;
	
	private final UtilityService utilityService;
	
	public Dashboard getDashboard(Integer dashboardIdx) {
		Optional<Dashboard> dashboard = dashboardRepository.findById(dashboardIdx);
		if(!dashboard.isEmpty())
			return dashboard.get();
		return null;
	}
	
	public List<DashboardListDTO> getDashboardList(Integer accountIdx, Integer jiraIdx){
		List<Map<String, Object>> dashboardList = dashboardRepository.findByDashboardList(accountIdx, jiraIdx);
		List<DashboardListDTO> result = new ArrayList<>();
		
		for(int i = 0; i < dashboardList.size(); i++) {
			Dashboard dashboard = null;
			if(dashboardList.get(i).get("dashboard") instanceof Dashboard) {
				dashboard = (Dashboard)dashboardList.get(i).get("dashboard");
			}
			boolean isLike = dashboardList.get(i).get("isLike").toString().equals("true") ? true : false;
			Long likeCount = (Long)dashboardList.get(i).get("likeCount");
			DashboardListDTO dto = DashboardListDTO.builder()
												   .dashboard(dashboard)
												   .isLike(isLike)
												   .likeCount(likeCount)
												   .build();
			result.add(dto);
		}
		return result;
	}
	
}
