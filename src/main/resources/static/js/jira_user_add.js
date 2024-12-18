// modal창 끄기
const jiraUserModal = document.querySelector(".jira_user_add_modal");
// modal창 껴졌을때 input 지우기
function removeJiraInput(){
	const jiraUserNameInput = document.querySelector("#jiraUserName");
	jiraUserNameInput.value = "";
	jiraUserNameInput.classList.remove("alert");
	
	const alertBox = document.querySelector(".jira_alert_box");
	alertBox.classList.remove("show");
}

jiraUserModal.addEventListener("click", function(e) {
	if (e.target.closest(".jira_user_add_container") === null) {
		removeJiraInput()
		this.classList.remove("show");
	}
});

document.querySelector(".jira_user_add_box > .btn_box .cancle_btn").addEventListener("click", function() {
		removeJiraInput()
		jiraUserModal.classList.remove("show");
});

document.querySelector("#jiraUserName").addEventListener("keyup", function(){
	if(this.value.trim() !== ""){
		this.classList.remove("alert");
	}else{
		this.classList.add("alert");
	}
});

document.querySelector("#jiraUserName").addEventListener("focus", function(){
	this.classList.add("active");
});

document.querySelector("#jiraUserName").addEventListener("blur", function(){
	this.classList.remove("active");
});

document.querySelector(".jira_user_add_container .jira_user_add_btn").addEventListener("click", async function(){
	const jiraUserNameInput = document.querySelector("#jiraUserName");
	const jiraUserName = document.querySelector("#jiraUserName").value.trim();
	const alertBox = document.querySelector(".jira_alert_box");
	
	// 유효성 검사1 빈값
	if(jiraUserName === "" || jiraUserName === null) {
		jiraUserNameInput.classList.add("alert");
		alertBox.classList.add("show");
		alertBox.querySelector(".comment").innerText = "사용자를 한 명 선택하세요";
		jiraUserNameInput.focus();
		return;
	}
	// 유효성 검사2 alert_box가 show인지
	if(alertBox.className.includes("show")) {
		jiraUserNameInput.classList.add("alert");
		jiraUserNameInput.focus();
		return;
	}
	// 유효성 검사3 지라에 속한 유저 인지
	async function jiraMemberDuplicationFetch(email){
		const uri = `/api/jira/duplication/member?email=${email}&uri=${window.location.pathname}`
		
		const res = await fetch(uri, {method:"get"})
		const data = await res.json();
		
		if(data === 1){
			return false;
		}
		return true;
	}
	
	if(!await jiraMemberDuplicationFetch(jiraUserName)){
		
	}
});