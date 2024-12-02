document.querySelector("body").addEventListener("click", function(e) {
    const menu = e.target.closest(".filter_menu");  // 클릭된 메뉴 찾기
    const menuWithShow = document.querySelector(".filter_issue_box.show");  // 열린 .filter_issue_box 찾기
    const menuBoxWithActive = document.querySelector(".filter_category_div.active");  // 활성화된 메뉴 찾기

    console.log(e.target.closest(".filter_menu"));
    console.log(menuWithShow);

    // 이미 열린 .filter_issue_box를 클릭한 경우 아무 작업도 하지 않음
    if (menuWithShow && menuWithShow.contains(e.target)) {
        return;
    }

    // 열린 메뉴가 있다면 그 메뉴의 show 클래스를 제거하고 스타일 초기화
    if (menuWithShow) {
        menuWithShow.classList.remove("show");  // 열린 메뉴 숨기기
        if (menuBoxWithActive) {
            menuBoxWithActive.classList.remove("active");  // 활성화된 메뉴 스타일 초기화
        }
    }

    // 클릭된 메뉴가 존재하면 해당 메뉴 열기
    if (menu !== null) {
        const filterBox = menu.querySelector(".filter_issue_box");  // 클릭한 메뉴의 filter_issue_box 찾기
        const menuBox = menu.querySelector(".filter_category_div");  // 해당 메뉴의 부모 .filter_category_div 찾기

        // 이미 열린 상태라면 메뉴 닫기
        if (filterBox.classList.contains("show")) {
            filterBox.classList.remove("show");  // 메뉴 닫기
            menuBox.classList.remove("active");  // 스타일 초기화
        } else {
            // 클릭된 메뉴 열기
            filterBox.classList.add("show");  // 해당 메뉴 열기
            menuBox.classList.add("active");  // 메뉴 스타일 활성화
        }
    }
});

let filterDatas = {
		"projecIdxArr" : [],
		"issueTypeArr" : [],
		"issueStatusArr" : []
	}
	
document.querySelectorAll(".filter_issue_box input[name='projectIdx']").forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.projecIdxArr.push(this.value);
		console.log(this.value);
		}else{
		filterDatas.projecIdxArr.splice(filterDatas.projecIdxArr.indexOf(this.value), 1);
		}
		fetchInput();
		});
})

document.querySelectorAll(".filter_issue_box input[name='issueTypeIdx']").forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.issueTypeArr.push(this.value); 
		console.log(this.value);
		}else{
		filterDatas.issueTypeArr.splice(filterDatas.issueTypeArr.indexOf(this.value), 1);
		}
		fetchInput();
		});
})

document.querySelectorAll(".filter_issue_box input[name='issueStatus']").forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.issueStatusArr.push(this.value); 
		console.log(this.value);
		}else{
		filterDatas.issueStatusArr.splice(filterDatas.issueStatusArr.indexOf(this.value), 1);
		}
		fetchInput();
		});
})


function fetchInput() {
	// fetch()를 사용하여 AJAX 요청
	let url = "/api/filter_issue_table/project_filter";

	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify({
			projectIdx: filterDatas.projecIdxArr, 
			issueTypes: filterDatas.issueTypeArr,
			issueStatus: filterDatas.issueStatusArr
		})
	})
		.then(response => response.json())  // JSON 형태로 응답 받기
		.then(issueListByProjectKey => {
			document.querySelector("tbody").innerHTML = ""
			
			issueListByProjectKey.forEach(function(item){
				document.querySelector("tbody").innerHTML +=
				`<tr class="filter_row">
					<td>
						<div style="display: flex;">
							<img width="16" height="16" src="/images/${item.issueIconFilename}">
						</div>
					</td>
					<td>
						<a>${item.issueKey}</a>
					</td>
					<td>
						<a>${item.issueName}</a>
					</td>
					<td>
						<div style="display: flex; align-items: center;">
							<div style="height: 24px;">
								<img width="24" height="24" src="/images/${item.issueManagerIconFilename}" alt="Manager Icon">
							</div>
							<div class="td_div">
							${item.issueManagerName}
							</div>
						</div>
					</td>
					<td>
						<div style="display: flex; align-items: center;">
							<div style="height: 24px;">
								<img width="24" height="24"
									src="/images/${item.issueReporterIconFilename}">
							</div>
							<div class="td_div">
							${item.issueReporterName}
							</div>
						</div>
					</td>
					<td>
						<div style="display: flex;">
							<div style="height: 16px;">
								<img src="/images/${item.issuePriorityIconFilename}"
									width="16px" height="16px">
							</div>
							<div class="td_div">
							${item.issuePriorityName}
							</div>
						</div>
					</td>
					<td>
						<button class="button_color ${item.issueStatus == 1 ? 'first' : ''}
						                      ${item.issueStatus == 2 ? 'second' : ''}">
							${item.issueStatusName}
							<svg role="presentation" width="12" height="12" viewBox="5 5 13 13">
								<path
									d="M8.292 10.293a1.009 1.009 0 0 0 0 1.419l2.939 2.965c.218.215.5.322.779.322s.556-.107.769-.322l2.93-2.955a1.01 1.01 0 0 0 0-1.419.987.987 0 0 0-1.406 0l-2.298 2.317-2.307-2.327a.99.99 0 0 0-1.406 0z"
									fill="currentColor" fill-rule="evenodd"></path>
							</svg>
						</button>
					</td>
					<td>

					</td>
					<td>
						<div>${item.createDate != null ? item.createDate : ""}</div>
					</td>
					<td>
						<div>${item.editDate != null ? item.editDate : ""}</div>
					</td>
					<td>
						<div>${item.deadlineDate != null ? item.deadlineDate : ""}</div>
					</td>
					<td>

					</td>
				</tr>`
			})
		})
		.catch(error => {
			console.error("Fetch error:", error);
		});
}

document.querySelector("body").addEventListener("click", function(e) {
	const account = e.target.closest(".filter_save_button");
	const modal = document.querySelector(".modal");
	const active = document.querySelector(".modal.active")
	const modalContent = document.querySelector(".modal_content")
	const cancleButton = modalContent.querySelector(".cancle_button")
	if (account) {
		modal.classList.add("active");
	}
	if(modal.classList.contains("active")){
		if(cancleButton && cancleButton.contains(e.target)){
			modal.classList.remove("active");
		}else if (!modalContent.contains(e.target) && !e.target.closest(".filter_save_button")) {
			modal.classList.remove("active");
		}
		
	}
		
});
