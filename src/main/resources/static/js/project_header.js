const url = window.location.pathname;
const urlArr = url.split("/");
const currentMenu = document.querySelector(`.menu-icon-box.${urlArr[4]}`);

currentMenu.style.borderBottom = "2px solid #44546f";

document.querySelectorAll(".menu-icon-box").forEach(function(btn){
	const icon = btn.querySelector(".menu-icon");
	const text = btn.querySelector(".menu-text");
	btn.addEventListener("mouseover", function(e){
		if(btn.className.includes(urlArr[4])){
			return;
		}
		btn.style.borderBottom = "2px solid #44546f";
		btn.style.paddingBottom = "5px";
		
		icon.style.marginBottom = "2px";
		text.style.marginBottom = "6px";
	});
	
	btn.addEventListener("mouseout", function(e){
		if(btn.className.includes(urlArr[4])){
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
	console.log(updateProjectNameData);
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

document.querySelector(".header-setbtn")?.addEventListener("click", function(e){
	
	const btnItem = document.querySelector(".header-setbtn");
	const windowItem = document.querySelector(".header-menuwindow");
	
	if(btnItem !== null && e.target.closest(".header-menuwindow") == null){
		
		btnItem.classList.toggle("active");
		document.querySelector(".header-setbtn-icon").classList.toggle("color");
		// btnItem.children[1].style.filter = "invert(94%) sepia(93%) saturate(0%) hue-rotate(238deg) brightness(107%) contrast(105%)";
		windowItem.classList.toggle("show");
	}
});

document.querySelector(".menuwindow-optionbtn-bg")?.addEventListener("click", function(e){
	
	const btnItem = document.querySelector(".menuwindow-optionbtn-bg");
	const windowItem = document.querySelector(".header-setbgbox");
	
	if(btnItem !== null && e.target.closest(".header-setbgbox") == null){
		
		btnItem.classList.toggle("active");
		btnItem.children[1].classList.toggle("color");
		// btnItem.children[1].style.filter = "invert(94%) sepia(93%) saturate(0%) hue-rotate(238deg) brightness(107%) contrast(105%)";
		windowItem.classList.toggle("show");
	}
});

