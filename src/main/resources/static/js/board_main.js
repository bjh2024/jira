let createLogData = {
	"userIdx": "",
	"issueIdx": "",
	"type": ""
}

function createProjectLog(){
	let url = "/api/project/create_project_log";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(createLogData)
	})
	.then(response => {
        if (response.ok) {
            console.log("로그 업데이트 성공");
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}

let updateExareaDatas = {
	"issueIdx": "",
	"content": ""
}

function updateIssueContent(value){
	let url = "/api/project/update_issue_content";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(updateExareaDatas)
	})
	.then(response => {
        if (response.ok) {
            value.innerText = updateExareaDatas.content;
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".editor.exarea").forEach(function(editor, index){
	const contentEditor = new toastui.Editor({
	    el: editor,
	    height: 'auto',
		minHeight: '74px',
	    initialEditType: 'wysiwyg',
	    initialValue: editor.dataset.content,
	    previewStyle: 'vertical'
	  });
	const submitbtn = editor.parentElement.querySelector(".editarea-save");
	submitbtn.addEventListener("click", function(e){
		updateExareaDatas.issueIdx = editor.dataset.idx;
		updateExareaDatas.content = contentEditor.getMarkdown();
		contentEditor.initialValue = updateExareaDatas.content;
		updateIssueContent(editor.parentElement.nextElementSibling);
		
		const container = e.target.closest(".issuedetail-container");
		createLogData.userIdx = container.dataset.useridx;
		createLogData.issueIdx = container.dataset.issueidx;
		createLogData.type = 4;
		createProjectLog();
		
		const editorContainer = editor.parentElement;
		editorContainer.style.display = "none";
		editorContainer.nextElementSibling.style.display = "block";
		editorContainer.parentElement.style.padding = "6px 8px";

	});
});

let createReplyData = {
	"issueIdx": "",
	"content": "",
	"writerIdx": ""
}

function createReply(replybox){
	let url = "/api/project/create_reply";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(createReplyData)
	})
	.then(response => response.json())
	.then(reply => {
		console.log(reply);
		const newReply = document.createElement("div");
		newReply.classList.add("issuedetail-reply");
		newReply.innerHTML = `
			<div style="width: 32px; height: 32px; border-radius: 50%; overflow: hidden;">
				<img class="issue-managerimg" src="/images/${reply.iconFilename}">
			</div>
			<div class="issuedetail-replydetailbox">
				<div class="replydetail-name-date">
					<span>${reply.name}</span>
					<span style="letter-spacing: -0.2px; margin-top: 2px;">${reply.date}</span>
				</div>
				<div class="replydetail-content">${reply.content}</div>
			</div>
			<div></div>
			<div class="replydetail-managebar">
				<span class="reply-geteditbtn">편집</span><strong>·</strong>
				<span>삭제</span><strong>·</strong>
				<button class="replydetail-emojibtn">
					<span style="height: 16px;">
						<img src="/images/reply_emoji_icon.svg"
							style="filter: invert(30%) sepia(10%) saturate(1715%) hue-rotate(179deg) brightness(97%) contrast(86%);">
					</span>
				</button>
			</div>
			<div class="editor-container">
				<div class="editor" style="width: 676px;"></div>
				<div class="editareabtns">
					<button class="editarea-save">저장</button>
					<button class="editarea-cancel">취소</button>
				</div>
			</div>
		`;
		replybox.prepend(newReply);
		
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".editor.newreply").forEach(function(editor, index){
	const contentEditor = new toastui.Editor({
		el: editor,
		height: 'auto',
		minHeight: '74px',
		initialEditType: 'wysiwyg',
		initialValue: '',
		previewStyle: 'vertical',
	});
	const editorContainer = editor.parentElement;
	  
	const submitbtn = editor.nextElementSibling.children[0];
	submitbtn.addEventListener("click", function(e){
		createReplyData.issueIdx = editor.dataset.issueidx;
		createReplyData.writerIdx = editor.dataset.useridx;
		createReplyData.content = contentEditor.getMarkdown();
		if(createReplyData.content.length < 1){
			return;
		}
		const replybox = editor.parentElement.parentElement.parentElement.nextElementSibling;
		createReply(replybox);
		
		const container = e.target.closest(".issuedetail-container");
		createLogData.userIdx = container.dataset.useridx;
		createLogData.issueIdx = container.dataset.issueidx;
		createLogData.type = 1;
		createProjectLog();
		
		editorContainer.classList.remove("show");
		editorContainer.nextElementSibling.style.display = "block";
	});
	const cancelbtn = editor.nextElementSibling.children[1];
	cancelbtn.addEventListener("click", function(e){
		editorContainer.classList.remove("show");
		editorContainer.nextElementSibling.style.display = "block";
	});
});

// 답글 편집
function initEditor(target, initialValue) {
    return new toastui.Editor({
        el: target,
		width: '676px',
        height: 'auto',
        minHeight: '74px',
        initialEditType: 'wysiwyg',
        initialValue: initialValue, // 동적으로 설정된 기본값
        previewStyle: 'vertical',
    });
}

editReplyData = {
	"replyIdx": "",
	"content": ""
}

function updateReplyFetch(replybox){
	let url = "/api/project/update_reply";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(editReplyData)
	}).then(response => {
        if (response.ok) {
            const detail = replybox.children[0];
			if(detail.children.length < 3){
				const edited = document.createElement("div");
				edited.innerText = "편집됨";
				detail.appendChild(edited);
			}
			replybox.children[1].innerText = editReplyData.content;
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".editor.alterreply").forEach(function(editor){
	const contentEditor = new toastui.Editor({
	    el: editor,
	    height: 'auto',
		minHeight: '74px',
	    initialEditType: 'wysiwyg',
	    initialValue: editor.dataset.content,
	    previewStyle: 'vertical'
	});
	const editorContainer = editor.parentElement;
		  
	const submitbtn = editor.nextElementSibling.children[0];
	submitbtn.addEventListener("click", function(e){
		editReplyData.replyIdx = editor.dataset.replyidx;
		editReplyData.content = contentEditor.getMarkdown();
		if(editReplyData.content.length < 1){
			return;
		}
		const barItem = editorContainer.previousElementSibling;
		const contentItem = barItem.previousElementSibling.previousElementSibling;
		updateReplyFetch(contentItem);
		barItem.style.display = "flex";
		contentItem.children[1].style.display = "block";
		editorContainer.style.display = "none";
	});
});

let changeDate = {
	"issueIdx": "",
	"date": "",
	"type": ""
}

function updateStartDateFetch(){
	let url = "/api/project/update_date";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(changeDate)
	})
	.catch(error => {
		console.error("Fetch error:", error);
	});
}

function loadDateData(issueIdx, date, type){
	changeDate.issueIdx = issueIdx;
	changeDate.date = date;
	changeDate.type = type;
	updateStartDateFetch();
	
	const container = e.target.closest(".issuedetail-container");
	createLogData.userIdx = container.dataset.useridx;
	createLogData.issueIdx = container.dataset.issueidx;
	if(type == "start"){
		createLogData.type = 13;
	}else{
		createLogData.type = 14;
	}
	createProjectLog();
}

/*document.querySelector("body").addEventListener("click", function(e) {
	if(e.target.closest(".show")?.className.includes("show")){
		return;
	}
	
	document.querySelector(".sidebarbtn.active")?.classList.remove("active");
	document.querySelector(".sidebarbtn-filter.active")?.classList.remove("active");
	document.querySelector(".sidebarbtn-other.active")?.classList.remove("active");
	document.querySelector(".btnwindow.show")?.classList.remove("show");
	document.querySelector(".btnwindow-filter.show")?.classList.remove("show");

	document.querySelector(".sidebarbtnicon-filter").style.filter = "none";
	document.querySelector(".sidebarbtnicon-other").style.filter = "none";
	
	const sidebarGroupItem = e.target.closest(".sidebarbtn-group");
	const sidebarFilterItem = e.target.closest(".sidebarbtn-filter");
	const sidebarOtherItem = e.target.closest(".sidebarbtn-other");
	
	if(sidebarGroupItem !== null){
		sidebarGroupItem.children[0].classList.add("show");
	}
	
	if(sidebarFilterItem !== null){
		sidebarFilterItem.classList.add("active");
		sidebarFilterItem.children[0].classList.add("show");
		document.querySelector(".sidebarbtnicon-filter").style.filter = "invert(100%) sepia(1%) saturate(7498%) hue-rotate(57deg) brightness(102%) contrast(102%)";
	}
	
	if(sidebarOtherItem !== null){
		sidebarOtherItem.classList.add("active");
		sidebarOtherItem.children[0].classList.add("show");
		document.querySelector(".sidebarbtnicon-other").style.filter = "invert(100%) sepia(1%) saturate(7498%) hue-rotate(57deg) brightness(102%) contrast(102%)";
	}
});*/

document.querySelector("body").addEventListener("click", function(e){
	if(e.target.closest(".status-menuwindow-title") !== null){
		return;
	}
	document.querySelector(".status-menuwindow")?.classList.remove("show");
	
	const btn = e.target.closest(".status-menubtn");
	
	if(btn !== null){
		btn.children[0].classList.toggle("show");
		const menubtn = e.target.closest(".status-menuwindow-btn");
		
		if(menubtn !== null && menubtn.className.includes("name")){
			const issueStatus = btn.parentElement.previousElementSibling;
			console.log(issueStatus);
			issueStatus.classList.add("none");
			issueStatus.previousElementSibling.classList.add("show");
			btn.children[0].classList.remove("show");
		}else if(menubtn !== null && menubtn.className.includes("del")){
			const container = document.querySelector(".status-delete-alert-container");
			container.querySelector(".delete-alert-titletext").innerText = `${menubtn.dataset.name} 열에서 작업 이동`;
			container.querySelector(".old-status").innerText = menubtn.dataset.name;	
			container.querySelector(".delete-alert-body").children[0].innerText = `백로그의 작업을 포함하여 ${menubtn.dataset.name} 상태의 모든 작업에 대해 새 홈을 선택합니다.`;
			container.classList.add("show");
			
			deleteStatusData.projectIdx = menubtn.dataset.projectidx;
			deleteStatusData.statusIdx = menubtn.dataset.statusidx;
		}
	}
});

let deleteStatusData = {
	"projectIdx": "",
	"statusIdx": "",
	"newStatusIdx": ""
}

function fetchnewStatusList(statusbox){
	let url = "/api/project/get_status_list";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(deleteStatusData)
	})
	.then(response => response.json())
	.then(statusList => {
		statusbox.innerHTML = "";
		
		statusList.forEach(function(status){
			const statusVal = document.createElement("div");
			statusVal.classList.add("new-status-btn");
			statusVal.setAttribute("data-statusidx", status.idx);
			
			const statusTitle = document.createElement("span");
			statusTitle.classList.add("new-status-item");
			statusTitle.innerHTML = `${status.name}`;
			if(status.status == 1){
				
			}else if(status.status == 2){
				statusTitle.classList.add("grade2");
			}else if(status.status == 3){
				statusTitle.classList.add("grade3");
			}
			statusVal.appendChild(statusTitle);
			statusbox.appendChild(statusVal);
			statusVal.addEventListener("click", function(e){
				const newStatus = document.querySelector(".new-status");
				deleteStatusData.newStatusIdx = `${status.idx}`;
				newStatus.innerText = status.name;
				if(status.status == 1){
					
				}else if(status.status == 2){
					newStatus.classList.add("grade2");
				}else if(status.status == 3){
					newStatus.classList.add("grade3");
				}
			});
		});
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}


document.querySelector(".new-status-box").addEventListener("click", function(e){
	const btn = e.target.closest(".new-status-box");
	if(btn !== null){
		fetchnewStatusList(btn.children[0]);
		btn.children[0].classList.toggle("show");
	}
});

function deleteAndUpdateIssueStatus(){
	let url = "/api/project/delete_issue_status";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(deleteStatusData)
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

document.querySelector(".status-delete-alert-container").addEventListener("mousedown", function(e) {
	document.querySelector(".status-delete-alert-container")?.classList.remove("show");
	
	const bgItem = e.target.closest(".delete-alert-cancelbtn");
	const detailItem = e.target.closest(".status-delete-alert-box");
	const boxItem = detailItem.querySelector(".new-status").innerText;
	
	const submitItem = e.target.closest(".delete-alert-submitbtn");
	if(submitItem !== null){
		if(boxItem == "상태 선택"){
			alert("반드시 변경할 상태를 선택해야 합니다.");
		}else{
			console.log(deleteStatusData);
			deleteAndUpdateIssueStatus();
		}
	}
	
	if(bgItem == null && detailItem !== null){
		document.querySelector(".status-delete-alert-container").classList.add("show");
	}else{
		document.querySelector(".status-delete-alert-container").classList.remove("show");
	}
});

document.querySelector("body").addEventListener("click", function(e) {
	if(e.target.closest(".status-menubtn") !== null || e.target.closest(".statusname-inputbox") !== null){
		return;
	}
	document.querySelector(".statusname-inputbox.show")?.classList.remove("show");
	document.querySelector(".issue-status.none")?.classList.remove("none");
});

document.querySelectorAll(".edit-statustitlebtn.cancel").forEach(function(btn){
	btn.addEventListener("click", function(e){
		btn.parentElement.classList.remove("show");
		btn.parentElement.nextElementSibling.classList.remove("none");
	});
});

let updateStatusTitleData = {
	"statusIdx": "",
	"name": ""
}

function updateStatusTitle(box, input){
	let url = "/api/project/update_status_title";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(updateStatusTitleData)
	}).then(response => {
        if (response.ok) {
            // 응답 상태가 200~299 범위인 경우
			input.value = updateStatusTitleData.statusIdx;
			box.innerText = updateStatusTitleData.name;
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".edit-statustitlebtn.submit").forEach(function(btn){
	btn.addEventListener("click", function(e){
		updateStatusTitleData.statusIdx = btn.previousElementSibling.dataset.idx;
		updateStatusTitleData.name = btn.previousElementSibling.value;
		console.log(updateStatusTitleData);
		const titleItem = btn.parentElement.nextElementSibling.children[0].children[0];
		updateStatusTitle(titleItem, btn.previousElementSibling);
		btn.parentElement.classList.remove("show");
		btn.parentElement.nextElementSibling.classList.remove("none");
	});
});

document.querySelectorAll(".subissuebtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e) {
		const subissueBtnIcon = btn.children[0].children[2];
		const subissueItem = btn.parentElement.nextElementSibling;

		if(btn !== null && subissueItem.className.includes("show")){
			subissueItem.classList.remove("show");
			subissueBtnIcon.classList.remove("rotate");
			return;
		}
		
		if(btn !== null){
			subissueItem.classList.add("show");
			subissueBtnIcon.classList.add("rotate");
		}
	});
});

document.querySelectorAll(".subissues").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		const lblItem = btn.querySelector(".issuedetail-graphval.label-def.sub");
		
		if(lblItem.children.length < 3){
			lblItem.querySelector(".graphval-label-def").classList.remove("none");
		}else{
			lblItem.querySelector(".graphval-label-def").classList.add("none");
		}
		
		if(btn !== null){
			btn.querySelector(".subissuedetail-container").classList.add("show");
		}
		
	});
	
});

document.querySelector("body").addEventListener("click", function(e) {
	if(e.target.closest(".show")?.className.includes("show")){
		return;
	}
	document.querySelector(".createissuebox.show")?.classList.remove("show");
	document.querySelector(".issuetypeselectbox.show")?.classList.remove("show");
	// document.querySelector(".create-issuekey").value = null;

	const createIssueItem = e.target.closest(".issuebox-create");
	if(createIssueItem !== null){
		createIssueItem.previousElementSibling.classList.add("show");
		createIssueItem.previousElementSibling.children[0].focus();
	}
});


document.querySelectorAll(".create-issuekey").forEach(function(form, index){
	form.addEventListener("keyup", function(e){
		
		const issueTitleItem = e.target.closest(".create-issuekey");
		if(issueTitleItem.value != "" && issueTitleItem.value != null){
			form.nextElementSibling.children[1].classList.add("action");
		}else{
			form.nextElementSibling.children[1].classList.remove("action");
		}
	})
});

document.querySelectorAll(".createissue-typebtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".issuetype") !== null){
			return;
		}
		
		// document.querySelector(".issuetypeselectbox.show")[index].classList.remove("show");
		e.target.closest(".issuetypeselectbox.show")?.classList.remove("show");
		
		const issueTypeBtn = btn;
		const issueTypeList = issueTypeBtn.children[0];
		const xyItem = issueTypeBtn.getBoundingClientRect();
		
 		if(issueTypeBtn !== null){
			issueTypeList.classList.add("show");
			if(xyItem.top >= 600){
				issueTypeList.style.left = "0px";
				issueTypeList.style.bottom = "30px";
			}else{
				issueTypeList.style.left = "0px";
				issueTypeList.style.top = "30px";
			}
		}else{
			issueTypeList.classList.remove("show");
		}
	})
});

document.querySelectorAll(".issuetype").forEach(function(btn){
	btn.addEventListener("click", function(e){
		btn.parentElement.parentElement.nextElementSibling.innerHTML = btn.querySelector(".issuetypeimg").innerHTML;
		const btnbox = btn.parentElement.parentElement.parentElement.parentElement;
		btnbox.setAttribute("data-typeidx", btn.dataset.typeidx);
		btn.parentElement.parentElement.classList.remove("show");
	});
});

let issueDatas = {
	"jiraName": "",
	"projectIdx": "",
	"issueTypeIdx": "",
	"reporterIdx": "",
	"statusIdx": "",
	"issueName": ""
}

function createissuefetch(){
	let url = "/api/project/create_issue";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(issueDatas)
	}).then(response => response.json())
	.then(idx => {
		location.reload();
		return idx.issueIdx;
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".create-issuekey").forEach(function(input){
	input.addEventListener("keyup", function(e){
		if(window.event.keyCode == 13){
			const btnBoxItem = input.nextElementSibling;
			
			issueDatas.jiraName = btnBoxItem.dataset.jiraname;
			issueDatas.projectIdx = btnBoxItem.dataset.projectidx;
			issueDatas.issueTypeIdx = btnBoxItem.dataset.typeidx;
			issueDatas.reporterIdx = btnBoxItem.dataset.useridx;
			issueDatas.statusIdx = btnBoxItem.dataset.statusidx;
			issueDatas.issueName = input.value;
			if(issueDatas.issueTypeIdx == 0){
				alert("반드시 이슈 유형을 지정해야 합니다.")
				return;
			}
			createissuefetch();
			
			/*createLogData.userIdx = btnBoxItem.dataset.useridx;
			createLogData.issueIdx = issueIdx;
			createLogData.type = issueIdx;
			createProjectLog();
			location.reload();*/
		}
	});
});

document.querySelectorAll(".createissuebtn").forEach(function(btn){
	btn.addEventListener("click", function(e){
		const btnBoxItem = btn.parentElement;
		const inputItem = btnBoxItem.previousElementSibling;
		
		issueDatas.jiraName = btnBoxItem.dataset.jiraname;
		issueDatas.projectIdx = btnBoxItem.dataset.projectidx;
		issueDatas.issueTypeIdx = btnBoxItem.dataset.typeidx;
		issueDatas.reporterIdx = btnBoxItem.dataset.useridx;
		issueDatas.statusIdx = btnBoxItem.dataset.statusidx;
		issueDatas.issueName = inputItem.value;
		
		if(issueDatas.issueTypeIdx == 0){
			alert("반드시 이슈 유형을 지정해야 합니다.")
			return;
		}
		createissuefetch();
	});
});



document.querySelector(".create-status-container").addEventListener("click", function(e){
	document.querySelector(".create-status-container")?.classList.remove("show");
	const container = document.querySelector(".create-status-container");
	const detailItem = e.target.closest(".create-statusbox");
	const bgItem = e.target.closest(".status-cancel-btn");
	const submitItem = e.target.closest(".status-submit-btn");
	if(submitItem !== null){
		getStatusSubmit();
	}
	
	if(bgItem == null && detailItem !== null){
		container.classList.add("show");
	}else{
		container.classList.remove("show");
	}
});

let createStatusDatas = {
	"name": "",
	"status": "",
	"projectIdx": ""
}

document.querySelector(".create-status-btn").addEventListener("click", function(e){
	if(e.target.closest(".create-status-container") !== null){
		return;
	}
	const btnItem = e.target.closest(".create-status-btn");
	if(btnItem !== null){
		btnItem.children[0].classList.add("show");
		btnItem.children[0].querySelector(".status-title-input").focus();
	}
});

document.querySelector(".set-status-status").addEventListener("click", function(e){
	const statusItem = document.querySelector(".set-status-status");
	
	if(e.target.closest(".set-status-left") !== null){
		const option = e.target.closest(".set-status-left");
		const currentStatus = statusItem.children[1];
		switch(option.dataset.idx){
			case "1":
				currentStatus.children[0].style.backgroundColor = "#0515240F";
				currentStatus.children[0].style.border = "1px solid #7D818A";
				currentStatus.children[1].innerText = "할 일 상태";
				currentStatus.dataset.idx = option.dataset.idx;
				break;
			case "2":
				currentStatus.children[0].style.backgroundColor = "#E9F2FF";
				currentStatus.children[0].style.border = "1px solid #0C66E4";
				currentStatus.children[1].innerText = "진행 중 상태";
				currentStatus.dataset.idx = option.dataset.idx;
				break;
			case "3":
				currentStatus.children[0].style.backgroundColor = "#EFFFD6";
				currentStatus.children[0].style.border = "1px solid #6A9A23";
				currentStatus.children[1].innerText = "완료 상태";
				currentStatus.dataset.idx = option.dataset.idx;
				break;
		}
	}
	
	statusItem.children[0].classList.toggle("show");
	statusItem.classList.toggle("focus");
});

document.querySelector(".status-title-input").addEventListener("keyup", function(e){
	const inputItem = e.target.closest(".status-title-input");
	const btnItem = document.querySelector(".status-submit-btn");
	if(inputItem.value != "" && inputItem.value != null){
		btnItem.classList.add("action");
	}else{
		btnItem.classList.remove("action");
	}
});

function createStatusFetch(){
	let url = "/api/project/create_projects_status";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(createStatusDatas)
	}).then(response => {
        if (response.ok) {
			console.log("삭제 성공");
			location.reload();
            return response.text(); // 응답 내용을 처리하지 않으려면 여기서 끝냄
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}

function getStatusSubmit(){
	createStatusDatas.name = document.querySelector(".status-title-input").value;
	createStatusDatas.status = document.querySelector(".set-status-left.current").dataset.idx;
	createStatusDatas.projectIdx = document.querySelector(".status-submit-btn").dataset.projectidx;
	document.querySelector(".create-status-container").classList.remove("show");
	createStatusFetch();
	location.reload();
}

document.querySelectorAll(".issues").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".subissuebtn") !== null || e.target.closest(".issue-menubtn") !== null
			|| e.target.closest(".subissuebox") !== null || e.target.closest(".issue-menuwindow") !== null
			|| e.target.closest(".menuwindow-option") !== null || e.target.closest(".insertwindow-btn") !== null){
			return;
		}
		
		const subissuelist = btn.querySelector(".subissue-list-box.main");
		if(subissuelist.children.length < 3){
			subissuelist.classList.remove("show");
		}else{
			subissuelist.classList.add("show");
		}
		
		const lblItem = btn.querySelector(".issuedetail-graphval.label-def.main");
		if(lblItem?.children.length < 3){
			lblItem.querySelector(".graphval-label-def").classList.remove("none");
		}else{
			lblItem.querySelector(".graphval-label-def").classList.add("none");
		}
		
		if(btn !== null){ 
			const container = btn.querySelector(".issuedetail-container");
			container.classList.add("show");
		}
	});
});

document.querySelectorAll(".issue-menubtnbox").forEach(function(btn){
	btn.addEventListener("click", function(e){
		if(btn.style.visibility == "hidden"){
			window.classList.remove("show");
		}
		const myWindow = btn.children[0];
		const xy = btn.getBoundingClientRect();
		myWindow.style.top = `${xy.top - 4}px`;
		if(window.innerWidth - xy.right < 240){
			myWindow.style.left = `${xy.right - 254}px`;
		}else{
			myWindow.style.left = `${xy.left + 40}px`;
			
		}
		myWindow.classList.toggle("show");
	});
});

let deleteIssueData = {
	"issueIdx": ""
}

function deleteIssue(){
	let url = "/api/project/delete_issue_data";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(deleteIssueData)
	})
	.then(response => {
        if (response.ok) {
			console.log("삭제 성공");
			location.reload();
            return response.text(); // 응답 내용을 처리하지 않으려면 여기서 끝냄
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".menuwindow-option").forEach(function(btn){
	btn.addEventListener("click", function(e){
		const container = document.querySelector(".issue-delete-alert-container");
		container.classList.add("show");
		container.children[0].children[0].children[1].innerText = `${btn.dataset.issuekey}을(를) 삭제하시겠습니까?`;
		deleteIssueData.issueIdx = btn.dataset.issueidx;
		deleteIssueData.issueIdx = btn.dataset.issueidx;
	});
});

document.querySelector(".issue-delete-alert-container").addEventListener("mousedown", function(e) {
	document.querySelector(".issue-delete-alert-container")?.classList.remove("show");
	
	const bgItem = e.target.closest(".delete-alert-cancelbtn");
	const detailItem = e.target.closest(".delete-alert-box");
	
	const submitItem = e.target.closest(".delete-alert-submitbtn");
	if(submitItem !== null){
		console.log(deleteIssueData);
		deleteIssue();
	}
	
	if(bgItem == null && detailItem !== null){
		document.querySelector(".issue-delete-alert-container").classList.add("show");
	}else{
		document.querySelector(".issue-delete-alert-container").classList.remove("show");
	}
});

document.querySelectorAll(".issuedetail-container").forEach(function(container, index){
	container.addEventListener("mousedown", function(e) {
		document.querySelectorAll(".issuedetail-container")[index]?.classList.remove("show");
		
		const bgItem = e.target.closest(".issuedetail-off");
		const issueDetailItem = e.target.closest(".issuedetailbox");
		
		if(bgItem == null && issueDetailItem !== null){
			container.classList.add("show");
		}else{
			container.classList.remove("show");
		}
	});
});

let addIssuePathData = {
	"parentIdx": "",
	"childIdx": "",
	"projectIdx": ""
}

function createIssuePath(btn){
	let url = "/api/project/create_issue_path";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(addIssuePathData)
	})
	.then(response => response.json())
	.then(epik => {
		const path = document.createElement("div");
		path.classList.add("issuekeypath");
		path.innerHTML = `<span class="path-issuekeyicon"><img src="/images/${epik.iconFilename}" width="16px" height="16px"></span>
						  <span style="margin-bottom: 2px; padding-right: 4px;">
							  <a href="#">${epik.issueKey}</a>
						  </span>
						  <span style="font-size: 16px;">/</span>`;
		btn.before(path);
		btn.remove();
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}

let getEpikIssueData = {
	"projectIdx": ""
}

function getEpikIssueList(box){
	let url = "/api/project/get_epik_issue_list";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(getEpikIssueData)
	})
	.then(response => response.json())
	.then(epikList => {
		box.innerHTML = `<div class="pathwindow-title">
							<span>에픽 이슈</span>
						</div>`;
		epikList.forEach(function(epik){
			const item = document.createElement("div");
			item.classList.add("pathwindow-option");
			item.setAttribute("data-issueidx", epik.issueIdx);
			item.innerHTML = `<img src="/images/${epik.iconFilename}">
							  <span style="margin-bottom: 4px;">${epik.issueKey} ${epik.name}</span>`;
			box.appendChild(item);
			item.addEventListener("click", function(e){
				addIssuePathData.projectIdx = item.parentElement.parentElement.dataset.projectidx;
				addIssuePathData.childIdx = item.parentElement.parentElement.dataset.issueidx;
				addIssuePathData.parentIdx = item.dataset.issueidx;
				createIssuePath(item.parentElement.parentElement.parentElement);
				
				const container = e.target.closest(".issuedetail-container");
				createLogData.userIdx = container.dataset.useridx;
				createLogData.issueIdx = container.dataset.issueidx;
				createLogData.type = 2;
				createProjectLog();
			});
		});
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".addissuepathbtn").forEach(function(btn){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".pathwindow-option") !== null){
			return;
		}
		getEpikIssueData.projectIdx = btn.dataset.projectidx;
		getEpikIssueList(btn.children[0]);
		btn.classList.toggle("active");
		btn.children[0].classList.toggle("show");
	});
});

// 업데이트할 수 있는 에픽 이슈들을 가져옴
let updateEpikIssueData = {
	"projectIdx": "",
	"currentIssue": ""
}

// 업데이트해 줄 이슈 정보를 저장
let newEpikIssueData = {
	"projectIdx": "",
	"childIdx": "",
	"newParentIdx": "",
	"oldParentIdx": ""
}

function updateEpikIssuePath(box){
	let url = "/api/project/update_epik_issuepath";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(newEpikIssueData)
	})
	.then(response => response.json())
	.then(epik => {
		box.children[0].dataset.projectidx = epik.projectIdx;
		box.children[0].dataset.currentissue = epik.currentIdx;
		box.children[1].innerText = epik.issueKey;
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}

function getNewEpikIssueList(box){
	let url = "/api/project/get_other_epik_list";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(updateEpikIssueData)
	})
	.then(response => response.json())
	.then(epikList => {
		if(epikList.length < 1){
			box.innerHTML = `<div class="pathwindow-title">
								<span>계속하려면 새로운 에픽 이슈를 추가하세요.</span>
							</div>`;
			return;
		}
		box.innerHTML = `<div class="pathwindow-title">
							<span>에픽 이슈</span>
						</div>`;
		epikList.forEach(function(epik){
			const item = document.createElement("div");
			item.classList.add("pathwindow-option");
			item.setAttribute("data-issueidx", epik.issueIdx);
			item.innerHTML = `<img src="/images/${epik.iconFilename}">
							  <span style="margin-bottom: 4px;">${epik.issueKey} ${epik.name}</span>`;
			box.appendChild(item);
			item.addEventListener("click", function(e){
				newEpikIssueData.projectIdx = item.parentElement.parentElement.dataset.projectidx;
				newEpikIssueData.childIdx = item.parentElement.parentElement.parentElement.nextElementSibling.dataset.issueidx;
				newEpikIssueData.oldParentIdx = updateEpikIssueData.currentIssue;
				newEpikIssueData.newParentIdx = item.dataset.issueidx;
				console.log(newEpikIssueData);
				updateEpikIssuePath(item.parentElement.parentElement.parentElement);
				
				const container = e.target.closest(".issuedetail-container");
				createLogData.userIdx = container.dataset.useridx;
				createLogData.issueIdx = container.dataset.issueidx;
				createLogData.type = 2;
				createProjectLog();
			});
		});
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".path-issuekeyicon").forEach(function(btn){
	btn.addEventListener("click", function(e){
		updateEpikIssueData.projectIdx = btn.dataset.projectidx;
		updateEpikIssueData.currentIssue = btn.dataset.currentissue;
		btn.classList.toggle("active");
		btn.children[0].classList.toggle("show");
		getNewEpikIssueList(btn.children[0]);
	});
});

let issueLogData = {
	"issueIdx": "",
	"order": "DESC"
}

function getIssueLogList(logbox){
	let url = "/api/project/get_issue_log_list";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(issueLogData)
	})
	.then(response => response.json())
		.then(logList => {
			logbox.innerHTML = "";
			logbox.previousElementSibling.style.display = "none";
			logbox.previousElementSibling.previousElementSibling.style.display = "none";
			logbox.previousElementSibling.previousElementSibling.previousElementSibling.style.display = "none";
			logList.forEach(function(log){
				const logItem = document.createElement("div");
				logItem.classList.add("issuedetail-log");
				logItem.innerHTML = `<img src="/images/${log.iconFilename}" width="32" height="32">
										<span>${log.username} ${log.logType}</span>
										<span>${log.date}</span>`;
				logbox.appendChild(logItem);
			});
		}).catch(error => {
			console.error("Fetch error:", error);
	});
}

function getAllIssueLogList(logbox){
	let url = "/api/project/get_all_issue_log_list";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(issueLogData)
	})
	.then(response => response.json())
		.then(logList => {
			logbox.innerHTML = "";
			logbox.nextElementSibling.style.display = "none";
			logbox.nextElementSibling.nextElementSibling.style.display = "none";
			logbox.nextElementSibling.nextElementSibling.nextElementSibling.style.display = "none";
			logList.forEach(function(log){
				const logItem = document.createElement("div");
				logItem.classList.add("issuedetail-log");
				logItem.innerHTML = `<img src="/images/${log.iconFilename}" width="32" height="32">
										<span>${log.username} ${log.logType}</span>
										<span>${log.date}</span>`;
				logbox.appendChild(logItem);
			});
		}).catch(error => {
			console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".issuedetail-atvbtn").forEach(function(btn){
	btn.addEventListener("click", function(e){
		const container = e.target.closest(".issuedetail-container");
		if(btn.className.includes("log")){
			btn.previousElementSibling.classList.remove("active");
			btn.classList.add("active");
			
			issueLogData.issueIdx = container.parentElement.dataset.idx;
			const logbox = container.querySelector(".issuedetail-loglist");
			logbox.style.display = "block";
			
			getIssueLogList(logbox);
		}else if(btn.className.includes("reply")){
			btn.classList.add("active");
			btn.nextElementSibling.classList.remove("active");
			
			container.querySelector(".issuedetail-replybox").style.display = "block";
			container.querySelector(".issuedetail-replylist").style.display = "block";
			container.querySelector(".issuedetail-loglist").style.display = "none";
		}
	});
});

document.querySelectorAll(".subissuedetail-container").forEach(function(container, index){
	container.addEventListener("mousedown", function(e) {
		document.querySelectorAll(".issuedetail-container")[index]?.classList.remove("show");
		
		const bgItem = e.target.closest(".issuedetail-off");
		const issueDetailItem = e.target.closest(".issuedetailbox");
		
		if(bgItem == null && issueDetailItem !== null){
			container.classList.add("show");
		}else{
			container.classList.remove("show");
		}
	});
});

document.querySelectorAll(".issuedetail-title").forEach(function(area){
	area.addEventListener("click", function(e){
		area.style.display = "none";
		area.previousElementSibling.classList.add("show");
		area.previousElementSibling.children[0].focus();
	});
});

document.querySelectorAll(".edit-detailtitlebtn.cancel").forEach(function(btn){
	btn.addEventListener("click", function(e){
		btn.parentElement.classList.remove("show");
		btn.parentElement.nextElementSibling.style.display = "block";
	});
});

let updateIssueNameData = {
	"issueIdx": "",
	"name": ""
}

function updateIssueNameFetch(title, input){
	let url = "/api/project/update_issue_name";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(updateIssueNameData)
	}).then(response => {
        if (response.ok) {
			console.log("업데이트 성공");
            input.value = updateIssueNameData.name;
			title.innerText = updateIssueNameData.name;
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".edit-issuedetail-title-input").forEach(function(input){
	input.addEventListener("keyup", function(e){
		if(window.event.keyCode == 13){
			updateIssueNameData.issueIdx = input.dataset.idx;
			updateIssueNameData.name = input.value;
			
			const title = input.parentElement.nextElementSibling;
			
			updateIssueNameFetch(title, input);
			input.parentElement.classList.remove("show");
			title.style.display = "block";
		}
	});
});



document.querySelectorAll(".edit-detailtitlebtn.submit").forEach(function(btn){
	btn.addEventListener("click", function(e){
		const input = btn.previousElementSibling;
		updateIssueNameData.issueIdx = input.dataset.idx;
		updateIssueNameData.name = input.value;
		
		const title = btn.parentElement.nextElementSibling;
		
		updateIssueNameFetch(title, input);
		btn.parentElement.classList.remove("show");
		title.style.display = "block";
	});
});

document.querySelectorAll(".issuedetail-exarea").forEach(function(area, index){
	area.addEventListener("click", function(e) {
		const areaItem = e.target.closest(".issuedetail-exarea");
		const editorItem = area.children[0];
		const valItem = area.children[1];
		const btnItem = e.target.closest(".editarea-cancel");
		const submitItem = e.target.closest(".editarea-save");
	
		if(btnItem !== null && editorItem !== null){
			editorItem.style.display = "none";
			valItem.style.display = "block";
			areaItem.style.padding = "6px 8px";
			return;
		}
		
		if(areaItem !== null && submitItem == null){
			editorItem.style.display = "block";
			valItem.style.display = "none";
			areaItem.style.padding = "0px";
			editorItem.style.marginLeft = "8px";
		}
	});

});

document.querySelectorAll(".create-subissue-input").forEach(function(input){
	input.addEventListener("keyup", function(e){
		const issueTitleItem = e.target.closest(".create-subissue-input");
		const submitbtn = input.parentElement.parentElement.nextElementSibling.children[1].children[0];
		if(issueTitleItem.value != "" && issueTitleItem.value != null){
			submitbtn.classList.add("action");
		}else{
			submitbtn.classList.remove("action");
		}
	});
});

let createSubIssueData = {
	"jiraName": "",
	"projectIdx": "",
	"parentIdx": "",
	"reporterIdx": "",
	"statusIdx": "",
	"issueTypeIdx": "",
	"name": ""
}

function createSubIssuefetch(afterbox){
	let url = "/api/project/create_sub_issue";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(createSubIssueData)
	}).then(response => response.json())
	.then(newIssue => {
		const issueItem = document.createElement("div");
		issueItem.classList.add("subissue-list");
		//th:style="${newIssue.status == 3} ? 'text-decoration: line-through;' : ''"
		issueItem.innerHTML = 
		`<div class="subissue-list-leftdetail">
			<span style="height: 16px;">
				<img src="/images/${newIssue.issueTypeIconFilename}">
			</span>
			<span style="height: 13px;">${newIssue.issueKey}</span>
			<span style="font-size: 14px; margin-left: 8px; margin-bottom: 2px;">${newIssue.name}</span>
		</div>
		<div class="subissue-list-rightdetail">
			<span style="height: 20px;">
				<img src="/images/${newIssue.priorityIconFilename}" width="20px" height="20px">
			</span>
			<span style="width: 24px; height: 24px; border-radius: 50%; overflow: hidden; margin-right: 2px;">
				<img src="/images/${newIssue.reporterIconFilename}" class="issue-managerimg">
			</span>
			<div class="rightdetail-subissue-status" style="margin-left: 0px; position: relative;">
				<div class="issuedetail-statuswindow" style="right: 0px; top: 24px;">
					<div class="statuswindow-menubox"></div>
				</div>
				<span style="margin-right: 2px;">${newIssue.statusName}</span>
				<img src="/images/arrow_under_icon.svg" width="10px" height="10px">
			</div>
		</div>`;
		const status = issueItem.querySelector(".rightdetail-subissue-status");
		if(newIssue.status == 1){
			status.classList.add("status1");
		}else if(newIssue.status == 2){
			status.classList.add("status2");
		}else if(newIssue.status == 3){
			status.classList.add("status3");
		}
		status.setAttribute("data-projectidx", createSubIssueData.projectIdx);
		status.setAttribute("data-statusidx", createSubIssueData.statusIdx);
		status.setAttribute("data-issueidx", createSubIssueData.parentIdx);
		
		afterbox.before(issueItem);
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".create-subissue").forEach(function(btn){
	btn.addEventListener("click", function(e){
		const submitbtn = e.target.closest(".create-subissue");
		const createbox = btn.parentElement.parentElement.parentElement;
		if(submitbtn !== null && submitbtn.className.includes("submit") && submitbtn.className.includes("action")){
			const input = createbox.querySelector(".create-subissue-input");
			createSubIssueData.jiraName = createbox.dataset.jiraname;
			createSubIssueData.projectIdx = createbox.dataset.projectidx;
			createSubIssueData.parentIdx = createbox.dataset.parentidx;
			createSubIssueData.reporterIdx = createbox.dataset.useridx;
			createSubIssueData.statusIdx = createbox.dataset.statusidx;
			createSubIssueData.name = input.value;
			createSubIssueData.issueTypeIdx = createbox.querySelector(".create-subissue-type").dataset.typeidx;
			console.log(createSubIssueData);
			createSubIssuefetch(createbox);
			
			const container = e.target.closest(".issuedetail-container");
			createLogData.userIdx = container.dataset.useridx;
			createLogData.issueIdx = container.dataset.issueidx;
			createLogData.type = 9;
			createProjectLog();
		}else if(submitbtn !== null && submitbtn.className.includes("cancel")){
			popupItem.classList.remove("show");
		}
	});
});

document.querySelectorAll(".writereplybox").forEach(function(box, index){
	box.addEventListener("click", function(e) {
		const areaItem = e.target.closest(".writereplybox");
		const editorItem = areaItem.children[1];
		const btnItem = e.target.closest(".editarea-cancel");
		const saveItem = e.target.closest(".editarea-save");
	
		if(btnItem !== null && editorItem !== null){
			editorItem.classList.remove("show");
			return;
		}
		
		if(areaItem !== null && saveItem == null){
			editorItem.classList.add("show");
			editorItem.style.marginLeft = "0px";
		}
	});

});

document.querySelectorAll(".issuedetail-replylist").forEach(function(btn, index){
	btn.addEventListener("click", function(e) {
		const areaItem = e.target.closest(".issuedetail-reply");
		const editorItem = areaItem.querySelector(".editor-container");
		const replyBtnItem = e.target.closest(".reply-geteditbtn");
		const delBtnItem = e.target.closest(".reply-deletebtn");
		const btnItem = e.target.closest(".editarea-cancel");
		
		const barItem = areaItem.querySelector(".replydetail-managebar");
		const contentItem = areaItem.querySelector(".replydetail-content");
		
		if(btnItem !== null && editorItem !== null){
			editorItem.style.display = "none";
			barItem.style.display = "flex";
			contentItem.style.display = "block";
			return;
		}
		
		if(replyBtnItem !== null){
			editorItem.style.display = "block";
			editorItem.style.marginLeft = `${-1}px`;
			editorItem.style.marginTop = `${-16}px`;
			barItem.style.display = "none";
			contentItem.style.display = "none";
		}
		
		if(delBtnItem !== null){
			deleteReplyData.replyIdx = delBtnItem.dataset.replyidx;
			document.querySelector(".reply-delete-alert-container").classList.add("show");
			getReplyDetail(areaItem);
		}
	});
});

let deleteReplyData = {
	"replyIdx": ""
}

function deleteIssueReply(){
	let url = "/api/project/delete_reply";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(deleteReplyData)
	})
	.then(response => {
        if (response.ok) {
			console.log("삭제 성공");
            return response.text(); // 응답 내용을 처리하지 않으려면 여기서 끝냄
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

function getReplyDetail(box){
	const submitItem = document.querySelector(".delete-alert-submitbtn.reply");
	submitItem.addEventListener("click", function(e){
		console.log(box);
		if(deleteReplyData.replyIdx != ""){
			deleteIssueReply();
			box.remove();
			e.target.closest(".reply-delete-alert-container").classList.remove("show");
		}
	});
}

document.querySelector(".reply-delete-alert-container").addEventListener("mousedown", function(e) {
	document.querySelector(".reply-delete-alert-container")?.classList.remove("show");
	
	const bgItem = e.target.closest(".delete-alert-cancelbtn");
	const detailItem = e.target.closest(".delete-alert-box");
	
	if(bgItem == null && detailItem !== null){
		document.querySelector(".reply-delete-alert-container").classList.add("show");
	}else{
		document.querySelector(".reply-delete-alert-container").classList.remove("show");
	}
});

document.querySelectorAll(".subissue-list").forEach(function(list, index){
	list.addEventListener("click", function(e){
		if(e.target.closest(".subissue-list-rightdetail") !== null){
			return;
		}
		if(list !== null){
			list.closest(".issuedetail-container")?.classList.remove("show");
			document.querySelectorAll(".subissuedetail-container")[index]?.classList.add("show");
		}
	});
});

let statusDatas = {
	"issueIdx": "",
	"statusIdx": ""
}

function updateStatusfetch(btn){
	let url = "/api/project/update_current_status";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(statusDatas)
	})
	.then(response => response.json())
	.then(newStatus => {
		console.log(btn);
		if(btn.className.includes("issuedetail-statusbtn")){
			btn.className = '';
			btn.classList.add("issuedetail-statusbtn");
		}else{
			btn.className = '';
			btn.classList.add("rightdetail-subissue-status");
		}
		
		if(newStatus.status == 1){
			btn.classList.add("status1");
		}else if(newStatus.status == 2){
			btn.classList.add("status2");
		}else if(newStatus.status = 3){
			btn.classList.add("status3");
		}
		btn.children[1].innerText = newStatus.name;
	}).catch(error => {
			console.error("Fetch error:", error);
		});
}

let currentStatus = {
	"projectIdx" : "",
	"statusIdx" : ""
}

function fetchStatusList(statusbox){
	let url = "/api/project/get_status_list";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(currentStatus)
	})
	.then(response => response.json())
	.then(statusList => {
		statusbox.innerHTML = "";
		
		statusList.forEach(function(status){
			const statusVal = document.createElement("div");
			statusVal.classList.add("statuswindow-status");
			statusVal.setAttribute("data-statusidx", status.idx);
			
			const statusTitle = document.createElement("span");
			statusTitle.classList.add("statuswindow-title");
			statusTitle.innerHTML = `${status.name}`;
			if(status.status == 1){
				statusTitle.classList.add("status1");
			}else if(status.status == 2){
				statusTitle.classList.add("status2");
			}else if(status.status = 3){
				statusTitle.classList.add("status3");
			}
			statusVal.appendChild(statusTitle);
			statusbox.appendChild(statusVal);
			statusVal.addEventListener("click", function(e){
				statusDatas.statusIdx = `${status.idx}`;
				statusDatas.issueIdx = statusbox.parentElement.parentElement.dataset.issueidx;
				const btn = statusbox.parentElement.parentElement;
				updateStatusfetch(btn);
				
				const container = e.target.closest(".issuedetail-container");
				createLogData.userIdx = container.dataset.useridx;
				createLogData.issueIdx = container.dataset.issueidx;
				createLogData.type = 8;
				createProjectLog();
			});
		});
	}).catch(error => {
			console.error("Fetch error:", error);
		});
}

document.querySelectorAll(".issuedetail-statusbtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		btn.children[0].classList.toggle("show");
		
		if(btn !== null && e.target.closest(".issuedetail-statuswindow") == null){
			currentStatus.projectIdx = btn.dataset.projectidx;
			currentStatus.statusIdx = btn.dataset.statusidx;
			fetchStatusList(btn.children[0].children[0]);
			
			
		}
	});
});

document.querySelectorAll(".issuedetail-insertbtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		const btnItem = e.target.closest(".issuedetail-insertbtn");
			const windowItem = btnItem.children[0];
			btnItem.classList.toggle("active");
			windowItem.classList.toggle("show");
	});
});

let getSubIssueTypeData = {
	"projectIdx": ""
}

function getSubIssueTypeFetch(btn, box){
	let url = "/api/project/get_subissue_type";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(getSubIssueTypeData)
	})
	.then(response => response.json())
	.then(typeList => {
		typeList.forEach(function(type, index){
			if(index == 0){
				btn.children[1].src = `/images/${type.iconFilename}`;
				btn.children[2].innerText = type.name;
				btn.setAttribute("data-typeidx", type.idx);
				
			}else{
				const typebox = document.createElement("div");
				typebox.classList.add("typewindow-type");
				typebox.innerHTML = `<img src="/images/${type.iconFilename}">
										<span class="create-subissue-type-title">${type.name}</span>`;
				typebox.setAttribute("data-typeidx", type.idx);
				typebox.addEventListener("click", function(e){
					btn.children[1].src = `/images/${type.iconFilename}`;
					btn.children[2].innerText = type.name;
					btn.setAttribute("data-typeidx", type.idx);
					box.classList.remove("show");
				});
				box.appendChild(typebox);
			}
		});
		console.log(box);
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".insertwindow-btn.subissue").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		const listbox = btn.parentElement.parentElement.parentElement.parentElement.querySelector(".subissue-list-box");
		const createbox = listbox.querySelector(".create-subissue-box");
		listbox.classList.add("show");
		
		const grade = btn.dataset.grade;
		if(grade == 3){
			const typebtn = createbox.querySelector(".create-subissue-type");
			getSubIssueTypeData.projectIdx = btn.dataset.projectidx;
			console.log(getSubIssueTypeData);
			getSubIssueTypeFetch(typebtn, typebtn.children[0]);
			typebtn.classList.add("active");
			typebtn.style.border = "1px solid #8C8F97";
		}
		createbox.classList.add("show");
	});
});

document.querySelectorAll(".create-subissue-type").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".create-subissue-type.active") !== null){
			btn.style.border = "2px solid #1868DB";
			btn.children[0].classList.toggle("show");
		}
	});
});

document.querySelectorAll(".rightdetail-subissue-status").forEach(function(btn, index){
	btn.addEventListener("mousedown", function(e){
		if(e.target.closest(".statuswindow-menubox") !== null){
			return;
		}
		const windowItem = btn.children[0];
		console.log("hi");
		currentStatus.projectIdx = btn.dataset.projectidx;
		currentStatus.statusIdx = btn.dataset.statusidx;
		fetchStatusList(windowItem.children[0]);
		windowItem.classList.toggle("show");
	});

});

document.querySelector(".issuedetail-sortbtn").addEventListener("click", function(e){
	const btnItem = e.target.closest(".issuedetail-sortbtn");
	btnItem.classList.toggle("active");
});

document.querySelectorAll(".issuedetail-graphval").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".graphval-selectwindow") !== null){
			return;
		}
		const xyItem = btn.getBoundingClientRect();
		const window = btn.querySelector(".graphval-selectwindow");
		window.style.top = `${xyItem.height + 8}px`;
		window.classList.toggle("show");
	});
});

let labelDatas = {
	"idx": [],
	"issueIdx": ""
}

let newLabelData = {
	"issueIdx": "",
	"labelIdx": ""
}

function createLabelData(graphval){
	let url = "/api/project/create_label_data";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(newLabelData)
	})
	.then(response => response.json())
	.then(labelData => {
		const label = document.createElement("span");
		label.classList.add("graphval-label");
		label.innerHTML = `<a>${labelData.name}</a>
					<img src="/images/cancel_icon.svg" style="width: 16px; height: 16px; margin-top: 2px; display: none;">`;
		label.setAttribute("data-idx", labelData.labelIdx);
		label.setAttribute("data-labeldataidx", labelData.labelDataIdx);
		graphval.appendChild(label);
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

function fetchInput(){
	let url = "/api/project/get_label_list";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(labelDatas)
	})
	.then(response => response.json())
	.then(alterLabelList => {
		const uniqueLabels = alterLabelList.filter((value, index, self) =>
           index === self.findIndex(item => item.name === value.name)
       );
		document.querySelectorAll(".graphval-selectwindow.label").forEach(function(label, index){
			label.innerHTML = "";
			const labelTitle = document.createElement("span");
			labelTitle.innerHTML = `<span class="graphval-selectwindow-title">모든 레이블</span>`;
			label.appendChild(labelTitle);
			
			uniqueLabels.forEach(function(value){	
				const labelValue = document.createElement("div");
				labelValue.classList.add("graphvalwindow-value");
				labelValue.innerHTML = `<span>${value.name}</span>`;
				labelValue.setAttribute("data-issueidx", labelDatas.issueIdx);
				labelValue.setAttribute("data-labelidx", value.labelIdx);
				labelValue.addEventListener("click", function(e){
					console.log(label.parentElement);
					newLabelData.issueIdx = labelValue.dataset.issueidx;
					newLabelData.labelIdx = labelValue.dataset.labelidx;
					label.classList.remove("show");
					createLabelData(label.parentElement);
				});
				label.appendChild(labelValue);
			});
		});
	}).catch(error => {
		console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".issuedetail-graphval.label-def").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".graphval-selectwindow") !== null){
			return;
		}
		const labelList = btn.querySelectorAll(".graphval-label");

		for(let i = 0; i < labelList.length; i++){
			const deleteLabelBtn = labelList[i].children[1];
			deleteLabelBtn.classList.toggle("show-labelicon");
		}
		
		let label = [...btn.querySelectorAll(".graphval-label")].map(input => input.dataset.idx);
		label = label.length > 0 ? label : [-1];
		labelDatas.idx = label;
		labelDatas.issueIdx = btn.dataset.issueidx;
		fetchInput();
		
	});
});

removeLabelDataValue = {
	"labelDataIdx": ""
}

function deleteLabelData(){
	let url = "/api/project/delete_label_data";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(removeLabelDataValue)
	})
	.then(response => {
        if (response.ok) {
			console.log("삭제 성공");
            return response.text(); // 응답 내용을 처리하지 않으려면 여기서 끝냄
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

document.querySelectorAll(".graphval-delete-labelbtn").forEach(function(btn){
	btn.addEventListener("click", function(e){
		removeLabelDataValue.labelDataIdx = btn.parentElement.dataset.labeldataidx;
		console.log(removeLabelDataValue);
		btn.parentElement.remove();
		deleteLabelData();
	});
});

let priorityDatas = {
	"issueIdx": "",
	"priorityIdx": "",
	"iconFilename": "",
	"name": ""
}

function updatePriorityFetch(graphval){
	let url = "/api/project/update_priority";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(priorityDatas)
	})
	.then(response => response.json())
	.then(priority => {
		graphval.dataset.priorityidx = priority.priorityIdx;
		graphval.dataset.iconFilename = priority.iconFilename;
		graphval.children[1].children[0].src = `/images/${priority.iconFilename}`;
		graphval.children[2].innerHTML = `${priority.name}`;
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

function getPriorityListFetch(window){
	let url = "/api/project/get_priority_list";
		fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json' // JSON 데이터를 전송
			},
			body: JSON.stringify(priorityDatas)
		})
		.then(response => response.json())
		.then(priorityList => {
			window.innerHTML = "";
			priorityList.forEach(function(item){
				const value = document.createElement("div");
				value.classList.add("graphvalwindow-value");
				value.innerHTML = `<span style="height: 16px;"><img src="/images/${item.iconFilename}"></span>
								<span>${item.name}</span>`;
				window.appendChild(value);
				value.addEventListener("click", function(e){
					priorityDatas.priorityIdx = item.priorityIdx;
					priorityDatas.iconFilename = item.iconFilename;
					priorityDatas.name = item.name;
					updatePriorityFetch(window.parentElement);
					
					const container = e.target.closest(".issuedetail-container");
					createLogData.userIdx = container.dataset.useridx;
					createLogData.issueIdx = container.dataset.issueidx;
					createLogData.type = 15;
					createProjectLog();
					window.classList.remove("show");
				});
			});
		}).catch(error => {
				console.error("Fetch error:", error);
			});
}

document.querySelectorAll(".issuedetail-graphval.priority").forEach(function(btn){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".graphvalwindow-value") !== null){
			return;
		}
		priorityDatas.issueIdx = btn.dataset.issueidx;
		priorityDatas.priorityIdx = btn.dataset.priorityidx;
		priorityDatas.iconFilename = btn.dataset.iconfilename;
		getPriorityListFetch(btn.children[0]);
	});
});

let teamDatas = {
	"teamIdx": "",
	"jiraIdx": "", 
	"name": "", 
	"iconFilename": "",
	"issueIdx": ""
}

function updateTeamFetch(graphval){
	let url = "/api/project/update_team";
			fetch(url, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json' // JSON 데이터를 전송
				},
				body: JSON.stringify(teamDatas)
			})
			.then(response => response.json())
			.then(team => {
				graphval.dataset.teamidx = team.teamIdx;
				graphval.dataset.jiraidx = team.jiraIdx;
				graphval.dataset.issueidx = team.issueIdx;
				graphval.children[1].innerHTML = `${team.name}`;
			}).catch(error => {
					console.error("Fetch error:", error);
			});
}

function getTeamListFetch(window){
	let url = "/api/project/get_team_list";
		fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json' // JSON 데이터를 전송
			},
			body: JSON.stringify(teamDatas)
		})
		.then(response => response.json())
		.then(teamList => {
			window.innerHTML = "";
			teamList.forEach(function(item){
				const value = document.createElement("div");
				value.classList.add("graphvalwindow-value");
				value.classList.add("withimg");
				value.innerHTML = `<span style="height: 32px;">
										<img src="/images/${item.iconFilename}" style="width: 32px; height: 32px; border-radius: 50%; margin-left: 6px;">
									</span>
									<div>
										<span>${item.name}</span>
									</div>`;
				window.appendChild(value);
				value.addEventListener("click", function(e){
					teamDatas.teamIdx = item.teamIdx;
					updateTeamFetch(window.parentElement);
					
					const container = e.target.closest(".issuedetail-container");
					createLogData.userIdx = container.dataset.useridx;
					createLogData.issueIdx = container.dataset.issueidx;
					createLogData.type = 12;
					createProjectLog();
					
					window.classList.remove("show");
				});
			});
		}).catch(error => {
				console.error("Fetch error:", error);
			});
}

document.querySelectorAll(".issuedetail-graphval.team").forEach(function(btn){
	btn.addEventListener("click", function(e){
		teamDatas.teamIdx = btn.dataset.teamidx;
		teamDatas.jiraIdx = btn.dataset.jiraidx;
		teamDatas.issueIdx = btn.dataset.issueidx;
		getTeamListFetch(btn.children[0]);
	});
});

detailUserDatas = {
	"userIdx": "",
	"projectIdx": "",
	"issueIdx": "",
	"type": "",
	"name": "",
	"iconFilename": "",
	"currentUserIdx": "",
	"currentUserIcon": ""
}

function updateUserFetch(graphval){
	let url = "/api/project/update_user";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(detailUserDatas)
	})
	.then(response => response.json())
	.then(reporter => {
		console.log(reporter);
		graphval.dataset.reporteridx = reporter.reporterIdx;
		graphval.dataset.projectidx = reporter.projectIdx;
		graphval.dataset.issueidx = reporter.issueIdx;
		graphval.children[1].children[0].src = `/images/${reporter.iconFilename}`;
		graphval.children[2].innerHTML = `${reporter.name}`;
	}).catch(error => {
			console.error("Fetch error:", error);
	});
}

function getReporterListFetch(window){
	let url = "/api/project/get_user_list";
		fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json' // JSON 데이터를 전송
			},
			body: JSON.stringify(detailUserDatas)
		})
		.then(response => response.json())
		.then(reporterList => {
			window.innerHTML = "";
			
			if(detailUserDatas.type == "manager"){
				const myValue = document.createElement("div");
				myValue.classList.add("graphvalwindow-value");
				myValue.classList.add("withimg");
				myValue.innerHTML = `<span>
										<img src="/images/${detailUserDatas.currentUserIcon}"
										style="width: 32px; height: 32px; margin-left: 6px;">
										</span>
									<span>나에게 할당</span>`;
				window.appendChild(myValue);
				myValue.addEventListener("click", function(e){
					detailUserDatas.userIdx = detailUserDatas.currentUserIdx;
					updateUserFetch(window.parentElement);
					
					const container = e.target.closest(".issuedetail-container");
					createLogData.userIdx = container.dataset.useridx;
					createLogData.issueIdx = container.dataset.issueidx;
					createLogData.type = 16;
					createProjectLog();
					
					window.classList.remove("show");
				});
			}
			
			reporterList.forEach(function(item){
				const value = document.createElement("div");
				value.classList.add("graphvalwindow-value");
				value.classList.add("withimg");
				value.innerHTML = `<span style="height: 32px;">
										<img src="/images/${item.iconFilename}" style="width: 32px; height: 32px; border-radius: 50%; margin-left: 6px;">
									</span>
									<div>
										<span>${item.name}</span>
									</div>`;
				window.appendChild(value);
				value.addEventListener("click", function(e){
					detailUserDatas.userIdx = item.userIdx;	
					updateUserFetch(window.parentElement);
					
					const container = e.target.closest(".issuedetail-container");
					createLogData.userIdx = container.dataset.useridx;
					createLogData.issueIdx = container.dataset.issueidx;
					if(detailUserDatas.type == "manager"){
						createLogData.type = 16;
					}else{
						createLogData.type = 6;
					}
					createProjectLog();
					
					window.classList.remove("show");
				});
			});
		}).catch(error => {
				console.error("Fetch error:", error);
			});
}

document.querySelectorAll(".issuedetail-graphval.reporter").forEach(function(btn){
	btn.addEventListener("click", function(e){
		detailUserDatas.userIdx = btn.dataset.reporteridx;
		detailUserDatas.projectIdx = btn.dataset.projectidx;
		detailUserDatas.issueIdx = btn.dataset.issueidx;
		detailUserDatas.currentUserIdx = btn.dataset.currentuseridx;
		detailUserDatas.currentUserIcon = btn.dataset.currentusericon;
		detailUserDatas.type = "reporter";
		getReporterListFetch(btn.children[0]);
	});
});

document.querySelectorAll(".issuedetail-graphval.manager").forEach(function(btn){
	btn.addEventListener("click", function(e){
		detailUserDatas.userIdx = btn.dataset.manageridx;
		detailUserDatas.projectIdx = btn.dataset.projectidx;
		detailUserDatas.issueIdx = btn.dataset.issueidx;
		detailUserDatas.iconFilename = btn.dataset.iconfilename;
		detailUserDatas.currentUserIdx = btn.dataset.currentuseridx;
		detailUserDatas.currentUserIcon = btn.dataset.currentusericon;
		detailUserDatas.type = "manager";
		getReporterListFetch(btn.children[0]);
	});
});

const columns = document.querySelectorAll(".issuebox-issues");

draggedIssueDatas = {
	"issueIdx": "",
	"statusIdx": "",
	"oldIdx": "",
	"newIdx": "",
	"oldStatusIdx": ""
}

function updateDraggedIssueOrder(){
	let url = "/api/project/update_issue_order";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(draggedIssueDatas)
	})
	.catch(error => {
		console.error("Fetch error:", error);
	});
}

function updateDraggedIssue(){
	let url = "/api/project/update_current_status";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(draggedIssueDatas)
	})
	.then(response => {
        if (response.ok) {
			updateDraggedIssueOrder();
            return response.text(); // 응답 내용을 처리하지 않으려면 여기서 끝냄
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    }).catch(error => {
		console.error("Fetch error:", error);
	});
}

columns.forEach((column) => {
    new Sortable(column, {
        group: "shared",
        animation: 150,
        ghostClass: "blue-background-class",
		filter: ".subissuebtn, .issue-menubtn",
		preventOnFilter: true,
		onStart: function (evt) {
	        originalIndex = evt.oldIndex; // 드래그 시작 시의 인덱스
			draggedIssueDatas.oldStatusIdx = evt.item.parentElement.dataset.statusidx;
	    },
		
		// Element dragging ended  
		onEnd: function (e) {  
			//드롭했을때 이벤트 실행  	
			const issueItem = e.item;
			const issueBoxItem = e.item.parentElement;
			console.log(issueItem);  
			
			const newIndex = e.newIndex; // 드래그 종료 후의 인덱스
			draggedIssueDatas.issueIdx = issueItem.dataset.idx;
			draggedIssueDatas.statusIdx = issueBoxItem.dataset.statusidx;
			draggedIssueDatas.oldIdx = originalIndex;
			draggedIssueDatas.newIdx = newIndex;
			console.log(draggedIssueDatas);
			
			updateDraggedIssue();
		}
    });
});

let issueBoxOrderDatas = {
	"oldIdx": "",
	"newIdx": "",
	"projectIdx": ""
}

function updateIssueBoxOrderFetch(){
	let url = "/api/project/update_issue_box_order";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(issueBoxOrderDatas)
	})
	.catch(error => {
		console.error("Fetch error:", error);
	});
}

const containers = document.querySelector(".board-body-container");

new Sortable(containers, {
	haldle: ".issuebox-moveicon",
	animation: 150,
	filter: ".create-status-btn, .issuebox-issues, .createissue-container",
	preventOnFilter: true,
	// 드래그 시작 시 요소의 초기 인덱스를 저장
    onStart: function (evt) {
        originalIndex = evt.oldIndex; // 드래그 시작 시의 인덱스
    },

    // 드래그 종료 후 새로운 위치를 확인
    onEnd: function (evt) {
        const newIndex = evt.newIndex; // 드래그 종료 후의 인덱스
		issueBoxOrderDatas.oldIdx = originalIndex;
		issueBoxOrderDatas.newIdx = newIndex;
		issueBoxOrderDatas.projectIdx = evt.item.dataset.projectidx;
		updateIssueBoxOrderFetch();
    }
});