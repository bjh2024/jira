
document.querySelector("body").addEventListener("click", function(e) {
    const menu = e.target.closest(".filter_menu");  // 클릭된 메뉴 찾기
    const menuWithShow = document.querySelector(".filter_issue_box.show");  // 열린 .filter_issue_box 찾기
    const menuBoxWithActive = document.querySelector(".filter_category_div.active");  // 활성화된 메뉴 찾기

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

document.querySelectorAll("input").forEach(function(input){
	input.addEventListener("keydown",function(){
		if(event.key==="Enter"){
			event.preventDefault()
		}
	})
})

let filterDatas = {
			"projectIdxArr" : [],
			"issueTypeArr" : [],
			"issueStatusArr" : [],
			"issueManagersArr" : [],
			"issueReporterArr" : [],
			"issuePriorityArr" : [],
			"updateStartDate" : null,
			"updateLastDate" : null,
			"updateBeforeDate": null,
			"createStartDate" : null,
			"createLastDate" : null,
			"createBeforeDate": null,
			"doneStartDate" : null,
			"doneLastDate" : null,
			"doneBeforeDate": null,
			"searchBox" : null,
			"doneCheck" : null,
			"notDoneCheck" : null,
			"filterIdx" : null
		}
		
		let issueKey = "";
		document.querySelector(".issueListFilter")?.addEventListener("click", function(event) {
	    // 클릭된 요소가 .issue_box_choice인 경우에만 처리
	    if (event.target.closest(".issue_box_choice")) {
		        let item = event.target.closest(".issue_box_choice");  // 클릭한 .issue_box_choice 요소
	        issueKey = item.dataset.issueKey;  // 데이터 속성에서 issueKey 값을 가져옴/
		        // issueKey로 세부 정보를 가져오는 함수 호출
		        fetchIssueDetail();
		    }
		});
		


	
// 업데이트 날짜 필터 -------------------------------------------------------------------
	document.getElementById("radio_days")?.addEventListener("change", function() {
	   // '몇일 전'을 선택했을 때
	   if (this.checked) {
		document.querySelector(".update_date_box_2").classList.remove("show");
		document.querySelector(".update_date_box_1").classList.add("show");
		filterDatas.updateLastDate = null;
		filterDatas.updateStartDate = null;
	   }
	 });
	 document.getElementById("radio_range")?.addEventListener("change", function() {
	   // '사이'를 선택했을 때
	   if (this.checked) {
	     document.querySelector(".update_date_box_2").classList.add("show");
	     document.querySelector(".update_date_box_1").classList.remove("show");
		 filterDatas.updateBeforeDate = null;
		 fetchInputFilter();
		 fetchInputFilterIssue();
		 fetchIssueDetail();
	   }
	 });
	 document.getElementById("update_date_save_button")?.addEventListener("click", function() {
		let value = document.getElementById("update_before_date").value;
		if (!/^\d+$/.test(value) || value < 0 || value > 365) {
			          alert("유효한 숫자만 입력해주세요 (0 ~ 365).");
			          value = '';  // 잘못된 입력은 지워줍니다.
					  return;
			      }
	     let date = new Date();
	     // this.value는 입력받은 일수만큼 빼기
	     date.setDate(date.getDate() - value);
	     // date 객체를 그대로 사용
	     filterDatas.updateBeforeDate = date; // 필터에 Date 객체를 저장
	     fetchInputFilter(); // API 호출 또는 함수 실행
		 fetchInputFilterIssue();
	 });
	 document.getElementById("update_start_date")?.addEventListener("change", function(){
		filterDatas.updateStartDate = new Date(this.value);
		if(filterDatas.updateLastDate != null){
			document.getElementById("update_last_date").value = null;
			filterDatas.createLastDate = null;
		}
		fetchInputFilter();
		fetchInputFilterIssue();
	 })
	 document.getElementById("update_last_date")?.addEventListener("change", function(){
		if(filterDatas.updateStartDate == null){
			alert("시작날짜 먼저 입력해주세요!");
			this.value = null;
		}else if(filterDatas.updateStartDate > new Date(this.value)){
			alert("마지막 날짜는 시작날짜보다 최근이여야 합니다");
			this.value = null;
		}else if(filterDatas.updateStartDate < new Date(this.value)){
			filterDatas.updateLastDate = new Date(this.value);
		}
		fetchInputFilter();
		fetchInputFilterIssue();
		fetchIssueDetail();
	 })
	 document.getElementById("update_date_reset")?.addEventListener("click",function(){
		filterDatas.updateBeforeDate = null;
		filterDatas.updateLastDate = null;
		filterDatas.updateStartDate = null;
		document.querySelector(".update_date_box_1").classList.remove("show");
		document.querySelector(".update_date_box_2").classList.remove("show");
		document.getElementById("update_start_date").value = null;
		document.getElementById("update_last_date").value = null;
		fetchInputFilter();
		fetchInputFilterIssue();
		fetchIssueDetail();
	 })
// 업데이트 날짜 필터 끝 --------------------------------------------------------------------
// 생성 날짜 필터 시작 ------------------------------------------------------------------------
	document.getElementById("create_date")?.addEventListener("change", function() {
	   // '몇일 전'을 선택했을 때
	   if (this.checked) {
		document.querySelector(".create_date_box_2").classList.remove("show");
		document.querySelector(".create_date_box_1").classList.add("show");
	   }
	 });

	 document.getElementById("create_between")?.addEventListener("change", function() {
	   // '사이'를 선택했을 때
	   if (this.checked) {
	     document.querySelector(".create_date_box_2").classList.add("show");
	     document.querySelector(".create_date_box_1").classList.remove("show");
	   }
	 });
	 document.getElementById("create_date_save_button")?.addEventListener("click", function() {
	 		let value = document.getElementById("create_before_date").value;
	 		if (!/^\d+$/.test(value) || value < 0 || value > 365) {
	 			          alert("유효한 숫자만 입력해주세요 (0 ~ 365).");
	 			          value = '';  // 잘못된 입력은 지워줍니다.
	 					  return;
	 			      }
	 	     let date = new Date();
	 	     // this.value는 입력받은 일수만큼 빼기
	 	     date.setDate(date.getDate() - value);
	 	     // date 객체를 그대로 사용
	 	     filterDatas.createBeforeDate = date; // 필터에 Date 객체를 저장
	 	     fetchInputFilter(); // API 호출 또는 함수 실행
			 fetchInputFilterIssue();
	 	 });
	 	 document.getElementById("create_start_date")?.addEventListener("change", function(){
	 			filterDatas.createStartDate = new Date(this.value);
	 			if(filterDatas.createLastDate != null){
	 				document.getElementById("create_last_date").value = null;
	 				filterDatas.createLastDate = null;
	 			}
	 			fetchInputFilter();
				fetchInputFilterIssue();
	 	 })
	 	 document.getElementById("create_last_date")?.addEventListener("change", function(){
	 		if(filterDatas.createStartDate == null){
	 			alert("시작날짜 먼저 입력해주세요!");
	 			this.value = null;
	 		}else if(filterDatas.createStartDate > new Date(this.value)){
	 			alert("마지막 날짜는 시작날짜보다 최근이여야 합니다");
	 			this.value = null;
	 		}else if(filterDatas.createStartDate < new Date(this.value)){
	 			filterDatas.createLastDate = new Date(this.value);
	 		}
	 		fetchInputFilter();
			fetchIssueDetail();
			fetchInputFilterIssue();
	 	 })
	 	 document.getElementById("create_date_reset")?.addEventListener("click",function(){
	 		filterDatas.createBeforeDate = null;
			filterDatas.createLastDate = null;
			filterDatas.createStartDate = null;
	 		document.querySelector(".create_date_box_1").classList.remove("show");
	 		document.querySelector(".create_date_box_2").classList.remove("show");
			fetchInputFilter();
			fetchIssueDetail();
			fetchInputFilterIssue();
	 	 })
// 생성 날짜 필터 끝 =========================================================================================
// 해결 필터 시작 ------------------------------------------------------------------------
	document.getElementById("done_date")?.addEventListener("change", function() {
	   // '몇일 전'을 선택했을 때
	   if (this.checked) {
		document.querySelector(".done_date_box_2").classList.remove("show");
		document.querySelector(".done_date_box_1").classList.add("show");
		filterDatas.doneStartDate = null;
		filterDatas.doneLastDate = null;
	   }
	   fetchInputFilter(); // API 호출 또는 함수 실행
	   fetchInputFilterIssue();
	 });

	 document.getElementById("done_between")?.addEventListener("change", function() {
	   // '사이'를 선택했을 때
	   if (this.checked) {
	     document.querySelector(".done_date_box_2").classList.add("show");
	     document.querySelector(".done_date_box_1").classList.remove("show");
		 filterDatas.doneBeforeDate = null;
		 document.getElementById("done_before_date").value = "";
	   }
	     fetchInputFilter(); // API 호출 또는 함수 실행
	   	 fetchInputFilterIssue();
	 });
	 document.getElementById("done_date_save_button")?.addEventListener("click", function() {
	 		let value = document.getElementById("done_before_date").value;
	 		if (!/^\d+$/.test(value) || value < 0 || value > 365) {
	 			          alert("유효한 숫자만 입력해주세요 (0 ~ 365).");
	 			          value = '';  // 잘못된 입력은 지워줍니다.
	 					  return;
	 			      }
	 	     let date = new Date();
	 	     // this.value는 입력받은 일수만큼 빼기
	 	     date.setDate(date.getDate() - value);
	 	     // date 객체를 그대로 사용
	 	     filterDatas.doneBeforeDate = date; // 필터에 Date 객체를 저장
	 	     fetchInputFilter(); // API 호출 또는 함수 실행
			 fetchInputFilterIssue();
	 	 });
	 	 document.getElementById("done_start_date")?.addEventListener("change", function(){
	 			filterDatas.doneStartDate = new Date(this.value);
	 			if(filterDatas.doneLastDate != null){
	 				document.getElementById("done_last_date").value = null;
	 				filterDatas.doneLastDate = null;
	 			}
	 			fetchInputFilter();
				fetchInputFilterIssue();
	 	 })
	 	 document.getElementById("done_last_date")?.addEventListener("change", function(){
	 		if(filterDatas.doneStartDate == null){
	 			alert("시작날짜 먼저 입력해주세요!");
	 			this.value = null;
	 		}else if(filterDatas.doneStartDate > new Date(this.value)){
	 			alert("마지막 날짜는 시작날짜보다 최근이여야 합니다");
	 			this.value = null;
	 		}else if(filterDatas.doneStartDate < new Date(this.value)){
	 			filterDatas.doneLastDate = new Date(this.value);
	 		}
			fetchInputFilter(); // API 호출 또는 함수 실행
			fetchInputFilterIssue();	
	 	 })
	 	 document.querySelector("#done_date_reset")?.addEventListener("click",function(){
	 		filterDatas.doneBeforeDate = null;
			filterDatas.doneLastDate = null;
			filterDatas.doneStartDate = null;
	 		document.querySelector(".done_date_box_1").classList.remove("show");
	 		document.querySelector(".done_date_box_2").classList.remove("show");
			fetchInputFilter();
			fetchInputFilterIssue();	
	 	 })
// 해결 필터 끝 =========================================================================================
// 프로젝트 필터 시작 ========================================================================================
document.querySelectorAll(".filter_issue_box input[name='projectIdx']")?.forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.projectIdxArr.push(this.value);
		console.log(this.value);
		}else{
		filterDatas.projectIdxArr.splice(filterDatas.projectIdxArr.indexOf(this.value), 1);
		}
		fetchInputFilterIssue();
		fetchInputFilter();
		});
})
// 프로젝트 필터 끝 ========================================================================================
// 해결 필터 시작 ========================================================================================
document.querySelectorAll(".filter_issue_box input[name='donecheck1']")?.forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.doneCheck = this.value;
		}else{
		filterDatas.doneCheck = null;
		}
		fetchInputFilterIssue();
		fetchInputFilter();
		});
})
document.querySelectorAll(".filter_issue_box input[name='donecheck2']")?.forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.notDoneCheck = this.value;
		}else{
		filterDatas.notDoneCheck = null;
		}
		fetchInputFilterIssue();
		fetchInputFilter();
		});
})
// 해결 필터 끝 ========================================================================================
// 이슈 타입 필터 시작 ========================================================================================
document.querySelectorAll(".filter_issue_box input[name='issueTypeIdx']")?.forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.issueTypeArr.push(this.value); 
		}else{
		filterDatas.issueTypeArr.splice(filterDatas.issueTypeArr.indexOf(this.value), 1);
		}
		fetchInputFilter();
		fetchInputFilterIssue();
		});
})
// 이슈타입 필터 끝 ========================================================================================
// 이슈상태 시작 ========================================================================================
document.querySelectorAll(".filter_issue_box input[name='issueStatus']")?.forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.issueStatusArr.push(this.value); 
		}else{
		filterDatas.issueStatusArr.splice(filterDatas.issueStatusArr.indexOf(this.value), 1);
		}
		fetchInputFilter();
		fetchInputFilterIssue();
		});
})
// 이슈상태 끝 ========================================================================================
// 이슈 담당자 시작 ========================================================================================
document.querySelectorAll(".filter_issue_box input[name='issueManager']")?.forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.issueManagersArr.push(this.value); 
		}else{
		filterDatas.issueManagersArr.splice(filterDatas.issueManagersArr.indexOf(this.value), 1);
		}
		fetchInputFilter();
		fetchInputFilterIssue();
		});
})
// 이슈 담당자 끝 ========================================================================================
// 이슈 보고자 시작 ========================================================================================
document.querySelectorAll(".filter_issue_box input[name='issueReporter']")?.forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.issueReporterArr.push(this.value); 
		}else{
		filterDatas.issueReporterArr.splice(filterDatas.issueReporterArr.indexOf(this.value), 1);
		}
		fetchInputFilter();
		fetchInputFilterIssue();
		});
})
// 이슈 보고자 끝 ========================================================================================
// 이슈 우선순위 시작 ========================================================================================
document.querySelectorAll(".filter_issue_box input[name='issuePriority']")?.forEach(function(input){
	input.addEventListener("click", function(e) {
		if(this.checked){
		filterDatas.issuePriorityArr.push(this.value); 
		}else{
		filterDatas.issuePriorityArr.splice(filterDatas.issuePriorityArr.indexOf(this.value), 1);
		}
		fetchInputFilter();
		fetchInputFilterIssue();
		});
})
// 이슈 우선순위 끝 ========================================================================================
document.getElementById("search_box")?.addEventListener("input",function(item){
		filterDatas.searchBox = item.target.value;
		fetchInputFilter();
		fetchInputFilterIssue();
})
// 전체리셋버튼 ========================================================================================
document.querySelector("#all_reset")?.addEventListener("click",function(){
	filterDatas = {
		"projectIdxArr" : [],
		"issueTypeArr" : [],
		"issueStatusArr" : [],
		"issueManagersArr" : [],
		"issueReporterArr" : [],
		"issuePriorityArr" : [],
		"updateStartDate" : null,
		"updateLastDate" : null,
		"updateBeforeDate": null,
		"createStartDate" : null,
		"createLastDate" : null,
		"createBeforeDate": null,
		"doneStartDate" : null,
		"doneLastDate" : null,
		"doneBeforeDate": null,
		"searchBox" : null,
		"doneCheck" : null,
		"notDoneCheck" : null,
		"filterIdx": null
		}
		document.querySelector(".createDate").style.display = "none";
		document.querySelector(".done_check").style.display = "none";
		document.querySelector(".doneDate").style.display = "none";
		document.querySelector(".updateDate").style.display = "none";
		document.querySelector(".issuePriority").style.display = "none";
		document.querySelector(".issueReporter").style.display = "none";
		fetchInputFilter();
		fetchInputFilterIssue();
})

// 전체리셋버튼 끝 ========================================================================================
window.addEventListener("load", function() {
		
			document.querySelectorAll(".project_input_list").forEach(function(item) {
		       if (item.checked) {
			           filterDatas.projectIdxArr.push(item.value);  // 체크된 값 추가
			       }
		    })
			document.querySelectorAll(".issue_type_input_list").forEach(function(item){
				if(item.checked){
					filterDatas.issueTypeArr.push(item.value);
				}
			})
			document.querySelectorAll(".issue_status_input_list").forEach(function(item){
				if(item.checked){
					filterDatas.issueStatusArr.push(item.value);
				}
			})
			document.querySelectorAll(".issue_manager_input_list").forEach(function(item){
				if(item.checked){
					filterDatas.issueManagersArr.push(item.value);
				}
			})
			document.querySelectorAll(".filter_reporter_input_list").forEach(function(item){
				if(item.checked){
					filterDatas.issueReporterArr.push(item.value);
					document.querySelector(".issueReporter").style.display = "block";
				}
			})
			document.querySelectorAll(".done_input_list").forEach(function(item){
				if(item.checked){
					filterDatas.doneCheck = item.value;
					document.querySelector(".done_check").style.display = "block";
				}
			})
			document.querySelectorAll(".done_input_list2").forEach(function(item){
				if(item.checked){
					filterDatas.notDoneCheck = item.value;
					document.querySelector(".done_check").style.display = "block";
				}
			})
			document.querySelectorAll(".issue_priority_input_list").forEach(function(item){
				if(item.checked){
					filterDatas.issuePriorityArr.push(item.value);
					document.querySelector(".issuePriority").style.display = "block";
				}
			})
			document.querySelectorAll(".update_date_input_list").forEach(function(item){
				let value = document.getElementById("update_before_date").value;
				let date = new Date();
					if(item.checked && item.id === "radio_days"){
						date.setDate(date.getDate() - value);
					    filterDatas.updateBeforeDate = date;
						document.querySelector(".update_date_box_1").classList.add("show")
						document.querySelector(".update_date_box_2").classList.remove("show")
						document.querySelector(".updateDate").style.display = "block"
					}else if(item.checked && item.id === "radio_range"){
						filterDatas.updateStartDate = new Date(document.getElementById("update_start_date").value);
						filterDatas.updateLastDate = new Date(document.getElementById("update_last_date").value);
						document.querySelector(".update_date_box_2").classList.add("show")
						document.querySelector(".update_date_box_1").classList.remove("show")
						document.querySelector(".updateDate").style.display = "block"
					}
				})
			document.querySelectorAll(".create_date_input_list").forEach(function(item){
				let value = document.getElementById("create_before_date").value;
				let date = new Date();
					if(item.checked && item.id === "create_date"){
						date.setDate(date.getDate() - value);
					    filterDatas.updateBeforeDate = date;
						document.querySelector(".create_date_box_1").classList.add("show")
						document.querySelector(".create_date_box_2").classList.remove("show")
						document.querySelector(".createDate").style.display = "block"
					}else if(item.checked && item.id === "create_between"){
						filterDatas.updateStartDate = new Date(document.getElementById("create_start_date").value);
						filterDatas.updateLastDate = new Date(document.getElementById("creat_last_date").value);
						document.querySelector(".create_date_box_2").classList.add("show")
						document.querySelector(".create_date_box_1").classList.remove("show")
						document.querySelector(".createDate").style.display = "block"
					}
				})
			document.querySelectorAll(".done_date_input_list").forEach(function(item){
				let value = document.getElementById("done_before_date").value;
				let date = new Date();
					if(item.checked && item.id === "done_date"){
						date.setDate(date.getDate() - value);
					    filterDatas.updateBeforeDate = date;
						document.querySelector(".done_date_box_1").classList.add("show")
						document.querySelector(".done_date_box_2").classList.remove("show")
						document.querySelector(".doneDate").style.display = "block"
					}else if(item.checked && item.id === "done_between"){
						filterDatas.updateStartDate = new Date(document.getElementById("done_start_date").value);
						filterDatas.updateLastDate = new Date(document.getElementById("done_last_date").value);
						document.querySelector(".done_date_box_2").classList.add("show")
						document.querySelector(".done_date_box_1").classList.remove("show")
						document.querySelector(".doneDate").style.display = "block"
					}
				})
				fetchInputFilter();
				fetchInputFilterIssue();
				fetchIssueDetail();
		});
// ajex 데이터 보내기 ========================================================================================
function fetchInputFilter() {
	// fetch()를 사용하여 AJAX 요청
	let url = "/api/filter_issue_table/project_filter";

	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify({
			projectIdx: filterDatas.projectIdxArr, 
			issueTypes: filterDatas.issueTypeArr,
			issueStatus: filterDatas.issueStatusArr,
			issueManager: filterDatas.issueManagersArr,
			issueReporter: filterDatas.issueReporterArr,
			issuePriority: filterDatas.issuePriorityArr,
			updateStartDate: filterDatas.updateStartDate,
			updateLastDate: filterDatas.updateLastDate,
			updateBeforeDate: filterDatas.updateBeforeDate,
			createStartDate : filterDatas.createStartDate,
			createLastDate : filterDatas.createLastDate,
			createBeforeDate: filterDatas.createBeforeDate,
			doneStartDate : filterDatas.doneStartDate,
 			doneLastDate : filterDatas.doneLastDate,
 			doneBeforeDate: filterDatas.doneBeforeDate,
			searchContent : filterDatas.searchBox,
			doneCheck : filterDatas.doneCheck,
			notDoneCheck : filterDatas.notDoneCheck,
			filterIdx : filterDatas.filterIdx
		})
	})
		.then(response => response.json())  // JSON 형태로 응답 받기
		.then(issueListByProjectKey => {
			if(document.querySelector("tbody") == null) return;
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
								<img width="24" height="24" 
								src="/images/${item.issueManagerIconFilename}" alt="Manager Icon">
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
						<div> ${item.finishDate != null ? new Date(item.finishDate).toLocaleDateString('ko-KR',{ year: 'numeric', month: 'long', day: 'numeric' }):""}</div>
					</td>
					<td>
						<div> ${item.createDate != null ? new Date(item.createDate).toLocaleDateString('ko-KR',{ year: 'numeric', month: 'long', day: 'numeric' }):""}</div>
					</td>
					<td>
						<div> ${item.editDate != null ? new Date(item.editDate).toLocaleDateString('ko-KR',{ year: 'numeric', month: 'long', day: 'numeric' }) : ""}</div>
					</td>
					<td>
						<div> ${item.deadlineDate != null ? new Date(item.deadlineDate).toLocaleDateString('ko-KR',{ year: 'numeric', month: 'long', day: 'numeric' }) : ""}</div>
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
// ajex 데이터 보내기 ========================================================================================
function fetchInputFilterIssue() {
	// fetch()를 사용하여 AJAX 요청
	let url = "/api/filter_issue_table/project_filter";

	fetch(url, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json' // JSON 데이터를 전송
		},
		body: JSON.stringify({
			projectIdx: filterDatas.projectIdxArr, 
			issueTypes: filterDatas.issueTypeArr,
			issueStatus: filterDatas.issueStatusArr,
			issueManager: filterDatas.issueManagersArr,
			issueReporter: filterDatas.issueReporterArr,
			issuePriority: filterDatas.issuePriorityArr,
			updateStartDate: filterDatas.updateStartDate,
			updateLastDate: filterDatas.updateLastDate,
			updateBeforeDate: filterDatas.updateBeforeDate,
			createStartDate : filterDatas.createStartDate,
			createLastDate : filterDatas.createLastDate,
			createBeforeDate: filterDatas.createBeforeDate,
			doneStartDate : filterDatas.doneStartDate,
 			doneLastDate : filterDatas.doneLastDate,
 			doneBeforeDate: filterDatas.doneBeforeDate,
			searchContent : filterDatas.searchBox,
			doneCheck : filterDatas.doneCheck,
			notDoneCheck : filterDatas.notDoneCheck,
			filterIdx : filterDatas.filterIdx
		})
	})
		.then(response => response.json())  // JSON 형태로 응답 받기
		.then(issueList => {
			if(document.querySelector(".issueListFilter") == null) return;
			document.querySelector(".issueListFilter").innerHTML = ""
			
			issueList.forEach(function(item, index){
				if(index === 0){
				issueKey = item.issueKey;
				fetchIssueDetail();
				}
				document.querySelector(".issueListFilter").innerHTML += 
				`<div class="issue_box_choice" data-issue-key="${item.issueKey}">
						<div class="issue_title">
							<span>${item.issueName}</span>
						</div>
					<div class="issue_category">
						<div style="display: flex; align-items: center;">
							<img style="margin-right: 4px;" width="16" height="16"
							src="/images/${item.issueIconFilename}"
							alt="작업" class="_1bsb7vkz _4t3i7vkz _2hwx1b66 _2rkoyh40">
							<span>${item.issueKey}</span>
						</div>
							<img width="24" height="24"
								src="/images/${item.issueReporterIconFilename}"
								alt="프로필이미지" style="border-radius: 50%;">
						</div>
				</div>`
			})
		})
		.catch(error => {
			console.error("Fetch error:", error);
		});
}
// ajex 데이터 보내기 끝========================================================================================
    window.onload = function() {
        // 이미 리다이렉트가 수행되었는지 로컬 스토리지를 확인합니다.
        if (!localStorage.getItem("redirected")) {
            // 첫 번째 a 태그를 찾습니다.
            let firstLink = document.querySelector("a#default-link");

            // 첫 번째 링크의 href 값을 가져옵니다.
            let href = firstLink ? firstLink.getAttribute("href") : "";

            // href 값이 있다면 해당 URL로 리다이렉트합니다.
            if (href) {
                // 리다이렉트가 발생했음을 로컬 스토리지에 기록합니다.
                localStorage.setItem("redirected", "true");

                // 해당 URL로 리다이렉트
                window.location.href = href;
            }
        }
    };
	function fetchIssueDetail() {
		    // fetch()를 사용하여 AJAX 요청
		    let url = "/api/filter_issue_table/issue_detail"; 

		    fetch(url, {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/json' // JSON 데이터를 전송
		        },
		        body: JSON.stringify({
		            "issueKey": issueKey  // issueKey 값을 JSON으로 전달
		        })
		    })
		    .then(response => response.json())  // JSON 형태로 응답 받기
			.then(issueDetail => {
				if(issueDetail[0] == null) return;
			    // issueDetail의 첫 번째 항목에서 issueKey를 가져옵니다.
			    let issueKey = issueDetail[0].issueKey;

			    // 모든 .issue_middle_right 요소를 가져와서 확인합니다.
			    document.querySelectorAll(".issue_middle_right").forEach(function(item) {
			        // 각 div의 data-key 값을 가져옵니다.
			        let itemKey = item.getAttribute("data-key");

			        // itemKey가 issueKey와 일치하면 해당 div를 표시하고, 그렇지 않으면 숨깁니다.
			        if (itemKey === issueKey) {
			            item.style.display = "block";  // 조건에 맞는 div만 보이도록 설정
			        } else {
			            item.style.display = "none";   // 나머지 div는 숨깁니다.
			        }
			    });
			})
		    .catch(error => {
		        console.error("Fetch error:", error);  // 에러 처리
		    });
		}
		// 현재 URL에서 쿼리 문자열을 가져옴
	/*	let urlParams = new URLSearchParams(window.location.search);
		// 쿼리 문자열에서 'filter' 값 추출
		let filterValue = urlParams.get('filter');  // '21'
		console.log(filterValue);
		filterDatas.filterIdx = filterValue;*/
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



document.getElementById("issueReporter")?.addEventListener("change",function(){
	if(this.checked){
		document.querySelector(".issueReporter").style.display = "block";
	}else{
		document.querySelector(".issueReporter").style.display = "none";
	}
})
document.getElementById("issuePriority")?.addEventListener("change",function(){
	if(this.checked){
		document.querySelector(".issuePriority").style.display = "block";
	}else{
		document.querySelector(".issuePriority").style.display = "none";
	}
})
document.getElementById("updateDate")?.addEventListener("change",function(){
	if(this.checked){
		document.querySelector(".updateDate").style.display = "block";
	}else{
		document.querySelector(".updateDate").style.display = "none";
	}
})
document.getElementById("createDate")?.addEventListener("change",function(){
	if(this.checked){
		document.querySelector(".createDate").style.display = "block";
	}else{
		document.querySelector(".createDate").style.display = "none";
	}
})
document.getElementById("doneDate")?.addEventListener("change",function(){
	if(this.checked){
		document.querySelector(".doneDate").style.display = "block";
	}else{
		document.querySelector(".doneDate").style.display = "none";
	}
})
document.getElementById("doneCheck")?.addEventListener("change",function(){
	if(this.checked){
		document.querySelector(".done_check").style.display = "block";
	}else{
		document.querySelector(".done_check").style.display = "none";
	}
})

let filterName = null;
let explain = null;
let isCompleted = [];
let doneDateBefore = null;
let updateBefore = null;
let createDateBefore = null;
let jiraIdx = null;

document.querySelector(".save_button")?.addEventListener("click",async function(){
	filterName = document.querySelector(".hover_input")?.value;
	explain = document.querySelector(".textArea_1")?.value;
	updateBefore = document.getElementById("update_before_date")?.value;
	doneDateBefore = document.getElementById("done_before_date")?.value;
	createDateBefore = document.getElementById("create_before_date")?.value;
	if(document.querySelector(".done_input_list").checked){
		isCompleted.push(1);
	}
	if(document.querySelector(".done_input_list2").checked){
		isCompleted.push(0);
	}
	jiraIdx = this.dataset.jiraIdx;
	
	try{
		const res = await fetch("/api/filter_issue_table/filter_duplicate",{
			method: 'POST',
			headers:{
				'Content-Type': 'application/json' // JSON 데이터를 전송
	        },
			body: JSON.stringify({
				filterName : filterName
			})
		})
		const booleanResult = await res.json();
		if(!booleanResult){
			document.querySelector(".hover_input").style.border = "2px solid #C9372C";
			document.querySelector(".hover_input").style.borderRadius = "4px";
			document.querySelector(".fail_name").innerText = "중복된 이름이 존재합니다.";
			document.querySelector(".fail_name").style.color = "#C9372C";
			return;
		}else{
			
		}
	}catch(error){
		console.error(error);
	}
	fetchFitlerCreate();
})


function fetchFitlerCreate() {
	    // fetch()를 사용하여 AJAX 요청
    let url = "/api/filter_issue_table/filter_create"; 
    fetch(url, {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json' // JSON 데이터를 전송
	        },
	        body: JSON.stringify({
				projectIdx: filterDatas.projectIdxArr, 
				issueTypes: filterDatas.issueTypeArr,
				issueStatus: filterDatas.issueStatusArr,
				issueManager: filterDatas.issueManagersArr,
				issueReporter: filterDatas.issueReporterArr,
				issuePriority: filterDatas.issuePriorityArr,
				updateStartDate: filterDatas.updateStartDate,
				updateLastDate: filterDatas.updateLastDate,
				updateBeforeDate: filterDatas.updateBeforeDate,
				createStartDate : filterDatas.createStartDate,
				createLastDate : filterDatas.createLastDate,
				createBeforeDate: filterDatas.createBeforeDate,
				doneStartDate : filterDatas.doneStartDate,
	 			doneLastDate : filterDatas.doneLastDate,
	 			doneBeforeDate: filterDatas.doneBeforeDate,
				searchContent : filterDatas.searchBox,
				doneCheck : filterDatas.doneCheck,
				notDoneCheck : filterDatas.notDoneCheck,
				filterIdx : filterDatas.filterIdx,
				filterName : filterName,
				explain : explain,
				isCompleted : isCompleted,
				doneDateBefore : doneDateBefore,
				updateBefore : updateBefore,
				createDateBefore : createDateBefore,
				jiraIdx : jiraIdx,
				issueKey: issueKey
	        })
	    })
		.then(response => {
				        if (response.ok) {
							window.location.href = "./every_filter"
				        } else {
				            // 응답 상태가 성공 범위를 벗어나는 경우
				            throw new Error(`HTTP error!`);
				        }
			    }).catch(error => {
	        console.error("Fetch error:", error);  // 에러 처리
	    });
	}