// 팀 만들기 모달창 on
document
	.querySelectorAll(".team_list_container .team_create_btn").forEach(function(btn) {
		btn.addEventListener("click", function() {
			document.querySelector(".team_create_modal").classList.add("show");
		});
	});

// 사용자 추가 모달창 on
document
	.querySelector(".team_list_container .jira_user_invite_btn")
	.addEventListener("click", function() {
		document.querySelector(".jira_user_add_modal").classList.add("show");
	});