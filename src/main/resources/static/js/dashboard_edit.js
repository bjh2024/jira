// 가젯 리스트 hidden
document.querySelector(".gadget_header .title .img_box").addEventListener("click", function() {
	const gadgetBox = document.querySelector(".dashboard_add_gadget");
	gadgetBox.style.width = '0px';
	gadgetBox.style.borderLeft = 'none';
	document.querySelector(".dashboard_container").style.gridTemplateColumns = '1fr 0';
});
// 가젯 리스트 show
document.querySelector(".gnb_btn2.gadget_add_btn").addEventListener("click", function() {
	const gadgetBox = document.querySelector(".dashboard_add_gadget");
	gadgetBox.style.width = '400px';
	gadgetBox.style.borderLeft = '3px solid #ddd';
	document.querySelector(".dashboard_container").style.gridTemplateColumns = '1fr 400px';

	const gadgetInput = document.querySelector(".gadget_header .input_box input");
	gadgetInput.focus();
})

// 프로젝트 idx 가져오기
async function projectIdxFetch(name) {
	const res = await fetch(`/api/project/idx?projectName=${name}`, { method: "get" });
	const idx = await res.json();
	return idx;
}

// 나에게 할당된 모든 이슈의 개수 가져오기
async function isseuTotalFetch(){
	const res = await fetch(`/api/issue/allot/count`, { method: "get" });
	const total = await res.json();
	return total;
}

// dashboardCol 문자로 바꾸기
function dashboardColChange(dashboardColIdx){
	switch(dashboardColIdx){
		case "1":
			return "담당자"
		case "2":
			return "레이블"
		case "3":
			return "보고자"
		case "4":
			return "상태"
		case "5":
			return "우선순위"
		case "6":
			return "이슈 유형"
	}
	return "값이 없습니다..."
}

// 구성 버튼 클릭시
function editChartChange(contentBox, chartTitle, name) {
	chartTitle = chartTitle.trim();
	name = name.trim();
	if (chartTitle === "파이 차트") {
		const colName = contentBox.querySelector(".dashboard_pie_chart_content .header h3").innerText;
		contentBox.querySelector(".dashboard_pie_chart").innerHTML = setPieChartContent(true, name, colName, 1);
	} else if (chartTitle === "나에게 할당됨") {

		const colNum = contentBox.querySelector(".dashboard_allot").getAttribute("row-num-data");
		contentBox.querySelector(".dashboard_allot").innerHTML = setAllotContent(name, colNum);
	} else if (chartTitle === "만듦 대비 해결됨 차트") {

		const unitPeriod = contentBox.querySelector(".dashboard_issue_complete").getAttribute("unitPeriod-data");
		const viewDate = contentBox.querySelector(".dashboard_issue_complete").getAttribute("viewdate-data");
		contentBox.querySelector(".dashboard_issue_complete").innerHTML = setIssueComplete(true, name, unitPeriod, viewDate);
	} else if (chartTitle === "최근에 만듦 차트") {

		const unitPeriod = contentBox.querySelector(".dashboard_issue_recent").getAttribute("unitPeriod-data");
		const viewDate = contentBox.querySelector(".dashboard_issue_recent").getAttribute("viewdate-data");
		contentBox.querySelector(".dashboard_issue_recent").innerHTML = setIssueRecent(true, name, unitPeriod, viewDate);
	} else if (chartTitle === "이슈 통계") {

		const dashboardColIdx = contentBox.querySelector(".dashboard_issue_statistics").getAttribute("dashboardColIdx-data");
		const rowNum = contentBox.querySelector(".dashboard_issue_statistics").getAttribute("row-num-data");
		name = name.split("(")[0].trim();
		contentBox.querySelector(".dashboard_issue_statistics").innerHTML = setIssueStatistics(true, name, dashboardColIdx, rowNum);
	} else if (chartTitle === "결과 필터") {
		return;
	}
}

// 대시보드 삭제 취소
document.querySelector("body").addEventListener("click", function(e) {
	const deleteBox = e.target.closest(".dashboard_delete_box");
	const cancleBtn = e.target.closest(".cancle_btn");
	const deleteBtn = e.target.closest(".delete_btn");
	
	if(deleteBox === null || cancleBtn !== null){
		document.querySelector(".dashboard_delete_modal.detail").classList.remove("show");
		return;
	}
	
	// 삭제 클릭시
	if(deleteBtn !== null){
		const idx = deleteBox.getAttribute("idx-data");
		function dashboardItemDeleteFetch(){
			let uri = "/api/dashboard/delete/";
			const type = deleteBox.getAttribute("type");
			switch(type){
				case "dashboard_pie_chart":
					uri += "pie_chart";
					break;
				case "dashboard_allot":
					uri += "allot";
					break;
				case "dashboard_issue_complete":
					uri += "issue_complete";
					break;
				case "dashboard_issue_recent":
					uri += "issue_recent";
					break;
				case "dashboard_issue_statistics":
					uri += "issue_statistics";
					break;
				case "dashboard_issue_filter":
					uri += "issue_filter";
					break;
			}
			fetch(uri, {method: "post", 
						headers: {"Content-Type" : "application/json"}, 
						body:JSON.stringify(idx)})
			.catch(err => {
				console.error(err);
			});
			location.reload(true);
		}
		dashboardItemDeleteFetch();
	}
	
});

// 대시보드 더보기 show
document.querySelector("body").addEventListener("click", function(e) {
	const moreBtn = e.target.closest(".img_box.dashboard_more");
	if (moreBtn !== null) {
		moreBtn.querySelector(".more_gadget_option").classList.toggle("show");
		// 대시보드 더보기 버튼 이벤트(구성, 복제, 삭제)
		const btn = e.target.closest(".more_gadget_option .btn_box button");
		if (btn !== null) {
			const btnText = btn.querySelector("span").innerText;
			const chartTitle = e.target.closest(".add_dashboard_content_header").querySelector("h2 span").innerText.split(":")[0];
			// 프로젝트 이름 or 사용자 이름
			const name = e.target.closest(".add_dashboard_content_header").querySelector("h2 span").innerText.split(":")[1];
			const contentBox = e.target.closest(".add_dashboard_content");
			switch (btnText) {
				case "구성":
					editChartChange(contentBox, chartTitle, name);
					break;
				case "복제":
					break;
				case "삭제":
					const deleteModal = document.querySelector(".dashboard_delete_modal.detail");
					const deleteBox = deleteModal.querySelector(".dashboard_delete_box");
					const deleteHeader = deleteModal.querySelector("h2");
					deleteHeader.innerHTML = `<img src="/images/alaret_icon.svg" width="16" height="16"/>
											 		   <span>${chartTitle} 가젯을 삭제하겠습니까?</span>`
				    deleteModal.classList.add("show");
					
					const dashboardItem = e.target.closest(".dashboard_item");
					const idx = dashboardItem.getAttribute("idx-data");
					deleteBox.setAttribute("idx-data", idx);
					deleteBox.setAttribute("type", dashboardItem.className.split(" ")[0]);
					break;
			}
		}
	} else {
		document.querySelector(".more_gadget_option.show")?.classList.remove("show");
	}
});

// 파이 차트(구성 클릭시 파이 차트 설정 보기)
function setPieChartContent(isChange = false, projectName = '', colName = '') {
	const pieChartContent = `
						<div class="add_dashboard_content_header">
							<h2>
								<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
								<span>파이 차트: ${projectName}</span>
							</h2>
							<div>
								<div class="img_box">
									<img src="/images/minimize_icon.svg" />
								</div>
								<div class="img_box">
									<img src="/images/maximize_icon.svg" />
								</div>
								<div class="img_box">
									<img src="/images/refresh_icon.svg" />
								</div>
							</div>
						</div>
						<div class="add_dashboard_content_main piechart_content_main dashboard_item_content">
							<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
							<div class="main_group box1">
								<label for="project_name">저장된 프로젝트<span class="not_null_check">*</span></label>
								<div class="project_name_box"> ${isChange ? `${projectName}` : '선택된 프로젝트 없음'}</div>
								<input type="text" id="project_name" placeholder="검색" autocomplete="off"/>
								<div class="name_search_box">
								</div>
								<p>그래프에 기준으로 사용할 저장된 프로젝트입니다.</p>
								<div>
									<button>고급 검색</button>
								</div>
								<div class="alert_box">선택한 프로젝트 없음</div>
							</div>
							<div class="main_group box2">
								<label for="pie_statistic">통계 유형<span class="not_null_check">*</span></label>
								<select name="statistic" id="pie_statistic">
									<option value=1 ${colName === '담당자' ? 'selected' : ''}>담당자</option>
									<option value=2 ${colName === '레이블' ? 'selected' : ''}>레이블</option>
									<option value=3 ${colName === '보고자' ? 'selected' : ''}>보고자</option>
									<option value=4 ${colName === '상태' ? 'selected' : ''}>상태</option>
									<option value=5 ${colName === '우선순위' ? 'selected' : ''}>우선순위</option>
									<option value=6 ${colName === '이슈 유형' ? 'selected' : ''}>이슈 유형</option>
								</select>
								<p>이 필터를 표시할 통계의 유형을 선택.</p>
							</div>
							<div class="save_btn">
								<button>저장</button>
							</div>
						</div>`;
	return pieChartContent;
}

// 저장 클릭시 파이 차트 저장
async function pieChartSave(pieChart) {
	const idx = pieChart.getAttribute("idx-data");
	const type = pieChart.querySelector("#pie_statistic").value
	pieChart.setAttribute("dashboardColIdx-data", type);
	const projectName = pieChart.querySelector(".project_name_box").innerText;
	const projectIdx = await projectIdxFetch(projectName);
	pieChart.setAttribute("projectIdx-data", projectIdx);
	pieChart.innerHTML = `<div class="add_dashboard_content_header">
								<h2>
									<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
									<span>파이 차트: ${projectName}</span>
								</h2>
								<div>
									<div class="img_box">
										<img src="/images/minimize_icon.svg">
									</div>
									<div class="img_box">
										<img src="/images/maximize_icon.svg">
									</div>
									<div class="img_box">
										<img src="/images/refresh_icon.svg">
									</div>
									<div class="img_box dashboard_more">
										<img src="/images/three_dots_row_icon.svg" width="16" height="16" />
										<div class="more_gadget_option">
											<div class="color_box">
												<p>강조 색상</p>
												<div class="color_content">
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
												</div>
											</div>
											<div class="btn_box">
												<button>
													<span>구성</span>
												</button>
												<button>
													<span>복제</span>
												</button>
												<button>
													<span>삭제</span>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="dynamic_box dashboard_pie_chart_container dashboard_item_content">
								<div class="doughnut_chart_ratio"></div>
								<div class="dashboard_pie_chart_box">
									<div>
										<div class="doughnut_chart_ratio"></div>
										<canvas id="pieChart-${idx}"></canvas>
									</div>
								</div>
								<div class="dashboard_pie_chart_content">
									<div class="header">
										<h3>${dashboardColChange(type)}</h3>
										<div class="issue_total"></div>
									</div>
									<div class="main">
									</div>
								</div>
							</div>`
	drawPieChart(pieChart);
}

// 나에게 할당됨(구성 클릭시 나에게 할당됨 표 설정 보기)
function setAllotContent(name = '', colNum = '10') {
	const allotContent = `<div class="add_dashboard_content_header">
								<h2>
									<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
									<span>나에게 할당됨: ${name}</span>
								</h2>
								<div>
									<div class="img_box">
										<img src="/images/minimize_icon.svg" />
									</div>
									<div class="img_box">
										<img src="/images/maximize_icon.svg" />
									</div>
									<div class="img_box">
										<img src="/images/refresh_icon.svg" />
									</div>
								</div>
							</div>
							<div class="add_dashboard_content_main my_responsibility_issue_main dashboard_item_content">
								<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
								<div class="main_group box1">
									<label>결과의 수<span class="not_null_check">*</span></label>
									<input type="text" value="${colNum}" />
									<p>표시할 결과의 수(최대 50개)</p>
								</div>
								<div class="main_group box2">
									<label>표시할 열<span class="not_null_check">*</span></label>
									<form>
										<table>
											<colgroup>
												<col width="8%" />
												<col width="73%" />
												<col width="19%" />
											</colgroup>
											<tr>
												<td>
												</td>
												<td>이슈 유형</td>
												<td>
													<button>
														<img src="/images/trash_icon.svg" / width="16" height="16">
													</button>
												</td>
											</tr>
											<tr>
												<td>
												</td>
												<td>키</td>
												<td>
													<button>
														<img src="/images/trash_icon.svg" / width="16" height="16">
													</button>
												</td>
											</tr>
											<tr>
												<td>
												</td>
												<td>요약</td>
												<td>
													<button>
														<img src="/images/trash_icon.svg" / width="16" height="16">
													</button>
												</td>
											</tr>
											<tr>
												<td>
												</td>
												<td>우선순위</td>
												<td>
													<button>
														<img src="/images/trash_icon.svg" / width="16" height="16">
													</button>
												</td>
											</tr>
										</table>
									</form>
								</div>
								<div class="main_group box3">
									<label>표시할 열 추가</label>
									<select>
										<option value=1>담당자</option>
										<option value=2>레이블</option>
										<option value=3>보고자</option>
										<option value=4>상태</option>
										<option value=5>우선순위</option>
										<option value=6>이슈 유형</option>
									</select>
									<p>필드를 선택하여 위의 목록에 해당 필드를 추가하십시오.</p>
								</div>
								<div class="save_btn">
									<button>저장</button>
								</div>
							</div>`
	return allotContent;
}

// 저장 클릭시 나에게 할당됨 표시
async function allotSave(allot) {
	const name = allot.querySelector(".add_dashboard_content_header h2 span").innerText.split(":")[1];
	const rowNum = allot.querySelector(".add_dashboard_content_main .main_group.box1 input").value;
	allot.setAttribute("row-num-data", rowNum);
	allot.setAttribute("issue-total", await isseuTotalFetch());
	allot.innerHTML = `<div class="add_dashboard_content_header">
								<h2>
									<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
									<span>나에게 할당됨: ${name}</span>
								</h2>
								<div>
									<div class="img_box">
										<img src="/images/minimize_icon.svg">
									</div>
									<div class="img_box">
										<img src="/images/maximize_icon.svg">
									</div>
									<div class="img_box">
										<img src="/images/refresh_icon.svg">
									</div>
									<div class="img_box dashboard_more">
										<img src="/images/three_dots_row_icon.svg" width="16" height="16" />
										<div class="more_gadget_option">
											<div class="color_box">
												<p>강조 색상</p>
												<div class="color_content">
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
												</div>
											</div>
											<div class="btn_box">
												<button>
													<span>구성</span>
												</button>
												<button>
													<span>복제</span>
												</button>
												<button>
													<span>삭제</span>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="dynamic_box dashboard_item_content">
								<table style="table-layout: fixed;">
									<colgroup>
										<col style="width: 32px;">
										<col style="width: 62px;">
										<col style="width: 65%;">
										<col style="width: 20%;">
									</colgroup>
									<thead>
										<tr>
											<th>화</th>
											<th>키</th>
											<th>요약</th>
											<th>p</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								<div class="paging_box"></div>
							</div>`
	drawAllot(allot);
}
// 만듦 대비 해결됨 차트(구성 클릭시 만듦 대비 해결됨 차트 설정 보기) 
function setIssueComplete(isChange = false, projectName = '', unitPeriod = '매일', viewDate = '30') {
	const issueComplete = `<div class="add_dashboard_content_header">
							<h2>
								<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
								<span>만듦 대비 해결됨 차트: ${projectName}</span>
							</h2>
							<div>
								<div class="img_box">
									<img src="/images/minimize_icon.svg" />
								</div>
								<div class="img_box">
									<img src="/images/maximize_icon.svg" />
								</div>
								<div class="img_box">
									<img src="/images/refresh_icon.svg" />
								</div>
							</div>
						</div>
						<div class="add_dashboard_content_main dashboard_item_content">
							<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
							<div class="main_group box1">
								<label for="project_name">저장된 프로젝트<span class="not_null_check">*</span></label>
								<div class="project_name_box">${isChange ? `${projectName}` : '선택된 프로젝트 없음'}</div>
								<input type="text" id="project_name" placeholder="검색" autocomplete="off"/>
								<div class="name_search_box">
								</div>
								<p>그래프에 기준으로 사용할 저장된 프로젝트입니다.</p>
								<div>
									<button>고급 검색</button>
								</div>
								<div class="alert_box">선택한 프로젝트 없음</div>
							</div>
							<div class="main_group box2">
								<label for="complete_unit_period">기간<span class="not_null_check">*</span></label>
								<select name="statistic" id="complete_unit_period">
									<option value="매시간" ${unitPeriod === "매시간" ? "selected" : ""}>매시간</option>
									<option value="매일"  ${unitPeriod === "매일" ? "selected" : ""}>매일</option>
									<option value="매주"  ${unitPeriod === "매주" ? "selected" : ""}>매주</option>
									<option value="매월"  ${unitPeriod === "매월" ? "selected" : ""}>매월</option>
									<option value="매년"  ${unitPeriod === "매년" ? "selected" : ""}>매년</option>
								</select>
								<p>이 필터를 표시할 통계의 유형을 선택.</p>
							</div>
							<div class="main_group box3">
								<label for="prev_date">이전 날짜<span class="not_null_check">*</span></label>
								<input type="text" id="prev_date" value="${viewDate}" />
								<p>선택한 기간내에서 자료를 수집할 과거 일 수</p>
							</div>
							<div class="save_btn">
								<button>저장</button>
							</div>
						</div>`
	return issueComplete;
}
// 저장 클릭시 만듦 대비 해결됨 차트 표시
async function issueCompleteSave(issueComplete) {
	const unitPeriod = issueComplete.querySelector(".main_group.box2 #complete_unit_period").value;
	issueComplete.setAttribute("unitPeriod-data", unitPeriod);
	const viewDate = issueComplete.querySelector(".main_group.box3 #prev_date").value;
	issueComplete.setAttribute("viewDate-data", viewDate);
	const idx = issueComplete.getAttribute("idx-data");
	const projectName = issueComplete.querySelector(".project_name_box").innerText;
	const projectIdx = await projectIdxFetch(projectName);
	issueComplete.setAttribute("projectIdx-data", projectIdx);
	issueComplete.innerHTML = `	<div class="add_dashboard_content_header">
									<h2>
										<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
										<span>만듦 대비 해결됨 차트: ${projectName}</span>
									</h2>
									<div>
										<div class="img_box">
											<img src="/images/minimize_icon.svg">
										</div>
										<div class="img_box">
											<img src="/images/maximize_icon.svg">
										</div>
										<div class="img_box">
											<img src="/images/refresh_icon.svg">
										</div>
										<div class="img_box dashboard_more">
											<img src="/images/three_dots_row_icon.svg" width="16" height="16" />
											<div class="more_gadget_option">
												<div class="color_box">
													<p>강조 색상</p>
													<div class="color_content">
														<div></div>
														<div></div>
														<div></div>
														<div></div>
														<div></div>
														<div></div>
														<div></div>
														<div></div>
													</div>
												</div>
												<div class="btn_box">
													<button>
														<span>구성</span>
													</button>
													<button>
														<span>복제</span>
													</button>
													<button>
														<span>삭제</span>
													</button>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="dynamic_box dashboard_issue_complete_container dashboard_item_content">
									<div>
										<canvas id="issueCompleteChart-${idx}"
											style="width: 400px; height: 400px;"></canvas>
									</div>
									<div class="dashboard_issue_complete_content">
										<h3>
											<b>최근 <span>${viewDate}</span>일간의 이슈</b>
											(단위:<span">${unitPeriod}</span>)
										</h3>
										<div class="main">
										</div>
									</div>
								</div>`
	drawIssueComplete(issueComplete);
}
// 최근에 만듦 차트(구성 클릭시 최근에 만듦 차트 설정 보기)
function setIssueRecent(isChange = false, projectName = '', unitPeriod = '매일', viewDate = 30) {
	const issueRecent = `<div class="add_dashboard_content_header">
							<h2>
								<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
								<span>최근에 만듦 차트: ${projectName}</span>
							</h2>
							<div>
								<div class="img_box">
									<img src="/images/minimize_icon.svg" />
								</div>
								<div class="img_box">
									<img src="/images/maximize_icon.svg" />
								</div>
								<div class="img_box">
									<img src="/images/refresh_icon.svg" />
								</div>
							</div>
						</div>
						<div class="add_dashboard_content_main dashboard_item_content dashboard_item_content">
							<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
							<div class="main_group box1">
								<label for="project_name">저장된 프로젝트<span class="not_null_check">*</span></label>
								<div class="project_name_box">${isChange ? `${projectName}` : '선택된 프로젝트 없음'}</div>
								<input type="text" id="project_name" placeholder="검색" autocomplete="off"/>
								<div class="name_search_box">
								</div>
								<p>그래프에 기준으로 사용할 저장된 프로젝트입니다.</p>
								<div>
									<button>고급 검색</button>
								</div>
								<div class="alert_box">선택한 프로젝트 없음</div>
							</div>
							<div class="main_group box2">
								<label for="recent_unit_period">기간<span class="not_null_check">*</span></label>
								<select name="statistic" id="recent_unit_period">
									<option value="매시간" ${unitPeriod === "매시간" ? "selected" : ""}>매시간</option>
									<option value="매일"  ${unitPeriod === "매일" ? "selected" : ""}>매일</option>
									<option value="매주"  ${unitPeriod === "매주" ? "selected" : ""}>매주</option>
									<option value="매월"  ${unitPeriod === "매월" ? "selected" : ""}>매월</option>
									<option value="매년"  ${unitPeriod === "매년" ? "selected" : ""}>매년</option>
								</select>
								<p>기간의 길이가 그래프에 표시됩니다.</p>
							</div>
							<div class="main_group box3">
								<label for="prev_date">이전 날짜<span class="not_null_check">*</span></label>
								<input type="text" id="prev_date" value="${viewDate}" />
								<p>그래프에 표시할 일수(오늘 포함)</p>
							</div>
							<div class="save_btn">
								<button>저장</button>
							</div>
						</div>`
	return issueRecent;
}
// 저장 클릭시 최근에 만듦 차트 표시
async function issueRecentSave(issueRecent) {
	const unitPeriod = issueRecent.querySelector(".main_group.box2 #recent_unit_period").value;
	issueRecent.setAttribute("unitPeriod-data", unitPeriod);
	const viewDate = issueRecent.querySelector(".main_group.box3 #prev_date").value;
	issueRecent.setAttribute("viewDate-data", viewDate);
	const idx = issueRecent.getAttribute("idx-data");
	const projectName = issueRecent.querySelector(".project_name_box").innerText;
	const projectIdx = await projectIdxFetch(projectName);
	issueRecent.setAttribute("projectIdx-data", projectIdx);
	issueRecent.innerHTML = `<div class="add_dashboard_content_header">
								<h2>
									<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
									<span>최근에 만듦 차트 : ${projectName}</span>
								</h2>
								<div>
									<div class="img_box">
										<img src="/images/minimize_icon.svg">
									</div>
									<div class="img_box">
										<img src="/images/maximize_icon.svg">
									</div>
									<div class="img_box">
										<img src="/images/refresh_icon.svg">
									</div>
									<div class="img_box dashboard_more">
										<img src="/images/three_dots_row_icon.svg" width="16" height="16" />
										<div class="more_gadget_option">
											<div class="color_box">
												<p>강조 색상</p>
												<div class="color_content">
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
													<div></div>
												</div>
											</div>
											<div class="btn_box">
												<button>
													<span>구성</span>
												</button>
												<button>
													<span>복제</span>
												</button>
												<button>
													<span>삭제</span>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="dynamic_box dashboard_issue_recent_container dashboard_item_content">
								<div>
									<canvas id="issueRecentChart-${idx}" style="width: 400px; height: 400px;">
									</canvas>
								</div>
								<div class="dashboard_issue_recent_content">
									<h3></h3>
									<p>
										기간: 최근 <b>${viewDate}</b>&nbsp;일
										(<b>${unitPeriod}</b>&nbsp;별)
									</p>
								</div>
							</div>`
	drawIssueRecent(issueRecent);
}
// 이슈 통계(구성 클릭시 이슈 통계 설정 보기)
function setIssueStatistics(isChange = false, projectName = '', type = '담당자', viewNum = '10') {
	const issueStatistics = `<div class="add_dashboard_content_header">
								<h2>
									<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
									<span>이슈 통계 :${projectName}</span>
								</h2>
								<div>
									<div class="img_box">
										<img src="/images/minimize_icon.svg" />
									</div>
									<div class="img_box">
										<img src="/images/maximize_icon.svg" />
									</div>
									<div class="img_box">
										<img src="/images/refresh_icon.svg" />
									</div>
								</div>
							</div>
							<div class="add_dashboard_content_main dashboard_item_content">
								<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
								<div class="main_group box1">
									<label for="project_name">저장된 프로젝트<span class="not_null_check">*</span></label>
									<div class="project_name_box">${isChange ? `${projectName}` : '선택된 프로젝트 없음'}</div>
									<input type="text" id="project_name" placeholder="검색" autocomplete="off"/>
									<div class="name_search_box">
									</div>
									<p>그래프에 기준으로 사용할 저장된 프로젝트입니다.</p>
									<div>
										<button>고급 검색</button>
									</div>
									<div class="alert_box">선택한 프로젝트 없음</div>
								</div>
								<div class="main_group box2">
									<label for="statistic">통계 유형<span class="not_null_check">*</span></label>
									<select name="statistic" id="statistic">
										<option value=1 ${type === "담당자" ? "selected" : ""}>담당자</option>
										<option value=2 ${type === "레이블" ? "selected" : ""}>레이블</option>
										<option value=3 ${type === "보고자" ? "selected" : ""}>보고자</option>
										<option value=4 ${type === "상태" ? "selected" : ""}>상태</option>
										<option value=5 ${type === "우선순위" ? "selected" : ""}>우선순위</option>
										<option value=6 ${type === "이슈 유형" ? "selected" : ""}>이슈 유형</option>
									</select>
									<p>이 필터를 표시할 통계의 유형을 선택.</p>
								</div>
								<div class="main_group box3">
									<label for="view_num">결과의 수<span class="not_null_check">*</span></label>
									<input type="text" id="view_num" value="${viewNum}" />
									<p>표시할 결과의 수(최대 50개)</p>
								</div>
								<div class="save_btn">
									<button>저장</button>
								</div>
							</div>`
	return issueStatistics;
}
// 저장 클릭시 이슈 통계 표시
async function issueStatisticsSave(issueStatistics) {
	const type = issueStatistics.querySelector(".add_dashboard_content_main .main_group.box2 #statistic").value;
	issueStatistics.setAttribute("dashboardColIdx-data", type);
	const rowNum = issueStatistics.querySelector(".add_dashboard_content_main .main_group.box3 input").value;
	issueStatistics.setAttribute("row-num-data", rowNum);
	const projectName = issueStatistics.querySelector(".project_name_box").innerText;
	const projectIdx = await projectIdxFetch(projectName);
	issueStatistics.setAttribute("projectidx-data", projectIdx);
	issueStatistics.innerHTML = `
								<div class="add_dashboard_content_header">
									<h2>
										<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
										<span>이슈 통계: ${projectName} (${dashboardColChange(type)})</span>
									</h2>
									<div>
										<div class="img_box">
											<img src="/images/minimize_icon.svg">
										</div>
										<div class="img_box">
											<img src="/images/maximize_icon.svg">
										</div>
										<div class="img_box dashboard_more">
											<img src="/images/three_dots_row_icon.svg" width="16" height="16" />
											<div class="more_gadget_option">
												<div class="color_box">
													<p>강조 색상</p>
													<div class="color_content">
														<div></div>
														<div></div>
														<div></div>
														<div></div>
														<div></div>
														<div></div>
														<div></div>
														<div></div>
													</div>
												</div>
												<div class="btn_box">
													<button>
														<span>구성</span>
													</button>
													<button>
														<span>복제</span>
													</button>
													<button>
														<span>삭제</span>
													</button>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="dynamic_box dashboard_item_content">
									<table style="table-layout: fixed;">
										<colgroup>
											<col style="width: 100px;">
											<col style="width: 32px;">
											<col>
											<col style="width: 32px;">
										</colgroup>
										<thead>
											<tr>
												<th>${type}</th>
												<th>개수</th>
												<th>퍼센트</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>`
	drawIssueStatistics(issueStatistics);
}
// 디바운스 함수
function debounce(func, timeout = 300) {
  let timer;
  return (...args) => {
    clearTimeout(timer);  // 기존 타이머를 취소
    timer = setTimeout(() => {
      func.apply(this, args);  // 최종 실행할 함수
    }, timeout);  // 설정된 timeout 후에만 실행
  };
}
function projectListFetch(searchText) {
	let projectName = searchText.value;
	const uri = `/api/project/search?searchName=${projectName}`
	fetch(uri, { method: "get" })
		.then(res => res.json())
		.then(projectList => {
			const box = searchText.nextElementSibling;
			box.innerHTML = ``;
			projectList.forEach(function(project) {
				box.innerHTML += `<div>
									 <img src='/images/${project.iconFilename}' width=16 height=16/>
									 <div><span class="projectName">${project.name}</span><span>(${project.key})</span></div>
								  </div>`;
			});
		})
		.catch(err => {
			console.error(err);
		})
}
const debouncedFetch = debounce((projectNameInput) => projectListFetch(projectNameInput));

document.querySelector(".dashboard_content_container").addEventListener("keyup", function(e) {
	const projectNameInput = e.target.closest("input#project_name");
	if (projectNameInput === null) return;
	debouncedFetch(projectNameInput);
});

// dashboardItem alert_box
function dashboardItemAlertBox(dashboardItem){
	const projectNameInput = dashboardItem.querySelector("#project_name");
	const projectName = dashboardItem.querySelector(".project_name_box").innerText;
	if(projectName === "선택된 프로젝트 없음"){
		dashboardItem.querySelector(".alert_box").classList.add("show");
		projectNameInput.focus();
		return false;
	}
	dashboardItem.querySelector(".alert_box").classList.remove("show");
	return true;
}

// 저장 버튼 클릭시
document.querySelector(".dashboard_content_container").addEventListener("click", async function(e) {
	const saveBtn = e.target.closest(".save_btn");
	if (saveBtn === null) return;
	// update OR create 판별
	let uri = "/api/dashboard/update/";
	let requestData = {};
	// 파이 차트
	let pieChart = e.target?.closest(".dashboard_pie_chart");
	if (pieChart !== null) {
		if(!dashboardItemAlertBox(pieChart)) return;
		
		await pieChartSave(pieChart);
		uri += "pie_chart";
		const idx = pieChart.getAttribute("idx-data");
		const projectIdx = pieChart.getAttribute("projectIdx-data");
		const dashboardColIdx = pieChart.getAttribute("dashboardColIdx-data");
		requestData = {
			"idx" : idx,
			"projectIdx" : projectIdx,
			"dashboardColIdx" : dashboardColIdx
		}
	}
	// 나에게 할당됨
	let allot = e.target?.closest(".dashboard_allot");
	if (allot !== null) {
		await allotSave(allot);
		uri += "allot";
		const idx = allot.getAttribute("idx-data");
		const rowNum = allot.getAttribute("row-num-data");
		requestData = {
			"idx" : idx,
			"rowNum" : rowNum
		}
	}
	// 만듦 대비 해결됨 차트
	let issueComplete = e.target?.closest(".dashboard_issue_complete");
	if (issueComplete !== null) {
		if(!dashboardItemAlertBox(issueComplete)) return;
		
		await issueCompleteSave(issueComplete);
		uri += "issue_complete";
		const idx = issueComplete.getAttribute("idx-data");
		const projectIdx = issueComplete.getAttribute("projectIdx-data");
		const viewDate = issueComplete.getAttribute("viewDate-data");
		const unitPeriod = issueComplete.getAttribute("unitPeriod-data");
		requestData = {
			"idx" : idx,
			"projectIdx" : projectIdx,
			"viewDate" : viewDate,
			"unitPeriod" : unitPeriod
		}
		
	}
	// 최근에 만듦 차트
	let issueRecent = e.target?.closest(".dashboard_issue_recent");
	if (issueRecent !== null) {
		if(!dashboardItemAlertBox(issueRecent)) return;
		
		await issueRecentSave(issueRecent);
		uri += "issue_recent";
		const idx = issueRecent.getAttribute("idx-data");
		const projectIdx = issueRecent.getAttribute("projectIdx-data");
		const viewDate = issueRecent.getAttribute("viewDate-data");
		const unitPeriod = issueRecent.getAttribute("unitPeriod-data");
		requestData = {
			"idx" : idx,
			"projectIdx" : projectIdx,
			"viewDate" : viewDate,
			"unitPeriod" : unitPeriod
		}
	}
	// 이슈 통계
	let issueStatistics = e.target?.closest(".dashboard_issue_statistics");
	if (issueStatistics !== null) {
		if(!dashboardItemAlertBox(issueStatistics)) return;
		
		await issueStatisticsSave(issueStatistics);
		uri += "issue_statistics";
		const idx = issueStatistics.getAttribute("idx-data");
		const projectIdx = issueStatistics.getAttribute("projectIdx-data");
		const dashboardColIdx = issueStatistics.getAttribute("dashboardColIdx-data");
		const rowNum = issueStatistics.getAttribute("row-num-data");
		requestData = {
			"idx" : idx,
			"projectIdx" : projectIdx,
			"dashboardColIdx" : dashboardColIdx,
			"rowNum" : rowNum
		}
	}

	fetch(uri, {
		method: "post",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify(requestData)
	})
	.catch(err => {
		console.log(err);
	})
});

document.querySelector(".dashboard_content_container").addEventListener("mousedown", function(e) {
	const projectSelectBtn = e.target.closest(".name_search_box > div");
	// projectSearch한 project btn 클릭시
	if (projectSelectBtn === null) return;
	const box = e.target.closest(".main_group");
	const input = box.querySelector("#project_name");
	const projectName = box.querySelector(".project_name_box");
	input.value = "";
	projectName.innerText = projectSelectBtn.querySelector("span.projectName").innerText;
});

const issueFilter = `<div class="add_dashboard_content_header">
						<h2>
							<img src="/images/switch_position_icon.svg" width="32" height="32" class="item_switch_btn" draggable="false">
							<span>결과 필터</span>
						</h2>
						<div>
							<div class="img_box">
								<img src="/images/minimize_icon.svg" />
							</div>
							<div class="img_box">
								<img src="/images/maximize_icon.svg" />
							</div>
							<div class="img_box">
								<img src="/images/refresh_icon.svg" />
							</div>
						</div>
					</div>
					<div class="add_dashboard_content_main piechart_content_main">
						<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
						<div class="main_group box1">
							<label for="filter_name">저장된 필터<span class="not_null_check">*</span></label>
							<input type="text" id="filter_name" />
							<div>
								<button>고급 검색</button>
							</div>
							<div class="alert_box">선택한 필터 없음</div>
						</div>
						<div class="alert_box">선택한 프로젝트 없음</div>
						<div class="main_group box2">
							<label for="view_num">결과의 수<span class="not_null_check">*</span></label>
							<input type="text" id="view_num" value="10" />
							<p>표시할 결과의 수(최대 50개)</p>
						</div>
						<div class="main_group box3">
							<label>표시할 열<span class="not_null_check">*</span></label>
							<form>
								<table>
									<colgroup>
										<col width="8%" />
										<col width="73%" />
										<col width="19%" />
									</colgroup>
									<tr>
										<td>
											<div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
											</div>
										</td>
										<td>이슈 유형</td>
										<td>
											<button>
												<img src="/images/trash_icon.svg" / width="16" height="16">
											</button>
										</td>
									</tr>
									<tr>
										<td>
											<div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
											</div>
										</td>
										<td>키</td>
										<td>
											<button>
												<img src="/images/trash_icon.svg" / width="16" height="16">
											</button>
										</td>
									</tr>
									<tr>
										<td>
											<div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
											</div>
										</td>
										<td>요약</td>
										<td>
											<button>
												<img src="/images/trash_icon.svg" / width="16" height="16">
											</button>
										</td>
									</tr>
									<tr>
										<td>
											<div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
												<div>
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
													<img
														src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAADCAYAAABWKLW/AAAAGklEQVR4XmNgAIJz507/B9FgxooVSyAcZAAA9ukJwPIcIAcAAAAASUVORK5CYII=">
												</div>
											</div>
										</td>
										<td>우선순위</td>
										<td>
											<button>
												<img src="/images/trash_icon.svg" / width="16" height="16">
											</button>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div class="main_group box4">
							<label>표시할 열 추가</label>
							<select>
								<option value=1>담당자</option>
								<option value=2>레이블</option>
								<option value=3>보고자</option>
								<option value=4>상태</option>
								<option value=5>우선순위</option>
								<option value=6>이슈 유형</option>
							</select>
							<p>필드를 선택하여 위의 목록에 해당 필드를 추가하십시오.</p>
						</div>
						<div class="save_btn">
							<button>저장</button>
						</div>
					</div>`

async function addDashboardGarget(tagStr, order) {
	let uri = "/api/dashboard/create/";
	let className = "";
	let idx = 0;
	switch(order){
		case 0:
			uri += "pie_chart"
			className = "dashboard_pie_chart"
			break;
		case 1:
			uri += "allot"
			className = "dashboard_allot"
			break;
		case 2:
			uri += "issue_complete"
			className = "dashboard_issue_complete"
			break;
		case 3:
			uri += "issue_recent";
			className = "dashboard_issue_recent"
			break;
		case 4:
			uri += "issue_statistics";
			className = "dashboard_issue_statistics"
			break;
		case 5:
			uri += "issue_filter";
			className = "dashboard_issue_filter"
			break;
	}
	async function dashboardItemAddFetch(uri){
		const dashboardIdx = window.location.pathname.split("/")[3];
		const res = await fetch(uri, {method:"post", 
									  headers:{"Content-Type":"application/json"}, 
									  body:JSON.stringify(dashboardIdx)
								     }
							   );
		const data = await res.json();
		// idx값 가져오기
		if(data){
			idx = data;
		}
	}
	await dashboardItemAddFetch(uri);
	
	const gadgetContentContainer1 = document.querySelector(".dashboard_content_container .container1");
	const gadgetContentEmptyBox = document.querySelector(".dashboard_content_container .empty_box");
	const newDiv = document.createElement("div");
	newDiv.classList.add("add_dashboard_content");
	newDiv.innerHTML = `<div class="${className} dashboard_item" idx-data="${idx}">${tagStr}</div>`;
	//gadgetContentEmptyBox.classList.remove("show");
	gadgetContentContainer1.prepend(newDiv);
	gadgetContentEmptyBox.classList.remove("show");
}

const htmlTagArr = [setPieChartContent(), setAllotContent(), setIssueComplete(), setIssueRecent(), setIssueStatistics(), issueFilter];
const gadgetAddBtn = document.querySelectorAll(".gadget_item_header button");
gadgetAddBtn.forEach(function(btn, idx) {
	btn.addEventListener("click", function() {
		addDashboardGarget(htmlTagArr[idx], idx);
	});
});

// 새 가젯 추가 클릭
document.querySelector(".dashboard_content_container").addEventListener("click", function(e){
	const btn = e.target.closest(".empty_box span");
	if(btn === null) return;
	
	// 가젯 추가창 open
	const gadgetBox = document.querySelector(".dashboard_add_gadget");
	gadgetBox.style.width = '400px';
	gadgetBox.style.borderLeft = '3px solid #ddd';
	document.querySelector(".dashboard_container").style.gridTemplateColumns = '1fr 400px';
	
	// 가젯 input 포커스
	const gadgetInput = document.querySelector(".gadget_header .input_box input");
	gadgetInput.focus();
});

const box1 = document.querySelector(".dashboard_content_container .container1");
const box2 = document.querySelector(".dashboard_content_container .container2");
new Sortable(box1, {
	group: 'shared',
	filter: '.empty_box',
	animation: 150,
	forceFallback: true,
	onStart: function(evt){
		const origin = evt.item;
		const originHeight = window.getComputedStyle(origin).height;
		
		evt.item.style.height = originHeight;
		evt.item.querySelector(".dashboard_item").classList.add("hide");
		evt.item.classList.add("dragging");
		
		const dragItem = document.querySelector(".sortable-drag .dashboard_item_content");
		dragItem.innerHTML = `<div class="drag_item" style="height:100px"><img src="/images/chart_preview_icon.svg" width="16" height="16"></div>`;
		
		document.querySelector(".sortable-drag").style.height = "200px";
	},
	onEnd: function(evt){
		evt.item.classList.remove("dragging");
		evt.item.querySelector(".dashboard_item").classList.remove("hide");
		
		const box1DashboardItems = box1.querySelectorAll(".dashboard_item");
		const box2DashboardItems = box2.querySelectorAll(".dashboard_item");
		if(box1DashboardItems.length === 0){
			box1.querySelector(".empty_box").classList.add("show");
		}else{
			box1.querySelector(".empty_box").classList.remove("show");
		}
		if(box2DashboardItems.length === 0){
			box2.querySelector(".empty_box").classList.add("show");
		}else{
			box2.querySelector(".empty_box").classList.remove("show");
		}
	}
});
new Sortable(box2, {
	group: 'shared',
	filter: '.empty_box',
	animation: 150,
	forceFallback: true,
	onStart: function(evt){
		const origin = evt.item;
		const originHeight = window.getComputedStyle(origin).height;
		
		evt.item.style.height = originHeight;
		evt.item.querySelector(".dashboard_item").classList.add("hide");
		evt.item.classList.add("dragging");
		
		const dragItem = document.querySelector(".sortable-drag .dashboard_item_content");
		dragItem.innerHTML = `<div class="drag_item" style="height:100px"><img src="/images/chart_preview_icon.svg" width="16" height="16"></div>`;
		
		document.querySelector(".sortable-drag").style.height = "200px";
	},
	onEnd: function(evt){
		evt.item.classList.remove("dragging");
		evt.item.querySelector(".dashboard_item").classList.remove("hide");
		
		const box1DashboardItems = box1.querySelectorAll(".dashboard_item");
		const box2DashboardItems = box2.querySelectorAll(".dashboard_item");
		if(box1DashboardItems.length === 0){
			box1.querySelector(".empty_box").classList.add("show");
		}else{
			box1.querySelector(".empty_box").classList.remove("show");
		}
		if(box2DashboardItems.length === 0){
			box2.querySelector(".empty_box").classList.add("show");
		}else{
			box2.querySelector(".empty_box").classList.remove("show");
		}
	}
});
