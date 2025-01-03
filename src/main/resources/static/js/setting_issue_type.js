document.querySelector("body").addEventListener("click", function(e) {
	
	document.querySelector(".infobtn-popup.show")?.classList.remove("show");
	
	const removeBg = document.querySelectorAll(".fieldtype-infobtn");
	const removeColor = document.querySelectorAll(".infobtnimg");
	
	for(let i = 0; i < removeBg.length; i++){
		const bgItem = removeBg.item(i);
		const colorItem = removeColor.item(i);
		colorItem.style.filter = "invert(13%) sepia(37%) saturate(1705%) hue-rotate(189deg) brightness(94%) contrast(93%)";
		bgItem.style.backgroundColor = "white";
	}
	
	const infoBtnItem = e.target.closest(".fieldtype-infobtn");
	
	if(infoBtnItem !== null){
		infoBtnItem.children[1].style.filter = "invert(26%) sepia(69%) saturate(4766%) hue-rotate(209deg) brightness(96%) contrast(91%)";
		infoBtnItem.style.backgroundColor = "#E9F2FF";
		infoBtnItem.children[0].classList.add("show");
	}

});

document.querySelector("body").addEventListener("click", function(e) {
	
	document.querySelector(".titleinfobtn-popup.show")?.classList.remove("show");
	
	const removeBg = document.querySelectorAll(".fieldtitle-infobtn");
	const removeColor = document.querySelectorAll(".titleinfobtnimg");
	
	for(let i = 0; i < removeBg.length; i++){
		const bgItem = removeBg.item(i);
		const colorItem = removeColor.item(i);
		colorItem.style.filter = "invert(13%) sepia(37%) saturate(1705%) hue-rotate(189deg) brightness(94%) contrast(93%)";
		bgItem.style.backgroundColor = "#F7F8F9";
	}
	
	const infoBtnItem = e.target.closest(".fieldtitle-infobtn");
	
	if(infoBtnItem !== null){
		infoBtnItem.children[1].style.filter = "invert(26%) sepia(69%) saturate(4766%) hue-rotate(209deg) brightness(96%) contrast(91%)";
		infoBtnItem.style.backgroundColor = "#E9F2FF";
		infoBtnItem.children[0].classList.add("show");
	}

});

document.querySelector("body").addEventListener("click", function(e) {
	
	document.querySelector(".fieldoption-popup.show")?.classList.remove("show");
	
	const removeBg = document.querySelectorAll(".myfield-optionbtn");
	const removeColor = document.querySelectorAll(".fieldoptionbtnimg");
	
	for(let i = 0; i < removeColor.length; i++){
		const bgItem = removeBg.item(i);
		const colorItem = removeColor.item(i);
		colorItem.style.filter = "invert(13%) sepia(37%) saturate(1705%) hue-rotate(189deg) brightness(94%) contrast(93%)";
		bgItem.style.backgroundColor = "#091E420F";
	}
	
	const optionBtnItem = e.target.closest(".myfield-optionbtn");
	
	if(optionBtnItem !== null){
		const xyItem = optionBtnItem.getBoundingClientRect();
		
		if(xyItem.top < 600){
			optionBtnItem.children[1].style.filter = "invert(26%) sepia(69%) saturate(4766%) hue-rotate(209deg) brightness(96%) contrast(91%)";
			optionBtnItem.style.backgroundColor = "#E9F2FF";
			optionBtnItem.children[0].classList.add("show");
			optionBtnItem.children[0].style.top = "30px";
		}else{
			optionBtnItem.children[1].style.filter = "invert(26%) sepia(69%) saturate(4766%) hue-rotate(209deg) brightness(96%) contrast(91%)";
			optionBtnItem.style.backgroundColor = "#E9F2FF";
			optionBtnItem.children[0].classList.add("show");
			
			const popupXyItem = optionBtnItem.children[0].getBoundingClientRect();
			
			if(popupXyItem.height > 300){
				optionBtnItem.children[0].style.top = "30px";
			}else{
				optionBtnItem.children[0].style.bottom = "30px";
			}

		}
		
	}

});

document.querySelector(".issuetype-settitle").addEventListener("click", function(e){
	const titleBtn = e.target.closest(".issuetype-settitle");
	const inputBox = document.querySelector(".edit-issuetype-title");
	if(titleBtn !== null){
		inputBox.classList.add("show");
		titleBtn.classList.add("none");
	}
});

let editTypeData = {
	"idx": "",
	"name": "", 
	"content": ""
}

function editIssueTypeTitle(){
	let url = "/api/project/update_issueType_name";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(editTypeData)
	})
	.then(response => {
        if (response.ok) {
			document.querySelector(".edit-issuetype-title").classList.remove("show");
			document.querySelector(".issuetype-settitle").classList.remove("none");
			document.querySelector(".issuetype-settitle").innerText = editTypeData.name;
			document.querySelector(".edit-issuetype-title-input").value = editTypeData.name;
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelector(".edit-typetitlebtn.submit").addEventListener("click", function(e){
	const input = document.querySelector(".edit-issuetype-title-input");
	editTypeData.idx = input.dataset.idx;
	editTypeData.name = input.value;
	editIssueTypeTitle();
});

document.querySelector(".edit-typetitlebtn.cancel").addEventListener("click", function(e){
	document.querySelector(".edit-issuetype-title").classList.remove("show");
	document.querySelector(".issuetype-settitle").classList.remove("none");
});

document.querySelector(".issuetype-content").addEventListener("click", function(e){
	const contentBtn = e.target.closest(".issuetype-content");
	if(contentBtn !== null){
		document.querySelector(".edit-issuetype-content").classList.add("show");
		contentBtn.classList.add("none");
	}
});

function editIssueTypeContent(){
	let url = "/api/project/update_issueType_content";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(editTypeData)
	})
	.then(response => {
        if (response.ok) {
			document.querySelector(".edit-issuetype-content").classList.remove("show");
			document.querySelector(".issuetype-content").classList.remove("none");
			document.querySelector(".issuetype-content").innerText = editTypeData.content;
			document.querySelector(".edit-issuetype-content-input").value = editTypeData.content;
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}
document.querySelector(".edit-typecontentbtn.submit").addEventListener("click", function(e){
	const input = document.querySelector(".edit-issuetype-content-input");
	editTypeData.idx = input.dataset.idx;
	editTypeData.content = input.value;
	editIssueTypeContent();
});

document.querySelector(".edit-typecontentbtn.cancel").addEventListener("click", function(e){
	document.querySelector(".edit-issuetype-content").classList.remove("show");
	document.querySelector(".issuetype-content").classList.remove("none");
});

document.querySelector(".titlebtn-other")?.addEventListener("click", function(e){
	this.children[0].classList.toggle("show");
});

document.querySelector(".type-deletebtn")?.addEventListener("click", function(e){
	const btn = e.target.closest(".type-deletebtn");
	if(btn !== null){
		document.querySelector(".delete-alert-container").classList.add("show");
	}
});

document.querySelector(".delete-alert-container").addEventListener("mousedown", function(e){
	document.querySelector(".delete-alert-container")?.classList.remove("show");
	
	const bgItem = e.target.closest(".delete-type-btn.cancel");
	const detailItem = e.target.closest(".delete-alert-box");
	
	if(bgItem == null && detailItem !== null){
		document.querySelector(".delete-alert-container").classList.add("show");
	}else{
		document.querySelector(".delete-alert-container").classList.remove("show");
	}
});

document.querySelector(".issuetype-selectbox")?.addEventListener("click", function(e){
	const btn = e.target.closest(".issuetype-selectbox");
	if(btn !== null){
		btn.children[0].classList.toggle("show");
	}
});

document.querySelectorAll(".issuetype-option").forEach(function(btn){
	btn.addEventListener("click", function(e){
		const selectedType = document.querySelector(".selected-type");
		selectedType.dataset.idx = btn.dataset.idx;
		selectedType.innerHTML = "";
		selectedType.appendChild(btn.children[0].cloneNode(true));
		selectedType.appendChild(btn.children[1].cloneNode(true));
	});
});

let deleteIssueTypeData = {
	"projectIdx" : "",
	"issueTypeIdx": "",
	"newTypeIdx": ""
}

function deleteIssueType(key){
	let url = "/api/project/delete_issueType";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(deleteIssueTypeData)
	})
	.then(response => {
        if (response.ok) {
			location.href = `/project/${key}/setting/info`;
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelector(".delete-type-btn.submit").addEventListener("click", function(e){
	const selectedType = document.querySelector(".selected-type");
	if(selectedType !== null){
		if(selectedType.dataset.idx == null && selectedType.dataset.listsize > 0){
			alert("반드시 이동할 이슈 유형을 선택해야 합니다.");
			return;
		}else{
			const btn = document.querySelector(".delete-type-btn.submit");
			deleteIssueTypeData.projectIdx = btn.dataset.projectidx;
			deleteIssueTypeData.issueTypeIdx = btn.dataset.oldidx;
			deleteIssueTypeData.newTypeIdx = selectedType.dataset.idx;
			deleteIssueType(btn.dataset.key);
		}
	}else{
		const btn = document.querySelector(".delete-type-btn.submit");
		deleteIssueTypeData.projectIdx = btn.dataset.projectidx;
		deleteIssueTypeData.issueTypeIdx = btn.dataset.oldidx;
		deleteIssueType(btn.dataset.key);
	}
});