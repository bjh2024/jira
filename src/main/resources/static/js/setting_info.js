const originProjectName = document.querySelector("#projectName").value;
const originProjectKey = document.querySelector("#projectKey").value;
const originProjectLeaderIdx = document.querySelector("#projectLeader").getAttribute("idx-data");

function isPreventBtn(){
	const saveBtn = document.querySelector(".save_btn_box button")
	
	const projectName = document.querySelector("#projectName").value;
	const projectKey = document.querySelector("#projectKey").value;
	const leaderIdx = document.querySelector("#projectLeader").getAttribute("idx-data");
	
	if(projectName === originProjectName && 
	   projectKey === originProjectKey &&
	   leaderIdx === originProjectLeaderIdx){
		saveBtn.classList.add("prevent_btn");
   	}else{
		saveBtn.classList.remove("prevent_btn");
	}
}

document.querySelectorAll(".input_box").forEach(function(inputBox){
	inputBox.querySelector("input").addEventListener("keyup", function(){
		isPreventBtn();
	})
})

// 리더 검색창 포커스 이벤트
document.querySelector("#projectLeader").addEventListener("focus", function(e){
	const img = this.previousElementSibling;
	img.setAttribute("src", "/images/default_icon_file.png");
	this.value = "";
});
// 리더 검색창 블러 이벤트
document.querySelector("#projectLeader").addEventListener("blur", function(e){
	const iconFilename = this.getAttribute("icon-file-name-data");
	const name = this.getAttribute("name-data");
	const img = this.previousElementSibling;
	img.setAttribute("src", `/images/${iconFilename}`);
	this.value = name;
	
	const dynamicBox = document.querySelector(".leader_box .search_box .dynamic");
	const searchBox = document.querySelector(".leader_box .search_box .search_list");
	dynamicBox.classList.remove("show");
	searchBox.classList.add("show");
	
});

// 리더 검색 => 디바운스 함수
function debounce(func, timeout = 300) {
  let timer;
  return (...args) => {
    clearTimeout(timer);  // 기존 타이머를 취소
    timer = setTimeout(() => {
      func.apply(this, args);  // 최종 실행할 함수
    }, timeout);  // 설정된 timeout 후에만 실행
  };
}

async function projectMembersListFetch(searchName){
	const uri = `/api/account/project/members?searchName=${searchName}`;
	try {
		const res = await fetch(uri, {method: "get"});
		const datas = await res.json();
		const dynamicBox = document.querySelector(".leader_box .search_box .dynamic");
		const projectLeader = document.querySelector("#projectLeader").getAttribute("name-data");
		dynamicBox.innerHTML = "";
		if(datas.length > 0){
			datas.forEach(function(user){
				// key == accountIdx
				dynamicBox.innerHTML += `<div class="${projectLeader === user.name ? 'active' : ''}"
				   							  "idx-data"=${user.key}>
											<img src="/images/${user.iconFilename}" width="32" height="32" style="border-radius: 32px;">
											<span>${user.name}</span>
										 </div>`
			})
		}else{
			dynamicBox.innerHTML = `<div>사용자를 찾을수 없습니다....</div>`
		}
	} catch (error) {
	    console.error(error);
	}
}

const debounceFetch = debounce((searchText) => projectMembersListFetch(searchText));

document.querySelector("#projectLeader").addEventListener("keyup", function(e){
	const searchName = this.value;
	const dynamicBox = document.querySelector(".leader_box .search_box .dynamic");
	const searchBox = document.querySelector(".leader_box .search_box .search_list");
	if(searchName.length !== 0){
		debounceFetch(searchName);
		dynamicBox.classList.add("show");
		searchBox.classList.remove("show");
	}else{
		dynamicBox.classList.remove("show");
		searchBox.classList.add("show");
	}
});

// 리더 선택시
document.querySelector(".leader_box .search_box").addEventListener("mousedown", function(e){
	const selectUser = e.target.closest(".search_list > div");
	
	if(selectUser === null) return;
	const accountIdx = selectUser.getAttribute("idx-data");
	const iconFilename = selectUser.querySelector("img").getAttribute("src");
	const name = selectUser.querySelector("span").innerText;
	
	const input = document.querySelector("#projectLeader");
	input.value = name;
	input.setAttribute("idx-data", accountIdx);
	input.setAttribute("name-data", name);
	input.setAttribute("icon-file-name-data", iconFilename.split("/")[2]);
	const img = input.previousElementSibling;
	img.setAttribute("src", iconFilename);
	
	const searchListStatic = document.querySelectorAll(".search_list.static > div");
	searchListStatic.forEach(function(item){
		const selectName = item.querySelector("span").innerText;
		item.classList.remove("active");
		if(input.value === selectName){
			item.classList.add("active");
		}
	});
	
	isPreventBtn();
	
});

// 저장 버튼 클릭시
document.querySelector(".save_btn_box button").addEventListener("click", function(){

	const projectNameInput = document.querySelector("#projectName");
	const projectKeyInput = document.querySelector("#projectKey");
	const projectIdx = document.querySelector("#projectLeader").getAttribute("project-idx-data");
	const leaderIdx = document.querySelector("#projectLeader").getAttribute("idx-data");
	
	if(projectNameInput.value === originProjectName && 
	   projectKeyInput.value=== originProjectKey &&
	   leaderIdx === originProjectLeaderIdx){
		return;
   	}
	// 유효성 검사 1
	document.querySelectorAll(".alret_project_update_box").forEach(function(alretBox) {
		if (alretBox.className.includes("show")) {
			return;
		}
	});
	let projectNameValidation = validationName(projectNameInput.value, projectNameInput);
	let projectKeyValidation = validationKey(projectKeyInput.value, projectKeyInput);
	// 유효성 검사 2
	if (!projectNameValidation || !projectKeyValidation) {
		return;
	}
	
	let requestProjectInfo = {
		"projectName" : projectNameInput.value,
		"projectKey" : projectKeyInput.value,
		"projectIdx" : projectIdx,
		"leaderIdx" : leaderIdx,
	}

	const uri = "/api/project/update"	
	fetch(uri, {method:"post", 
			    headers:{"Content-type" : "application/json"}, 
				body:JSON.stringify(requestProjectInfo)}
	)
	.then(res => res.json())
	.then(res => {
		if(res){
			alert("프로젝트가 업데이트 되었습니다!")
			location.reload();
		}
	})
	.catch(err => {
		console.error(err);
	});
});

// 프로젝트 이름, 프로젝트 키 유효성 검사
// 중복키 발생, 중복 프로젝트 이름 발생
async function validationName(projectName, inputBox) {
	const alretProjectName = document.querySelectorAll(".alret_project_update_box")[0];
	if(projectName === originProjectName){
		alretProjectName.classList.remove("show");
		
		inputBox.style.border = "1px solid #aaa";
		inputBox.style.margin = "0 0";
		return true;
	}
	let result = false;
	// 유효성 검사
	// 프로젝트 이름이 존재하는지 확인 fetch
	async function projectNameDuplication() {
		let url = `/api/project/duplication/name?projectName=${projectName}`;
		try {
			const res = await fetch(url, { method: "get" });
			const data = await res.json();
			if (data != 0) return true;
		} catch (err) {
			console.error(err);
		}
		return false;
	}
	if (await projectNameDuplication()) {
		alretProjectName.querySelector(".comment").innerText = "해당 이름의 프로젝트가 이미 존재합니다";
		alretProjectName.classList.add("show");
	} else if (projectName.length <= 1) {
		if (projectName.length === 0) {
			alretProjectName.querySelector(".comment").innerText = "프로젝트에 이름이 있어야합니다";
		} else {
			alretProjectName.querySelector(".comment").innerText = "프로젝트 이름이 너무 짧습니다";
		}
		alretProjectName.classList.add("show");
	} else {
		alretProjectName.classList.remove("show");
		result = true;
	}

	if (alretProjectName.className.includes("show")) {
		inputBox.style.border = "2px solid #E2483D";
		inputBox.style.margin = "0 -1px";
	} else {
		inputBox.style.border = "1px solid #aaa";
		inputBox.style.margin = "0 0";
	}
	return result;
}

// 프로젝트 이름 유효성 검사
document.querySelector("#projectName").addEventListener("blur", function(){
	validationName(this.value, this);
})

async function validationKey(keyName, inputBox) {
	const alretProjectName = document.querySelectorAll(".alret_project_update_box")[1];
	if(keyName === originProjectKey){
		alretProjectName.classList.remove("show");
		
		inputBox.style.border = "1px solid #aaa";
		inputBox.style.margin = "0 0";
		return true;
	}
	let result = false;
	let projectName = "";
	// 유효성 검사
	// 키 이름이 존재하는지 확인 fetch
	async function keyNameDuplication() {
		let url = `/api/project/duplication/key?keyName=${keyName}`;
		try {
			const res = await fetch(url, { method: "get" });
			const data = await res.json();
			if (data.count != 0) {
				projectName = data.projectName;
				return true;
			}
		} catch (err) {
			console.error(err);
		}
		return false;
	}
	if (keyName.toUpperCase() !== keyName || keyName.length <= 1) {
		if (keyName.length === 0) {
			alretProjectName.querySelector(".comment").innerText = "프로젝트에 키가 있어야합니다";
		} else {
			alretProjectName.querySelector(".comment").innerText = "프로젝트 키는 대문자로 시작해야 하고 1자 이상의 대문자 영숫자가 뒤따라야 합니다.";
		}
		alretProjectName.classList.add("show");
	} else if (await keyNameDuplication()) {
		alretProjectName.querySelector(".comment").innerText = `프로젝트 '${projectName}'은(는) 이 프로젝트 키를 사용합니다.`;
		alretProjectName.classList.add("show");
	} else {
		alretProjectName.classList.remove("show");
		result = true;
	}
	if (alretProjectName.className.includes("show")) {
		inputBox.style.border = "2px solid #E2483D";
		inputBox.style.margin = "0 -1px";
	} else {
		inputBox.style.border = "1px solid #aaa";
		inputBox.style.margin = "0 0";
	}
	return result;
}

// 프로젝트 키 유효성 검사
document.querySelector("#projectKey").addEventListener("blur", function(){
	validationKey(this.value,this);
})

