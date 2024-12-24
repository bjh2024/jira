const projectNameInput = document.querySelector(".project_name_input_box input");

projectNameInput.addEventListener("keydown", function(e) {
	const projectName = document.querySelector(".project_name_example");
	if (e.target.value === "") {
		projectName.innerText = "제목 없는 프로젝트";
	} else {
		projectName.innerText = e.target.value;
	}
});

// 프로젝트 이름
projectNameInput.addEventListener("keyup", function(e) {
	const projectName = document.querySelector(".project_name_example");
	if (e.target.value === "") {
		projectName.innerText = "제목 없는 프로젝트";
	} else {
		projectName.innerText = e.target.value;
	}
});

const projectKeyInput = document.querySelector(".key_name_input_box input#key");
const keyBoxs = document.querySelectorAll(".table_box2 td:nth-of-type(2)>div");

projectKeyInput.addEventListener("keydown", function(e) {
	if (e.target.value === "") {
		keyBoxs.forEach(function(key, idx) {
			key.innerText = `KEY-${idx + 1}`;
		});
	} else {
		keyBoxs.forEach(function(key, idx) {
			key.innerText = `${e.target.value}-${idx + 1}`;
		});
	}
});

// 키 이름
projectKeyInput.addEventListener("keyup", function(e) {
	if (e.target.value === "") {
		keyBoxs.forEach(function(key, idx) {
			key.innerText = `KEY-${idx + 1}`;
		});
	} else {
		keyBoxs.forEach(function(key, idx) {
			key.innerText = `${e.target.value}-${idx + 1}`;
		});
	}
});

// 더보기
const moreBtnBox = document.querySelector(".more_btn_box .more_btn");
let check = false;
moreBtnBox.addEventListener("click", function() {
	const moreBtn = this;
	const img = moreBtn.children[0];
	const button = moreBtn.children[1];

	const moreBox = document.querySelector(".more_box");
	check = !check;
	if (check) {
		moreBox.classList.add("show");
		img.src = "/images/arrow_under_icon.svg"
		button.innerText = "간략히 보기";
	} else {
		moreBox.classList.remove("show");
		img.src = "/images/arrow_right_icon.svg"
		button.innerText = "더 보기";
	}
});

async function validationName(projectName, inputBox) {
	let result = false;
	const alretProjectName = document.querySelectorAll(".alret_project_create_box")[0];
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

// input 프로젝트 이름 포커스 이벤트
document.querySelector(".project_create_main_box1 input").addEventListener("focus", function() {
	this.style.border = "2px solid #4d97ff";
	this.style.margin = "0 -1px";
});
document.querySelector(".project_create_main_box1 input").addEventListener("focusout", async function() {
	let projectName = this.value.trim();
	await validationName(projectName, this);
});

async function validationKey(keyName, inputBox) {
	let result = false;
	const alretProjectName = document.querySelectorAll(".alret_project_create_box")[1];
	let projectName = "";
	// 유효성 검사
	// 키 이름이 존재하는지 확인 fetch
	async function keyNameDuplication() {
		let url = `/api/project/duplication/key?keyName=${keyName}&uri=${window.location.pathname}`;
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

// input 키 이름 포커스 이벤트
document.querySelector(".key_name_input_box input#key").addEventListener("focus", function() {
	this.style.border = "2px solid #4d97ff";
	this.style.margin = "0 -1px";
});
document.querySelector(".key_name_input_box input#key").addEventListener("focusout", async function() {
	let keyName = this.value.trim();
	await validationKey(keyName, this);
});

// 취소 클릭
document.querySelector(".project_create_btn_box .cancle").addEventListener("click", function() {
	location.href = `/`;
})


// 프로젝트 만들기 클릭
document.querySelector(".project_create_btn_box .create").addEventListener("click", async function() {
	let projectNameInput = document.querySelectorAll(".project_create_main_box1 input")[0];
	let projectKeyInput = document.querySelectorAll(".project_create_main_box1 input")[1];
	let projectInfo = {
		"name": projectNameInput.value,
		"key": projectKeyInput.value
	};
	// 유효성 검사 1
	document.querySelectorAll(".alret_project_create_box").forEach(function(alretBox) {
		if (alretBox.className.includes("show")) {
			return;
		}
	});
	let projectNameValidation = await validationName(projectNameInput.value, projectNameInput);
	let projectKeyValidation = await validationKey(projectKeyInput.value, projectKeyInput);
	// 유효성 검사 2
	if (!projectNameValidation || !projectKeyValidation) {
		return;
	}
	
	function projectCreateFetch(data) {
		let url = "/api/project/create";
		fetch(url, {
			method: "post",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(data)
		})
		.then(res=>res.json())
		.then(res=>{
			let projectKey = projectKeyInput.value;
			if(res){
				window.location.href=`/project/${projectKey}/summation`;
			}else{
				console.error("프로젝트 생성 실패....")
			}
		})
		.catch(e => {
				console.error(e);
		})
	}
	projectCreateFetch(projectInfo);
})
