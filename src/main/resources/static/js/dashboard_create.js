function resetDashboardCreate(roleBox) {
	// 프로젝트 reset
	const roleProject1 = roleBox.querySelectorAll(".role_projct_item>div")[0];
	const roleProject2 = roleBox.querySelectorAll(".role_projct_item>div")[1];
	console.log(roleBox.querySelectorAll(".role_projct_item>div"))
	
	roleProject1.querySelector("input").placeholder = "프로젝트 선택";
	roleProject1.querySelector("input").classList.remove("active");
	roleProject1.querySelector(".change_icon").classList.remove("show");

	roleProject2.querySelector("input").placeholder = "역할 선택";
	roleProject2.querySelector("input").classList.remove("active");

	roleProject1.querySelector("input").nextElementSibling?.querySelector(".active").classList.remove("active");
	roleProject1.querySelector("input").nextElementSibling?.children[0].classList.add("active");

	roleProject2.querySelector("input").nextElementSibling?.querySelector(".active").classList.remove("active");
	roleProject2.querySelector("input").nextElementSibling?.children[0].classList.add("active");

	roleProject1.classList.remove("show");
	roleProject2.classList.remove("show");

	// 그룹 reset
	const roleGroup = roleBox.querySelector(".role_group_item");

	roleGroup.querySelector("input").placeholder = "그룹 선택";
	roleGroup.querySelector("input").classList.remove("active");
	roleGroup.querySelector(".change_icon").classList.remove("show");

	roleGroup.querySelector("input").nextElementSibling?.querySelector(".active").classList.remove("active");
	roleGroup.querySelector("input").nextElementSibling?.children[0].classList.add("active");

	roleGroup.classList.remove("show");

	// 사용자 reset
	const roleUser = roleBox.querySelector(".role_user_item");

	roleUser.querySelector("input").placeholder = "사용자 선택";
	roleUser.querySelector("input").classList.remove("active");
	roleUser.querySelector(".change_icon").classList.remove("show");

	roleUser.querySelector("input").nextElementSibling?.querySelector(".active").classList.remove("active");
	roleUser.querySelector("input").nextElementSibling?.children[0].classList.add("active");

	roleUser.classList.remove("show");

	// 비공개 reset
	const rolePrivate = roleBox.querySelector(".role_private_item");
	rolePrivate.classList.remove("show");
}

document.querySelectorAll(".role_input_box .role_input_lnb_item").forEach(function(item) {
	item.addEventListener("mousedown", function(e) {

		// 클릭 타겟 기준 부모 찾아서 active 제거
		e.target.closest(".role_input_lnb_container").querySelector(".active").classList.remove("active");
		this.classList.add("active");
		if (e.target.closest(".role_input_item") === null) return;
		// 맨 앞이 바뀌었을떄 모든 값 초기화
		const roleBox = e.target.closest(".role_input_box");
		resetDashboardCreate(roleBox); // idx=0: 조회자 reset, idx=1: 편집자 reset
		
		const type = this.children[1].innerText;
		switch (type) {
			case "프로젝트":
				roleBox.querySelectorAll(".role_projct_item>div")[0].classList.add("show");
				break;
			case "그룹":
				roleBox.querySelector(".role_group_item").classList.add("show");
				break;
			case "사용자":
				roleBox.querySelector(".role_user_item").classList.add("show");
				break;
			case "비공개":
				roleBox.querySelector(".role_private_item").classList.add("show");
				break;
		}

	});
});

// 프로젝트 선택 클릭시 다음 아이템 보여주기
document.querySelectorAll(".role_project_input").forEach(function(input, idx) {
	input.addEventListener("blur", function() {
		if (this.placeholder !== "프로젝트 선택") {
			document.querySelectorAll(".role_projct_item > div:nth-of-type(2)")[idx].classList.add("show");
		} else {
			document.querySelectorAll(".role_projct_item > div:nth-of-type(2)")[idx].classList.remove("show");
		}
	})
})

// lnb 아이템 선택시 이벤트
document.querySelectorAll(".role_input_lnb_item").forEach(function(item) {
	item.addEventListener("mousedown", function() {

		const imgSrc = this.children[0].getAttribute("src");
		const spanText = this.querySelector("span").innerText;

		let img = "";
		let input = "";

		if (this.closest(".role_input_box .item").className === "role_projct_item item") {
			img = this.closest(".role_input_box .item > div")?.querySelector(".change_icon");
			input = this.closest(".role_input_box .item > div")?.querySelector("input");
		} else {
			img = this.closest(".role_input_box .item")?.querySelector(".change_icon");
			input = this.closest(".role_input_box .item")?.querySelector("input");
		}

		img?.setAttribute("src", imgSrc);
		img?.classList.add("show");
		input.placeholder = spanText;
		input.classList.add("active");
	});
});

// 비공개가 있는지 검사
function isPrivateItem(idx){
	const createAuthTypeItems = document.querySelectorAll(".dashboard_create_list_container")[idx].children;
	let isPrivateItem = false;
	[...createAuthTypeItems].forEach(function(item){
		const originName = item.querySelector(".item_info_box span").innerText;
		if(originName === "비공개"){
		   isPrivateItem = true;
	   	}
	});
	return isPrivateItem;
}

// 추가 버튼 클릭시 아이템이 있는지 검사
function isSameItem(idx, iconFilename, name){
	const createAuthTypeItems = document.querySelectorAll(".dashboard_create_list_container")[idx].children;
	let isSame = true;
	[...createAuthTypeItems].forEach(function(item, index){
		const originIconFilename = item.querySelector(".item_info_box img").getAttribute("src");
		const originName = item.querySelector(".item_info_box span").innerText;
		if(originIconFilename === iconFilename &&
		   originName === name){
			isSame = false;
	   	}
	});
	return isSame;
}

// 추가 버튼 클릭시
document.querySelectorAll(".add_dashboard_auth_type_btn button").forEach(function(btn, idx) {
	btn.addEventListener("click", function(e) {
		const roleInputBox = e.target.closest(".role_input_box")
		const type = roleInputBox.querySelector(".role_input_item input").placeholder;
		const createAuthType = document.querySelectorAll(".dashboard_create_list_container")[idx];
		let name = "";
		let iconFilename = "";
		let tagStr = "";
		
		if (type === "프로젝트") {
			const projectName = roleInputBox.querySelector(".role_projct_item .role_project_input").placeholder;
			const projectAuthType = roleInputBox.querySelector(".role_projct_item .auth_type_input").placeholder;
			name = projectName + ", " + projectAuthType;
			iconFilename = roleInputBox.querySelector(".role_projct_item .change_icon").getAttribute("src");
			if(projectName === "프로젝트 선택" || projectAuthType === "역할 선택") return;
		}else if(type === "그룹") {
			name = roleInputBox.querySelector(".role_group_item .select_name").placeholder;
			iconFilename = roleInputBox.querySelector(".role_group_item .change_icon").getAttribute("src");
			if(name === "그룹 선택") return;
		}else if(type === "사용자") {
			name = roleInputBox.querySelector(".role_user_item .select_name").placeholder;
			iconFilename = roleInputBox.querySelector(".role_user_item .change_icon").getAttribute("src");
			if(name === "사용자 선택") return;
		}else if(type === "비공개") {
			if(isPrivateItem(idx)) return;
			createAuthType.innerHTML = "";
			name = "비공개";
			iconFilename = "/images/create_user_icon.svg";
		}
		tagStr = `<div class="dashboard_create_list_item">
					<div class="item_info_box">
						<img src="${iconFilename}" alt="" width="24" height="24" style="border-radius: 4px;">
						<span>${name}</span>
					</div>
					${type !== "비공개" ? '<img class="cancle_btn" src="/images/cancle_icon.svg" width="16" height="16">' : ""}
				  </div>`
				  
		if(isPrivateItem(idx))
			createAuthType.innerHTML = "";
				  
		if(isSameItem(idx, iconFilename, name))
			createAuthType.innerHTML += tagStr;
	});
});

// 삭제 버튼 클릭시
document.querySelectorAll(".dashboard_create_list_container").forEach(function(container, idx){
	container.addEventListener("click", function(e){
		const createAuthTypeItems = document.querySelectorAll(".dashboard_create_list_container")[idx].children;
		const createAuthType = document.querySelectorAll(".dashboard_create_list_container")[idx];
		const cancleBtn = e.target.closest(".cancle_btn");
		if(cancleBtn === null) return;
		
		const authTypeItme = e.target.closest(".dashboard_create_list_item");
		authTypeItme.remove();
		// 비어있으면 비공개 추가
		if(createAuthTypeItems.length === 0){
			createAuthType.innerHTML = `<div class="dashboard_create_list_item">
											<div class="item_info_box">
												<img src="/images/create_user_icon.svg" alt="" width="24" height="24" style="border-radius: 4px;">
												<span>비공개</span>
											</div>
										  </div>`;
		}
	})
})

// 대시보드 이름 키업 이벤트
document.querySelector("#dashboard_name").addEventListener("keyup", function(){
	const dashboardAlertBox = document.querySelector(".dashboard_alert_box");
	const dashboardAlertComment = dashboardAlertBox.querySelector(".comment");
	
	if(this.value.length === 0){
		dashboardAlertBox.classList.add("show");
		dashboardAlertComment.innerText = "이름 필드는 필수입니다."
	}else{
		dashboardAlertBox.classList.remove("show");
	}
});

// 대시보드 이름 포커스 이벤트
document.querySelector("#dashboard_name").addEventListener("focus", function(){
	this.style.border = "2px solid #0c66e4";
});
// 대시보드 이름 블러 이벤트
document.querySelector("#dashboard_name").addEventListener("blur", function(){
	const alertBox = document.querySelector(".dashboard_alert_box.show")
	if(alertBox !== null){
		this.style.border = "2px solid #E2483D"
	}else{
		this.style.border = "1px solid #44546fa4";
	}
})

// 저장 버튼 클릭시
document.querySelector(".dashboard_create_btn_box .create_btn").addEventListener("click", function(){
	// 이름 유효성 검사
	// 비어있는지, 중복 대시보드 이름이 있는지 검색
	
	const dashboardNameInput = document.querySelector("#dashboard_name");
	const dashboardName = dashboardNameInput.value.trim();
	
	const dashboardAlertBox = document.querySelector(".dashboard_alert_box");
	const dashboardAlertComment = dashboardAlertBox.querySelector(".comment");
	if(dashboardName.length === 0){
		dashboardAlertBox.classList.add("show");
		dashboardAlertComment.innerText = `대시보드를 저장하려면 이름을 지정해야 합니다.`
		
		dashboardNameInput.style.border = "2px solid #E2483D"
		return;
	}
	// fetch 중복인지 확인
	function duplicationDashboardNameFetch(){
		const uri = "api/dashboard/duplication/name";
		
		
		
	}
	
	
	// fetch 대시보드 저장
	console.log("click");
})

