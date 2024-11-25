document.querySelector(".project_chart").addEventListener("click", function(e) {
	const lastTd = e.target.closest(".filter_last_div");
		if(lastTd){
		document.querySelector(".filter_last_div").classList.add("active");
		document.querySelector(".last_first_div").classList.add("active");
		document.querySelector(".last_second_div").classList.add("active");
		document.querySelector(".last_td").classList.add("active");
	}
});

document.querySelector(".input_js_code").addEventListener("keyup", function(e){
		
	const issueTitleItem = e.target.closest(".input_js_code");
	if(issueTitleItem.value != "" && issueTitleItem.value != null){
		document.querySelector(".chart_issue_create_bt").style.backgroundColor = "#1868DB";
		document.querySelector(".chart_issue_create_bt").style.color = "white";
		document.querySelector(".chart_issue_create_bt").style.cursor = "not-allowed";
	}else{
		document.querySelector(".chart_issue_create_bt").style.backgroundColor = "#091E4208";
		document.querySelector(".chart_issue_create_bt").style.color = "#080F214A";
		document.querySelector(".chart_issue_create_bt").style.cursor = "pointer";
	}
});

document.querySelector(".issue_choice_box").addEventListener("click", function(e) {
	const issueChoice = document.querySelector(".issue_choice_box");
	const issueChoice2 = document.querySelector(".issue_choice_box.show");
	if(issueChoice !== null){
		document.querySelector(".issue_type_choicebox").classList.toggle("show");
	}
	
});

