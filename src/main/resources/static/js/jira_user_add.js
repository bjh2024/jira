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
	const alertBox = document.querySelector(".jira_alert_box");
	if(this.value.trim() !== ""){
		this.classList.remove("alert");
		alertBox.classList.remove("show");
		alertBox.querySelector(".comment").innerText = "필수 입력 항목입니다.";
	}else{
		this.classList.add("alert");
		alertBox.classList.add("show");
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
	const jiraUserEmail = document.querySelector("#jiraUserName").value.trim();
	const alertBox = document.querySelector(".jira_alert_box");
	
	// 유효성 검사1 빈값
	if(jiraUserEmail === "" || jiraUserEmail === null) {
		jiraUserNameInput.classList.add("alert");
		alertBox.classList.add("show");
		alertBox.querySelector(".comment").innerText = "사용자를 입력해주세요";
		jiraUserNameInput.focus();
		return;
	}
	console.log(jiraUserEmail.split("@").length !== 2);
	// 유효성 검사2 이메일 형식인지
	if(jiraUserEmail.split("@").length !== 2 || jiraUserEmail.split(".")[jiraUserEmail.split(".").length - 1] !== "com"){
		jiraUserNameInput.classList.add("alert");
		alertBox.classList.add("show");
		alertBox.querySelector(".comment").innerText = "이메일 형식으로 작성하세요 ex) kdw@gmail.com";
		jiraUserNameInput.focus();
		return;
	}
	
	// 유효성 검사3 alert_box가 show인지
	if(alertBox.className.includes("show")) {
		jiraUserNameInput.classList.add("alert");
		jiraUserNameInput.focus();
		return;
	}
	// 유효성 검사4 지라에 속한 유저 인지
	async function jiraMemberDuplicationFetch(email){
		const uri = `/api/account/duplication/jira_member?email=${email}&uri=${window.location.pathname}`
		
		const res = await fetch(uri, {method:"get"})
		const data = await res.json();
		
		if(data === 1){
			return false;
		}
		return true;
	}
	
	if(!await jiraMemberDuplicationFetch(jiraUserEmail)){
		jiraUserNameInput.classList.add("alert");
		alertBox.classList.add("show");
		alertBox.querySelector(".comment").innerText = "이미 지라에 존재하는 사용자 입니다.";
		jiraUserNameInput.focus();
	}
	
	function addJiraUserFetch(jiraUserEmail){
		const uri = "/api/account/add/jira_member/send_email"
		
		fetch(uri, {method:"post", 
					headers:{"Content-Type" : "application/json"}, 
					body:JSON.stringify({"email":jiraUserEmail})
					 }
			 )
		 .then(res => res.json())
		 .then(res => {
			if(res === 1){
				alert("이메일이 발송되었습니다.")
			}
		 })
		.catch(err => {
			console.error(err);
		});
	}
	addJiraUserFetch();
});