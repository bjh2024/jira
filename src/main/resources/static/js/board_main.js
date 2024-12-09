document.querySelectorAll(".editor").forEach(function(editor, index){
	const contentEditor = new toastui.Editor({
	    el: editor,
	    height: 'auto',
		minHeight: '74px',
	    initialEditType: 'wysiwyg',
	    initialValue: 'markdown',
	    previewStyle: 'vertical',
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
			document.querySelectorAll(".createissuebtn")[index].style.backgroundColor = "#0C66E4";
			document.querySelectorAll(".createissuebtn")[index].style.color = "white";
			document.querySelectorAll(".createissuebtn")[index].style.cursor = "pointer";
		}else{
			document.querySelectorAll(".createissuebtn")[index].style.backgroundColor = "#091E4208";
			document.querySelectorAll(".createissuebtn")[index].style.color = "#A5ADBA";
			document.querySelectorAll(".createissuebtn")[index].style.cursor = "not-allowed";
		}
	})
});

document.querySelectorAll(".createissue-typebtn").forEach(function(btn, index){
	btn.addEventListener("click", function(e){
		
		// document.querySelector(".issuetypeselectbox.show")[index].classList.remove("show");
		e.target.closest(".issuetypeselectbox.show")?.classList.remove("show");
		
		const issueTypeBtn = document.querySelectorAll(".createissue-typebtn")[index];
		const issueTypeList = document.querySelectorAll(".issuetypeselectbox")[index];
		const xyItem = issueTypeBtn.getBoundingClientRect();
		
 		if(issueTypeBtn !== null){
			issueTypeList.classList.add("show");
			if(xyItem.top >= 600){
				issueTypeList.style.marginTop = "26px";
				issueTypeList.style.marginLeft = "-242px";
			}else{
				issueTypeList.style.left = xyItem.left;
				issueTypeList.style.top = xyItem.top;
				issueTypeList.style.marginLeft = "16px";
				issueTypeList.style.marginTop = "60px";
			}
		}else{
			issueTypeList.classList.remove("show");
		}
	})
});

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

reporterDatas = {
	"reporterIdx": "",
	"projectIdx": "",
	"issueIdx": "",
	"type": "",
	"name": "",
	"iconFilename": ""
}

function updateReporterFetch(graphval){
	let url = "/api/project/update_reporter";
			fetch(url, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json' // JSON 데이터를 전송
				},
				body: JSON.stringify(reporterDatas)
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
	let url = "/api/project/get_reporter_list";
		fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json' // JSON 데이터를 전송
			},
			body: JSON.stringify(reporterDatas)
		})
		.then(response => response.json())
		.then(reporterList => {
			console.log(reporterList);
			window.innerHTML = "";
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
					reporterDatas.reporterIdx = item.reporterIdx;	
					updateReporterFetch(window.parentElement);
					window.classList.remove("show");
				});
			});
		}).catch(error => {
				console.error("Fetch error:", error);
			});
}

document.querySelectorAll(".issuedetail-graphval.reporter").forEach(function(btn){
	btn.addEventListener("click", function(e){
		reporterDatas.reporterIdx = btn.dataset.reporteridx;
		reporterDatas.projectIdx = btn.dataset.projectidx;
		reporterDatas.issueIdx = btn.dataset.issueidx;
		reporterDatas.type = "reporter";
		console.log(reporterDatas);
		getReporterListFetch(btn.children[0]);
	});
});

document.querySelectorAll(".issuedetail-graphval.reporter").forEach(function(btn){
	btn.addEventListener("click", function(e){
		reporterDatas.reporterIdx = btn.dataset.reporteridx;
		reporterDatas.projectIdx = btn.dataset.projectidx;
		reporterDatas.issueIdx = btn.dataset.issueidx;
		reporterDatas.type = "manager";
		console.log(reporterDatas);
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

columns.forEach((column) => {
    new Sortable(column, {
        group: "shared",
        animation: 150,
        ghostClass: "blue-background-class"
    });
});

/*document.querySelectorAll(".issuebox-issues").forEach(function(box, index){
	box.addEventListener("dragover", function(e){
		e.preventDefault();
	    const afterElement = getDragAfterElement(box, e.clientX);
	    const draggable = document.querySelector(".dragging");
	    if (afterElement === undefined) {
	      box.appendChild(draggable);
	    } else {
	      box.insertBefore(draggable, afterElement);
	    }
	});
	
	box.addEventListener("drop", function(e){
			
	});
});*/

/*function getDragAfterElement(box, x) {
  const draggableElements = [
    ...box.querySelectorAll(".draggable:not(.dragging)"),
];

  return draggableElements.reduce(
    (closest, child) => {
      const box = child.getBoundingClientRect();
      const offset = x - box.left - box.width / 2;
      // console.log(offset);
      if (offset < 0 && offset > closest.offset) {
        return { offset: offset, element: child };
      } else {
        return closest;
      }
    },
    { offset: Number.NEGATIVE_INFINITY },
  ).element;
}*/