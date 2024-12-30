// modal 끄기
const teamCreateModal = document.querySelector(".team_create_modal");

// modal창 껴졌을때 reset
function removeTeamInput() {
	const inputs = document.querySelectorAll(".team_create_content_box .input_box input");
	inputs.forEach(function(input) {
		input.value = "";
	});
	
	const alertBox = document.querySelector(".team_alert_box");
	alertBox.classList.remove("show");
	const teamNameInput = document.querySelector("#teamName");
	teamNameInput.classList.remove("alert");
}

teamCreateModal.addEventListener("click", function(e) {
	if (e.target.closest(".team_create_container") === null) {
		removeTeamInput();
		this.classList.remove("show");
	}
});

document.querySelector(".team_create_container .btn_box .cancle_btn").addEventListener("click", function() {
		removeTeamInput();
		teamCreateModal.classList.remove("show");
});

document.querySelector("#teamName").addEventListener("keyup", function(){
	const alertBox = document.querySelector(".team_alert_box");
	if(this.value.trim() !== ""){
		alertBox.classList.remove("show");
		this.classList.remove("alert");
	}else{
		alertBox.classList.add("show");
		this.classList.add("alert");
		alertBox.querySelector(".comment").innerText = "팀 이름을 입력해야 합니다";
	}
})

document.querySelector("#teamName").addEventListener("focus", function(){
	this.classList.add("active");
});

document.querySelector("#teamName").addEventListener("blur", function(){
	this.classList.remove("active");
	const alertBox = document.querySelector(".team_alert_box");
	console.log(alertBox.className.includes("show"));
	if(alertBox.className.includes("show")){
		this.classList.add("alert");
	}else{
		this.classList.remove("alert");
	}
});

document.querySelector(".team_create_container .create_team_btn").addEventListener("click",async function(){
	console.log("click");
	const teamNameInput = document.querySelector("#teamName");
	const teamName = teamNameInput.value.trim();
	const alertBox = document.querySelector(".team_alert_box");
	
	// 유효성 검사1 alert_box 빈값
	if(teamName === "" || teamName === null){
		teamNameInput.focus();
		teamNameInput.classList.add("alert");
		
		alertBox.classList.add("show");
		alertBox.querySelector(".comment").innerText = "팀 이름은 필수 필드 입니다."
		return;
	}
	
	// 유효성 검사2 alert_box 유무
	if(alertBox.className.includes("show")) {
		teamNameInput.focus();
		return;
	}
	
	// 유효성 검사3 => 중복
	async function teamDuplicationFetch(){
		const uri = `/api/team/duplication/name?teamName=${teamName}`;
		const res = await fetch(uri, {method:"get"})
		const data = await res.json();
		
		if(data === 1){
			return false;
		}
		return true;
	}
	
	if(!await teamDuplicationFetch()) {
		teamNameInput.focus();
		
		alertBox.classList.add("show");
		teamNameInput.classList.add("alert");
		alertBox.querySelector(".comment").innerText = "중복되는 팀 이름입니다."
		return;
	}
	
	// 팀 저장 fetch()
	function teamCreateFetch(teamName){
		const uri = "/api/team/create";
		fetch(uri, {method:"post",
					headers:{"Content-Type" : "application/json"}, 
					body:JSON.stringify(teamName)
				 	}
		)
		.then(res => res.json())
		.then(res => {
			if(res){
				location.href=`/team/list`
			}
		})
		.catch(err => {
			console.log(err);
		});
	}
	teamCreateFetch(teamName);
});

