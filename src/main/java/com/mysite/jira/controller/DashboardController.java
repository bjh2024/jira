package com.mysite.jira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@GetMapping("/list")
	public String listPage() {
		return "dashboard/dashboard_list";
	}
	@GetMapping("/detail")
	public String detailPage(HttpServletRequest request,Model model) {
		
		model.addAttribute("url", request.getServletPath());
		return "dashboard/dashboard_detail";
	}
	@GetMapping("/detail/edit")
	public String detailEditPage(HttpServletRequest request,Model model) {
		
		model.addAttribute("url", request.getServletPath());
		return "dashboard/dashboard_detail_edit";
	}
}
