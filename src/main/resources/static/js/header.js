document.querySelectorAll(".account_jira_list a").forEach(function(jiraBtn){
	jiraBtn.addEventListener("click", function(){
		const jiraIdx = this.getAttribute("idx-data");
		const uri = `/api/header/setJiraIdx`
		fetch(uri, {method:"post", headers:{"Content-Type" : "application/json"}, body:JSON.stringify(jiraIdx)})
		.catch(err => {
			console.error(err);
		})
		location.href="/";
	});
});
let prevBtn = "";
document.querySelector("body").addEventListener("mousedown", function(e) {
	if (e.target.closest(".my_app_btn") === null) {
		document.querySelector(".my_app_content.item_lnb.show")?.classList.remove("show");
	}

	if (e.target.closest(".search_box") === null) {
		document.querySelector(".search_lnb_box.show")?.classList.remove("show");
	}

	if (e.target.closest(".rable_content_box") === null) {
		document.querySelector(".rable_content_box.show")?.classList.remove("show");
	}

	// gnb2_box click 이벤트
	if (e.target.closest(".gnb2_box_btn") === null) {
		document.querySelectorAll(".gnb2_box_icon").forEach(function(btn) {
			btn.nextElementSibling?.classList.remove("show");
		});
	} else {
		if (e.target.closest(".gnb2_box_content") !== null) return;
		if (e.target.closest(".gnb2_box_icon") !== prevBtn) {
			document.querySelectorAll(".gnb2_box_icon").forEach(function(btn) {
				btn.nextElementSibling?.classList.remove("show");
			});
		}
		prevBtn = e.target.closest(".gnb2_box_icon");
		e.target.closest(".gnb2_box_icon")?.nextElementSibling?.classList.toggle("show");
	}

});

// my_app btn click 이벤트
document.querySelector(".my_app_btn .img_box").addEventListener("click", function() {
	this.nextElementSibling.classList.toggle("show");
});

// search_box click 이벤트
document.querySelector(".header_input_container input").addEventListener("click", function() {
	this.nextElementSibling.classList.add("show");

});

// input_filtering label box click 이벤트
document.querySelector(".rable_filtering input").addEventListener("click", function() {
	document.querySelector(".rable_content_box").classList.toggle("show");
})

function setLocalTime(time){
	time = new Date(time.getTime() - time.getTimezoneOffset() * 60 * 1000);
	time = time.toISOString();
	return time;
}

let initStartDate = new Date();
initStartDate.setDate(initStartDate.getDate() - 365 * 5);
let headerInputDatas = {
	"startDate": setLocalTime(initStartDate),
	"endDate": setLocalTime(new Date),
	"projectIdxArr": [],
	"managerIdxArr": [],
	"isReporter": false,
	"statusArr": [],
	"searchText": ""
}

function fetchInput() {
	document.querySelector(".input_recent_dynamic").classList.add("show");
	document.querySelector(".input_recent_box1").classList.remove("show");
	// fetch()를 사용하여 AJAX 요청
	let url = "/api/header/filter";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(headerInputDatas)
	})
		.then(response => response.json())  // JSON 형태로 응답 받기
		.then(issueList => {
			const recentBox = document.querySelector(".input_recent_dynamic");
			recentBox.innerHTML = "";
			if(issueList.length === 0){
				recentBox.innerHTML = `<div class="no_search_box">
											<img src="/images/no_search_icon_light.svg"></img>
											<p>검색과 일치하는 결과가 없습니다.</p>
										</div>`;
				return;
			}
			let h5Element = document.createElement('h5');
			h5Element.innerHTML = `이슈 <span>${issueList.length}</span>`;
			recentBox.appendChild(h5Element);

			const divContainer = document.createElement("div");
			issueList.forEach(function(issue) {
				const aElement = document.createElement("a");
				let url = new URL(window.location.href);
				aElement.classList.add("input_recent_content");
				aElement.innerHTML = `<img src="/images/${issue.iconFilename}"></img>
									  <div>
										  <div class='issue_info1'>
											  <span>${issue.key}</span>
											  <span>${issue.name}</span>
										</div>
										<div class='issue_info2'>
											<span>${issue.projectName}</span>
											<span>${issue.elapsedTime}</span>
										</div>
									   </div>`;
				divContainer.appendChild(aElement);
			});
			recentBox.appendChild(divContainer);
		})
		.catch(error => {
			console.error("Fetch error:", error);
		});
}

// input 마지막 업데이트 click 이벤트
document.querySelectorAll(".last_update_box>ul>li").forEach(function(li) {
	li.addEventListener("click", function() {
		// div 교체
		document.querySelector(".input_recent_dynamic").classList.add("show");
		document.querySelector(".input_recent_box1").classList.remove("show");
		
		document.querySelector(".last_update_box>ul>.active").classList.remove("active");
		this.classList.add("active");

		let today = new Date();
		let startDate = new Date();
		let endDate = new Date();

		switch (this.innerText) {
			case "오늘":
				break;
			case "어제":
				startDate.setDate(today.getDate() - 1);
				endDate.setDate(today.getDate() - 1);
				endDate.setHours(23, 59, 59, 999);
				break;
			case "지난 7일":
				startDate.setDate(today.getDate() - 7);
				break;
			case "지난 30일":
				startDate.setDate(today.getDate() - 30);
				break;
			case "지난해":
				startDate.setDate(today.getDate() - 365);
				break;
			case "모든 기간":
				if(headerInputDatas.projectIdxArr.length === 0 &&
				   headerInputDatas.managerIdxArr.length === 0 &&
				   !headerInputDatas.isReporter &&
				   headerInputDatas.statusArr.length === 0
				){  
					document.querySelector(".input_recent_dynamic").classList.remove("show");
					document.querySelector(".input_recent_box1").classList.add("show");
					return;
				}
				startDate.setDate(today.getDate() - 365 * 5);
				break;
		}
		startDate.setHours(0, 0, 0, 0);
		// 로컬 시간대 변경
		startDate = setLocalTime(startDate);
		endDate = setLocalTime(endDate);

		headerInputDatas.startDate = startDate;
		headerInputDatas.endDate = endDate;
		fetchInput();
	});
});

let projectIdxArr = [];
let managerIdxArr = [];
let isReporter = false;
let statusArr = [];
document.querySelectorAll(".filtering_box").forEach(function(box) {
	box.querySelectorAll("input").forEach(function(input) {
		input.addEventListener("change", function(e) {

			let filter = "";
			if (box.className.includes("project")) {
				filter = e.target.getAttribute("id").slice(-1);
				if (e.target.checked) {
					projectIdxArr.push(filter);
				} else {
					let index = projectIdxArr.indexOf(filter);
					projectIdxArr.splice(index, 1);
				}
			} else if (box.className.includes("manager")) {
				filter = e.target.getAttribute("id").slice(-1);
				if (e.target.checked) {
					managerIdxArr.push(filter);
				} else {
					let index = managerIdxArr.indexOf(filter);
					managerIdxArr.splice(index, 1);
				}
			} else if (box.className.includes("reporter")) {
				isReporter = e.target.checked
			} else if (box.className.includes("status")) {
				filter = e.target.getAttribute("id");
				if (filter == "OPEN") {
					if (e.target.checked) {
						statusArr.push(1);
						statusArr.push(2);
					} else {
						statusArr.splice(statusArr.indexOf(1), 1);
						statusArr.splice(statusArr.indexOf(2), 1);
					}
				} else {
					if (e.target.checked) {
						statusArr.push(3);
					} else {
						statusArr.splice(statusArr.indexOf(3), 1);
					}
				}
			}
			headerInputDatas.projectIdxArr = projectIdxArr;
			headerInputDatas.managerIdxArr = managerIdxArr;
			headerInputDatas.isReporter = isReporter;
			headerInputDatas.statusArr = statusArr;
			fetchInput();
		});
	});
});

function debounce(func, timeout = 300){
	let timer;
	return (...args)=>{
		clearTimeout(timer);
		timer = setTimeout(() => {
			func.apply(this,args);
		}, timeout);
	}
}

const debounceFetch =  debounce(fetchInput);

// input에 값을 입력했을때 => 검색
document.querySelector(".main_header .search_box .search_all_input").addEventListener("keyup", function(){
	headerInputDatas.searchText = this.value.trim();
	debounceFetch();
});
