const url = window.location.pathname;
const urlArr = url.split("/");
const currentMenu = document.querySelector(`.menu-icon-box.${urlArr[3]}`);

currentMenu.style.borderBottom = "2px solid #44546f";

if(document.querySelector(".insert-user-selectwindow").children.length < 1){
	document.querySelector(".insert-user-selectwindow").remove();
}

document.querySelectorAll(".menu-icon-box").forEach(function(btn){
	const icon = btn.querySelector(".menu-icon");
	const text = btn.querySelector(".menu-text");
	btn.addEventListener("mouseover", function(e){
		if(btn.className.includes(urlArr[3])){
			return;
		}
		btn.style.borderBottom = "2px solid #44546f";
		btn.style.paddingBottom = "5px";
		
		icon.style.marginBottom = "2px";
		text.style.marginBottom = "6px";
	});
	
	btn.addEventListener("mouseout", function(e){
		if(btn.className.includes(urlArr[3])){
			return;
		}
		btn.style.borderBottom = "none";
		btn.style.paddingBottom = "10px";
		
		icon.style.marginBottom = "0px";
		text.style.marginBottom = "2px";
	});
});

document.querySelector("body").addEventListener("click", function(e){
	if(e.target.closest(".project-title-editbox") !== null){
		return;
	}
	
	document.querySelector(".project-title-editbox")?.classList.remove("show");
	document.querySelector(".header-title")?.classList.remove("none");	
	
	const btn = e.target.closest(".header-title");
	
	if(btn !== null){
		btn.parentElement.previousElementSibling.classList.add("show");
		btn.classList.add("none");
	}
});

let updateProjectNameData = {
	"projectIdx": "",
	"name": ""
}

function updateProjectNameFetch(box, input){
	let url = "/api/project/update_project_name";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(updateProjectNameData)
	}).then(response => {
        if (response.ok) {
            box.innerText = updateProjectNameData.name;
			input.value = updateProjectNameData.name;
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelector(".edit-prjtitlebtn.submit").addEventListener("click", function(e){
	const submitItem = e.target.closest(".edit-prjtitlebtn.submit");
	const inputItem = document.querySelector(".project-title-input");
	updateProjectNameData.projectIdx = inputItem.dataset.projectidx;
	updateProjectNameData.name = inputItem.value;
	updateProjectNameFetch(document.querySelector(".header-title"), inputItem);
	submitItem.parentElement.classList.remove("show");
	submitItem.parentElement.nextElementSibling.children[0].classList.remove("none");
});

document.querySelector(".edit-prjtitlebtn.cancel").addEventListener("click", function(e){
	const cancelItem = e.target.closest(".edit-prjtitlebtn.cancel");
	if(cancelItem !== null){
	cancelItem.parentElement.classList.remove("show");
	cancelItem.parentElement.nextElementSibling.children[0].classList.remove("none");
	}
});

document.querySelector(".header-setbgimg")?.addEventListener("click", function(e) {
	const setBgItem = e.target.closest(".header-setbgimgbtn");
	const removePopupItem = e.target.closest(".header-setbgbtn");
	
	if(setBgItem !== null){
		setBgItem.classList.add("active");
		setBgItem.nextElementSibling.classList.add("show");
		
	}
	
	if(removePopupItem !== null){
		document.querySelector(".header-setbgimgbtn").classList.remove("active");
		document.querySelector(".header-setbgimgbtn").nextElementSibling.classList.remove("show");
	}
});

document.querySelector(".header-setbtn").addEventListener("mouseover", function(e){
	const btnItem = e.target.closest(".header-setbtn");
	if(btnItem !== null && !btnItem.className.includes("active")){
		btnItem.classList.add("hovered");
	}
});

document.querySelector(".header-setbtn").addEventListener("mouseout", function(e){
	const btnItem = e.target.closest(".header-setbtn");
	if(btnItem !== null && !btnItem.className.includes("active")){
		btnItem.classList.remove("hovered");
	}
});

document.querySelector("body").addEventListener("click", function(e) {
	document.querySelector(".header-setbtn.active")?.classList.remove("active");
	document.querySelector(".header-setbtn.hovered")?.classList.remove("hovered");
	document.querySelector(".header-menuwindow.show")?.classList.remove("show");
	document.querySelector(".header-setbtn-icon.color")?.classList.remove("color");

	const btnItem = e.target.closest(".header-setbtn");
	if(btnItem !== null){
		btnItem.classList.add("active");
		document.querySelector(".header-menuwindow").classList.add("show");
		document.querySelector(".header-setbtn-icon").classList.add("color");
		if(e.target.closest(".menuwindow-optionbtn") !== null){
			btnItem.classList.remove("active");
			document.querySelector(".header-menuwindow").classList.remove("show");
			document.querySelector(".header-setbtn-icon").classList.remove("color");
		}
	}
});

document.querySelector(".menuwindow-optionbtn-bg")?.addEventListener("click", function(e){
	
	const btnItem = document.querySelector(".menuwindow-optionbtn-bg");
	const windowItem = document.querySelector(".header-setbgbox");
	
	if(btnItem !== null && e.target.closest(".header-setbgbox") == null){
		
		btnItem.classList.toggle("active");
		btnItem.children[1].classList.toggle("color");
		windowItem.classList.toggle("show");
	}
});

document.querySelector(".menuwindow-optionbtn.insert").addEventListener("click", function(e){
	document.querySelector(".insert-member-container").classList.add("show");
});

document.querySelector(".insert-member-container").addEventListener("mousedown", function(e) {
	document.querySelector(".insert-member-container")?.classList.remove("show");
	
	const bgItem = e.target.closest(".insert-user-btn.cancel");
	const detailItem = e.target.closest(".insert-member-box");
	
	const submitItem = e.target.closest(".insert-user-btn.submit");
	if(submitItem !== null){
		if(document.querySelector(".user-select-alert").style.display == "none"){
			const dataItem = document.querySelector(".insert-user-selectbtn");
			createProjectMemberData.userIdx = dataItem.dataset.useridx;
			createProjectMemberData.projectIdx = dataItem.dataset.projectidx;
			createProjectMemberFetch();
		}
	}
	
	if(bgItem == null && detailItem !== null){
		document.querySelector(".insert-member-container").classList.add("show");
	}else{
		document.querySelector(".insert-member-container").classList.remove("show");
	}
});

let createProjectMemberData = {
	"userIdx": "",
	"projectIdx": ""
}

function createProjectMemberFetch(){
	let url = "/api/project/create_project_member";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(createProjectMemberData)
	}).then(response => {
        if (response.ok) {
			location.reload();
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelector(".insert-user-selectbtn").addEventListener("click", function(e){
	const btnItem = e.target.closest(".insert-user-selectbtn");
	if(btnItem !== null && btnItem.children.length > 1){
		btnItem.children[0].classList.toggle("show");
	}
});

document.querySelectorAll(".user-select-option")?.forEach(function(btn){
	btn.addEventListener("click", function(e){
		document.querySelector(".user-select-alert").style.display = "none";
		const icon = btn.children[0].cloneNode(true);
		const name = btn.children[1].cloneNode(true);
		document.querySelector(".insert-user-selectbtn").appendChild(icon);
		document.querySelector(".insert-user-selectbtn").appendChild(name);
		document.querySelector(".insert-user-selectbtn").dataset.useridx = btn.dataset.useridx;
		document.querySelector(".insert-user-btn.submit").classList.add("active");
	});
});

document.querySelector(".menuwindow-optionbtn.delete").addEventListener("click", function(e){
	document.querySelector(".project-delete-alert-container")?.classList.add("show");
});

document.querySelector(".project-delete-alert-container")?.addEventListener("mousedown", function(e) {
	document.querySelector(".project-delete-alert-container")?.classList.remove("show");
	
	const bgItem = e.target.closest(".delete-alert-cancelbtn");
	const detailItem = e.target.closest(".delete-alert-box");
	
	const submitItem = e.target.closest(".delete-alert-submitbtn");
	if(submitItem !== null){
		deleteProjectData.projectIdx = submitItem.dataset.projectidx;
		console.log(deleteProjectData);
		deleteProjectFetch();
	}
	
	if(bgItem == null && detailItem !== null){
		document.querySelector(".project-delete-alert-container").classList.add("show");
	}else{
		document.querySelector(".project-delete-alert-container").classList.remove("show");
	}
});

let deleteProjectData = {
	"projectIdx": ""
}

function deleteProjectFetch(){
	let url = "/api/project/delete_project";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(deleteProjectData)
	}).then(response => {
        if (response.ok) {
			location.href = "/";
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}