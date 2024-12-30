document.querySelector(".dashboard_craete_modal").addEventListener("mousedown", function(e){
	const dashboardCreateBox = e.target.closest(".dashboard_create");
	if(dashboardCreateBox === null){
		this.classList.remove("show");
		
		document.querySelector("#dashboard_name").value = "";
		document.querySelector("#dashboard_explain").value = "";
		
		document.querySelector("#dashboard_name").style.border ="1px solid #44546fa4";
		document.querySelector(".dashboard_alert_box").classList.remove("show");
				
		const roleBoxs = document.querySelectorAll(".role_input_box");
		roleBoxs.forEach(function(box){
			resetDashboardCreate(box);
		});
		
		const roleInputItems = document.querySelectorAll(".role_input_item");
		roleInputItems.forEach(function(item){
			item.querySelector(".change_icon").setAttribute("src", "/images/create_user_icon.svg");
			item.querySelector("input").placeholder = "비공개";
		});
		
		const roleInputInbItems = document.querySelectorAll(".role_input_item .role_input_lnb_item");
		roleInputInbItems.forEach(function(item){
			if(item.querySelector("span").innerText === "비공개"){
				item.classList.add("active");
			}else{
				item.classList.remove("active");
			}
		});
		
		const authTypeList = document.querySelectorAll(".dashboard_create_list_container");
		authTypeList.forEach(function(item){
			item.innerHTML = `<div class="dashboard_create_list_item">
								<div class="item_info_box">
									<img src="/images/create_user_icon.svg" alt="" width="24" height="24" style="border-radius: 4px;">
									<span>비공개</span>
								</div>
							   </div>`
		});
	}
});

// reset
function resetDashboardCreate(roleBox) {
	const roleProject1 = roleBox.querySelectorAll(".role_projct_item>div")[0];
	const roleProject2 = roleBox.querySelectorAll(".role_projct_item>div")[1];
	
	roleProject1.querySelector("input").placeholder = "프로젝트 선택";
	roleProject1.querySelector("input").classList.remove("active");
	roleProject1.querySelector(".change_icon").classList.remove("show");

	roleProject2.querySelector("input").placeholder = "역할 선택";
	roleProject2.querySelector("input").classList.remove("active");

	roleProject1.querySelector("input").nextElementSibling?.querySelector(".active")?.classList.remove("active");
	roleProject1.querySelector("input").nextElementSibling?.children[0]?.classList.add("active");

	roleProject2.querySelector("input").nextElementSibling?.querySelector(".active")?.classList.remove("active");
	roleProject2.querySelector("input").nextElementSibling?.children[0]?.classList.add("active");

	roleProject1.classList.remove("show");
	roleProject2.classList.remove("show");

	// 그룹 reset
	const roleGroup = roleBox.querySelector(".role_group_item");

	roleGroup.querySelector("input").placeholder = "그룹 선택";
	roleGroup.querySelector("input").classList.remove("active");
	roleGroup.querySelector(".change_icon").classList.remove("show");

	roleGroup.querySelector("input").nextElementSibling?.querySelector(".active")?.classList.remove("active");
	roleGroup.querySelector("input").nextElementSibling?.children[0]?.classList.add("active");

	roleGroup.classList.remove("show");

	// 사용자 reset
	const roleUser = roleBox.querySelector(".role_user_item");

	roleUser.querySelector("input").placeholder = "사용자 선택";
	roleUser.querySelector("input").classList.remove("active");
	roleUser.querySelector(".change_icon").classList.remove("show");

	roleUser.querySelector("input").nextElementSibling?.querySelector(".active")?.classList.remove("active");
	roleUser.querySelector("input").nextElementSibling?.children[0]?.classList.add("active");

	roleUser.classList.remove("show");

	// 비공개 reset
	const rolePrivate = roleBox.querySelector(".role_private_item");
	rolePrivate.classList.remove("show");
}

// 맨앞 lnb 클릭 이벤트
document.querySelectorAll(".role_input_box .role_input_lnb_item").forEach(function(item) {
	item.addEventListener("mousedown", function(e) {

		// 클릭 타겟 기준 부모 찾아서 active 제거
		e.target.closest(".role_input_lnb_container").querySelector(".active").classList.remove("active");
		this.classList.add("active");
		if (e.target.closest(".role_input_item") === null) return;
		// 맨 앞이 바뀌었을떄 모든 값 초기화
		const roleBox = e.target.closest(".role_input_box");
		resetDashboardCreate(roleBox);
		
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

// lnb 아이템 불러오기(project) fetch
document.querySelectorAll(".role_project_input").forEach(function(projectInput){
	projectInput.addEventListener("focus", function(e){
		const lnbContainer = this.nextElementSibling;
		async function projectListFetch(lnbContainer){
			const uri = `/api/project/dashboard/list`
			try{
				const res = await fetch(uri, {method : "get"})
				const projectList = await res.json();
				lnbContainer.innerHTML = "";
				projectList.forEach(function(project, idx){
					let active = "";
					if(e.target.placeholder !== "프로젝트 선택"){
						if(e.target.placeholder === project.name)
							active = "active";
					}else{
						if(idx === 0)
							active = "active"
					}
					lnbContainer.innerHTML += `<div class="role_input_lnb_item ${active}" idx-data="${project.idx}">
													<img src="/images/${project.iconFilename}" alt="" style="border-radius: 2px;" width="24" height="24">
													<span>${project.name}</span>
												</div>`
				});
			}catch(err){
				console.log(err);
			}
		}
		projectListFetch(lnbContainer);
	});
});

// lnb 아이템 불러오기(group) fetch
document.querySelectorAll(".role_group_input").forEach(function(groupInput){
	groupInput.addEventListener("focus", function(e){
		const lnbContainer = this.nextElementSibling;
		async function teamListFetch(lnbContainer){
			const uri = `/api/team/dashboard/list`
			try{
				const res = await fetch(uri, {method : "get"})
				const teamList = await res.json();
				lnbContainer.innerHTML = "";
				teamList.forEach(function(team, idx){
					let active = "";
					if(e.target.placeholder !== "그룹 선택"){
						if(e.target.placeholder === team.name)
							active = "active";
					}else{
						if(idx === 0)
							active = "active"
					}
					lnbContainer.innerHTML += `<div class="role_input_lnb_item ${active}" idx-data="${team.idx}">
													<img src="/images/create_group_icon.svg" alt="" style="border-radius: 2px;" width="24" height="24">
													<span>${team.name}</span>
												</div>`
				});
			}catch(err){
				console.log(err);
			}
		}
		teamListFetch(lnbContainer);
	});
});

// lnb 아이템 불러오기(user) fetch
document.querySelectorAll(".role_user_input").forEach(function(groupInput){
	groupInput.addEventListener("focus", function(e){
		const lnbContainer = this.nextElementSibling;
		async function userListFetch(lnbContainer){
			const uri = `/api/account/dashboard/list`
			try{
				const res = await fetch(uri, {method : "get"})
				const userList = await res.json();
				lnbContainer.innerHTML = "";
				userList.forEach(function(user, idx){
					let active = "";
					if(e.target.placeholder !== "그룹 선택"){
						if(e.target.placeholder === user.name)
							active = "active";
					}else{
						if(idx === 0)
							active = "active"
					}
					lnbContainer.innerHTML += `<div class="role_input_lnb_item ${active}" idx-data="${user.idx}">
													<img src="/images/${user.iconFilename}" alt="" style="border-radius: 24px;" width="24" height="24">
													<span>${user.name}</span>
												</div>`
				});
			}catch(err){
				console.log(err);
			}
		}
		userListFetch(lnbContainer);
	});
});


// lnb 아이템 선택시 이벤트
document.querySelectorAll(".role_input_lnb_container").forEach(function(item) {
	item.addEventListener("mousedown", function(e) {
		const lnbItem = e.target.closest(".role_input_lnb_item")
		if(lnbItem === null) return;
		const roleInputBox = e.target.closest(".role_input_box");
		if(!lnbItem.className.includes("project_role")){
			roleInputBox.setAttribute("idx-data", lnbItem.getAttribute("idx-data"));
		}
		const imgSrc = lnbItem.children[0].getAttribute("src");
		const spanText = lnbItem.querySelector("span").innerText;

		let img = "";
		let input = "";

		if (lnbItem.closest(".role_input_box .item").className === "role_projct_item item") {
			img = lnbItem.closest(".role_input_box .item > div")?.querySelector(".change_icon");
			input = lnbItem.closest(".role_input_box .item > div")?.querySelector("input");
		} else {
			img = lnbItem.closest(".role_input_box .item")?.querySelector(".change_icon");
			input = lnbItem.closest(".role_input_box .item")?.querySelector("input");
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
		
		const newDiv = document.createElement("div");
		newDiv.classList.add("dashboard_create_list_item");
		newDiv.setAttribute("idx-data", roleInputBox.getAttribute("idx-data"));
		
		if (type === "프로젝트") {
			newDiv.setAttribute("type-data", "프로젝트");
			
			const projectName = roleInputBox.querySelector(".role_projct_item .role_project_input").placeholder;
			const projectAuthType = roleInputBox.querySelector(".role_projct_item .auth_type_input").placeholder;
			name = projectName + ", " + projectAuthType;
			iconFilename = roleInputBox.querySelector(".role_projct_item .change_icon").getAttribute("src");
			if(projectName === "프로젝트 선택" || projectAuthType === "역할 선택") return;
		}else if(type === "그룹") {
			newDiv.setAttribute("type-data", "그룹");
			
			name = roleInputBox.querySelector(".role_group_item .select_name").placeholder;
			iconFilename = roleInputBox.querySelector(".role_group_item .change_icon").getAttribute("src");
			if(name === "그룹 선택") return;
		}else if(type === "사용자") {
			newDiv.setAttribute("type-data", "사용자");
			
			name = roleInputBox.querySelector(".role_user_item .select_name").placeholder;
			iconFilename = roleInputBox.querySelector(".role_user_item .change_icon").getAttribute("src");
			if(name === "사용자 선택") return;
		}else if(type === "비공개") {
			newDiv.setAttribute("type-data", "비공개");
			
			if(isPrivateItem(idx)) return;
			createAuthType.innerHTML = "";
			name = "비공개";
			iconFilename = "/images/create_user_icon.svg";
		}
		newDiv.innerHTML = `<div class="item_info_box">
								<img src="${iconFilename}" alt="" width="24" height="24" style="border-radius: 4px;">
								<span>${name}</span>
							</div>
							${type !== "비공개" ? '<img class="cancle_btn" src="/images/cancle_icon.svg" width="16" height="16">' : ""}`
				  
		if(isPrivateItem(idx))
			createAuthType.innerHTML = "";
				  
		if(isSameItem(idx, iconFilename, name))
			createAuthType.appendChild(newDiv);
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
			createAuthType.innerHTML = `<div class="dashboard_create_list_item" type-data='비공개'>
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
document.querySelector(".dashboard_create_btn_box .create_btn").addEventListener("click",async function(){
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
		dashboardNameInput.focus();
		return;
	}
	
	// fetch 중복인지 확인
	async function duplicationDashboardNameFetch(){
		const uri = `/api/dashboard/duplication/name?dashboardName=${dashboardName}`;
		
		try{
			const res = await fetch(uri, {method:"get"});
			const data = await res.json();
			if(data == 1){
				dashboardAlertBox.classList.add("show");
				dashboardAlertComment.innerText = `같은 이름의 대시보드가 이미 있습니다.`
				
				dashboardNameInput.style.border = "2px solid #E2483D"
				dashboardNameInput.focus();
				return false;
			}
		}
		catch(err){
			console.error(err);
		}
		return true;
	}
	if(!await duplicationDashboardNameFetch()) return;
	
	// fetch 대시보드 저장
	function dashboardCreate(name, explain, authItems){
		const requestDashboardCreate = {
			"name": name,
			"explain": explain,
			"authItems" : authItems
		}
		
		const uri = "/api/dashboard/create"
		fetch(uri, {method: "post", 
					headers:{"Content-type" : "application/json"}, 
					body:JSON.stringify(requestDashboardCreate)}
		)
		.then(res => res.json())
		.then(dashboardIdx => {
			if(dashboardIdx){
				location.href=`/dashboard/edit/${dashboardIdx}`;
			}
		})
		.catch(err => {
			console.error(err);
		});
	}
	
	let authItems = [];
	document.querySelectorAll(".dashboard_create_list_container").forEach(function(container, idx){
		[...container.children].forEach(function(item){
			const itemType = item.getAttribute("type-data");
			let itemIdx = item.getAttribute("idx-data");
			let role = null;
			if(itemType === "프로젝트"){
				const name = item.querySelector("span").innerText;
				role = name.split(",")[1].trim();
				switch(role){
					case "Administrator":
						role = 3;
						break;
					case "Member":
						role = 2;
						break;
					case "Viewer":
						role = 1;
						break;
				}
			}
			let authItem = {
				"itemType" : itemType,
				"idx" : itemIdx,
				"role" : role,
			    "authType" : idx+1
			}
			authItems.push(authItem);
		});
	});
	
	const dashboardExplain = document.querySelector("#dashboard_explain").value;
	let name = dashboardName;	
	let explain = dashboardExplain;
	console.log(authItems);
	dashboardCreate(name, explain, authItems);
});

// 취소 버튼 클릭시
document.querySelector(".dashboard_create_btn_box .cancel_btn").addEventListener("click", function(){
	const modal = document.querySelector(".dashboard_craete_modal");
	modal.classList.remove("show");
});

