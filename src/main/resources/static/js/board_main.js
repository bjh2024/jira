document.querySelectorAll(".editor.exarea").forEach(function(editor, index){
	const contentEditor = new toastui.Editor({
	    el: editor,
	    height: 'auto',
		minHeight: '74px',
	    initialEditType: 'wysiwyg',
	    initialValue: editor.dataset.content,
	    previewStyle: 'vertical',
	  });
});

document.querySelectorAll(".editor.newreply").forEach(function(editor, index){
	const contentEditor = new toastui.Editor({
	    el: editor,
	    height: 'auto',
		minHeight: '74px',
	    initialEditType: 'wysiwyg',
	    initialValue: '댓글 작성...',
	    previewStyle: 'vertical',
	  });
});

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

document.querySelectorAll(".reply-geteditbtn").forEach(function(btn){
	btn.addEventListener("click", function(e){
		if (e.target.matches('.reply-geteditbtn')) {
	        const isEdit = e.target.textContent === '편집'; // 편집 버튼인지 확인
	        const editorContainer = e.target.closest('.replydetail-managebar').nextElementSibling; // 에디터 컨테이너 찾기
	        const editorTarget = editorContainer.querySelector('.editor');
	        const editContent = isEdit ? e.target.closest('.reply-geteditbtn').dataset.content : ''; // 편집: 기존 내용, 댓글: 빈 내용

	        // 기존 에디터 제거 후 새로 초기화
	        if (editorTarget._toastuiEditorInstance) {
	            editorTarget._toastuiEditorInstance.destroy(); // 기존 에디터 인스턴스 제거
	        }

	        // 에디터 생성
	        initEditor(editorTarget, editContent);

	        // 에디터 표시
	        editorContainer.style.display = 'block';
	    }
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
}

document.querySelector("body").addEventListener("click", function(e) {
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
		const lblItem = btn.querySelector(".issuedetail-graphval.label-def-sub");
		
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
	})
	.then(response => {
        if (response.ok) {
            // 응답 상태가 200~299 범위인 경우
            console.log("성공");
			location.reload();
            return response.text(); // 응답 내용을 처리하지 않으려면 여기서 끝냄
        } else {
            // 응답 상태가 성공 범위를 벗어나는 경우
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    })
	.catch(error => {
			console.error("Fetch error:", error);
	});
}

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
		createissuefetch();
	});
});

document.querySelector(".create-status-btn").addEventListener("click", function(e){
	const btnItem = e.target.closest(".create-status-btn");
	if(btnItem !== null){
		btnItem.children[0].classList.add("show");
	}
});

document.querySelector(".create-status-container").addEventListener("mousedown", function(e){
	document.querySelector(".create-status-container")?.classList.remove("show");
	const container = document.querySelector(".create-status-container");
	const detailItem = e.target.closest(".create-statusbox");
	const bgItem = e.target.closest(".status-cancel-btn");
	const submitItem = e.target.closest(".status-create-btn");
	
	if(bgItem == null && submitItem == null && detailItem !== null){
		container.classList.add("show");
	}else{
		if(detailItem !== null){
			getStatusSubmit();
			return;
		}
		container.classList.remove("show");
	}
});

let createStatusDatas = {
	"name": "",
	"status": "",
	"projectIdx": ""
}

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
	const btnItem = document.querySelector(".status-create-btn");
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
	})
	.catch(error => {
		console.error("Fetch error:", error);
	});
}

function getStatusSubmit(){
	createStatusDatas.name = document.querySelector(".status-title-input").value;
	createStatusDatas.status = document.querySelector(".set-status-left.current").dataset.idx;
	createStatusDatas.projectIdx = document.querySelector(".status-create-btn").dataset.projectidx;
	document.querySelector(".create-status-container").classList.remove("show");
	createStatusFetch();
	location.reload();
}

document.querySelectorAll(".issues").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".subissuebtn") !== null || e.target.closest(".issue-menubtn") !== null
			|| e.target.closest(".subissuebox") !== null){
			return;
		}
		
		const lblItem = btn.querySelector(".issuedetail-graphval.label-def");
		if(lblItem?.children.length < 3){
			lblItem.querySelector(".graphval-label-def").classList.remove("none");
		}else{
			lblItem.querySelector(".graphval-label-def").classList.add("none");
		}
		
		if(btn !== null){
			btn.querySelector(".issuedetail-container").classList.add("show");
		}
	});
});

document.querySelectorAll(".issuedetail-container").forEach(function(container, index){
	container.addEventListener("mousedown", function(e) {
		document.querySelectorAll(".issuedetail-container")[index]?.classList.remove("show");
		
		const bgItem = e.target.closest(".issuedetail-off");
		const subIssueItem = e.target.closest(".subissue-list");
		const issueDetailItem = e.target.closest(".issuedetailbox");
		
		if(bgItem == null && issueDetailItem !== null){
			
			container.classList.add("show");
		}else{
			container.classList.remove("show");
			// location.reload(true);
			/*if(e.target.closest(".subissue-list-rightdetail") !== null){
				container.classList.add("show");	
			}*/
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

document.querySelectorAll(".issuedetail-exarea").forEach(function(area, index){
	area.addEventListener("click", function(e) {
		const areaItem = e.target.closest(".issuedetail-exarea");
		const editorItem = areaItem.children[0];
		const valItem = areaItem.children[1];
		const btnItem = e.target.closest(".editarea-cancel");
		
	
		if(btnItem !== null && editorItem !== null){
			editorItem.style.display = "none";
			valItem.style.display = "block";
			areaItem.style.padding = "6px 8px";
			return;
		}
		
		if(areaItem !== null){
			editorItem.style.display = "block";
			valItem.style.display = "none";
			areaItem.style.padding = "0px";
			editorItem.style.marginLeft = "8px";
		}
	});

});

document.querySelectorAll(".writereplybox").forEach(function(box, index){
	box.addEventListener("click", function(e) {
		const areaItem = e.target.closest(".writereplybox");
		const editorItem = areaItem.children[1];
		const btnItem = e.target.closest(".editarea-cancel");
		
	
		if(btnItem !== null && editorItem !== null){
			editorItem.style.display = "none";
			areaItem.style.padding = "6px 8px";
			return;
		}
		
		if(areaItem !== null){
			editorItem.style.display = "block";
			areaItem.style.padding = "0px";
			editorItem.style.marginLeft = "8px";
		}
	});

});

document.querySelectorAll(".issuedetail-replylist").forEach(function(btn, index){
	btn.addEventListener("click", function(e) {
		const areaItem = e.target.closest(".issuedetail-reply");
		const editorItem = areaItem.querySelector(".editor-container");
		const replyBtnItem = e.target.closest(".reply-geteditbtn");
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
	});

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
		btn.className = '';
		btn.classList.add("issuedetail-statusbtn");
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

function fetchStatusList(){
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
		document.querySelectorAll(".statuswindow-menubox").forEach(function(box){
			box.innerHTML = "";
			
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
				// statusVal.innerHTML += `<input type="hidden" class="send-status-value" value="${status.idx}">`;
				box.appendChild(statusVal);
				statusVal.addEventListener("click", function(e){
					statusDatas.statusIdx = `${status.idx}`;
					statusDatas.issueIdx = box.parentElement.parentElement.dataset.issueidx;
					const btn = box.parentElement.parentElement;
					updateStatusfetch(btn);
				});
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
			fetchStatusList();
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

document.querySelectorAll(".rightdetail-subissue-status").forEach(function(btn, index){
	btn.addEventListener("mousedown", function(e){
		if(e.target.closest(".statuswindow-menubox") !== null){
			return;
		}
		const windowItem = btn.children[0];
		
		windowItem.classList.toggle("show");
	});

});

document.querySelector(".issuedetail-sortbtn").addEventListener("click", function(e){
	const btnItem = e.target.closest(".issuedetail-sortbtn");
	btnItem.classList.toggle("active");
});

document.querySelectorAll(".issues").forEach(function(box, index){
	box.addEventListener("dragstart", function(e){
		if(e.target.closest(".subissuebtn") !== null){
			return;
		}
		box.classList.add("dragging");
	});
	box.addEventListener("dragend", function(e){
		box.classList.remove("dragging");
	});
});



document.querySelectorAll(".issuedetail-graphval").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		if(e.target.closest(".graphval-selectwindow") !== null){
			return;
		}
		btn.querySelector(".graphval-selectwindow").classList.toggle("show");

	});
	
	/*btn.addEventListener("mouseover", function(e){
		
	});
	
	btn.addEventListener("mouseout", function(e){
		
	});*/
});

let labelDatas = {
	"idx": []
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
		
		let label = [...btn.querySelectorAll(".graphval-label")].map(input => input.dataset.idx);
		label = label.length > 0 ? label : [-1];
		labelDatas.idx = label;
		fetchInput();
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
					console.log(priorityDatas.issueIdx);
					priorityDatas.priorityIdx = item.priorityIdx;
					priorityDatas.iconFilename = item.iconFilename;
					priorityDatas.name = item.name;
					updatePriorityFetch(window.parentElement);
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
			console.log(reporterList);
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

/*document.querySelectorAll(".issue-container").forEach(function(box, index){
	box.addEventListener("click", function(e){
		if(e.target.closest(".issuebox-moveicon") !== null){
			return;
		}
		
		const defItem = document.querySelectorAll(".issue-container");
		for(let i = 0; i < defItem.length; i++){
			
		}
		
		const boxItem = e.target.closest(".issue-container");
		if(boxItem !== null){
			boxItem.draggable = "false";
		}
	});
});*/

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
	filter: ".create-status-btn",
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
			console.log(issueBoxOrderDatas);
			updateIssueBoxOrderFetch();
	    }
});