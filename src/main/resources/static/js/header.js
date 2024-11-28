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



let datas = {
	"startDate": null,
	"endDate": null,
	"projectIdxArr": [],
	"managerIdxArr": [],
	"reporterIdx": "",
	"statusArr": []
}

function fetchInput() {
	console.log(datas);
	// fetch()를 사용하여 AJAX 요청
	let url = "/api/header/filter";
	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify(datas)
	})
		.then(response => response.json())  // JSON 형태로 응답 받기
		.then(issueList => {
			console.log(issueList);
			// div 교체
			document.querySelector(".input_recent_dynamic").classList.add("show");
			document.querySelector(".input_recent_box1").classList.remove("show");
			const recentBox = document.querySelector(".input_recent_dynamic");
			recentBox.innerHTML = "";

			let h5Element = document.createElement('h5');
			h5Element.innerHTML = `이슈 <span>${issueList.length}</span>`;
			recentBox.appendChild(h5Element);

			const divContainer = document.createElement("div");
			issueList.forEach(function(issue) {
				const aElement = document.createElement("a");
				aElement.classList.add("input_recent_content");
				aElement.setAttribute("href", "#");
				aElement.innerHTML = `
									 	<img src="/images/${issue.iconFilename}"></img>
										<div>
											<div>
												<span>${issue.key}</span>&nbsp;
												<span>${issue.name}</span>
											</div>
											<div>
												<span>${issue.projectName}</span>&nbsp;
												<span>${issue.elapsedTime}</span>
											</div>
										</div>
										`;
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
		document.querySelector(".last_update_box>ul>.active").classList.remove("active");
		this.classList.add("active");

		let today = new Date();
		let startDate = new Date();
		let endDate = new Date();

		switch (this.innerText) {
			case "Today":
				break;
			case "Yesterday":
				startDate.setDate(today.getDate() - 1);
				endDate.setDate(today.getDate() - 1);
				endDate.setHours(23, 59, 59, 999);
				break;
			case "Past 7 days":
				startDate.setDate(today.getDate() - 7);
				break;
			case "Past 30 days":
				startDate.setDate(today.getDate() - 30);
				break;
			case "Past Year":
				startDate.setDate(today.getDate() - 365);
				break;
			case "Any Time":
				document.querySelector(".input_recent_dynamic").classList.remove("show");
				document.querySelector(".input_recent_box1").classList.add("show");
				break;
		}
		startDate.setHours(0, 0, 0, 0);
		// 로컬 시간대 변경
		startDate = new Date(startDate.getTime() - startDate.getTimezoneOffset() * 60 * 1000)
		endDate = new Date(endDate.getTime() - endDate.getTimezoneOffset() * 60 * 1000)
		startDate = startDate.toISOString();
		endDate = endDate.toISOString();

		datas.startDate = startDate;
		datas.endDate = endDate;
		fetchInput();

	});

});


document.querySelectorAll(".filtering_box").forEach(function(box) {
	
	let projectIdxArr = [];
	let managerIdxArr = [];
	let reporterIdx = 0;
	let statusArr = [];
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
				if(e.target.checked){
					reporterIdx = 1;
				}else{
					reporterIdx = null;
				}
				
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
			datas.projectIdxArr = projectIdxArr;
			datas.managerIdxArr = managerIdxArr;
			datas.statusArr = statusArr;
			fetchInput();
		});
	});
});




