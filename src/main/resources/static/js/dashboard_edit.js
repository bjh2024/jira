// 가젯 리스트 hidden
document.querySelector(".gadget_header .title .img_box").addEventListener("click", function() {
	document.querySelector(".dashboard_add_gadget").style.width = '0px';
	document.querySelector(".dashboard_add_gadget").style.borderLeft = 'none';
	document.querySelector(".dashboard_container").style.gridTemplateColumns = '1fr 0';
});
// 가젯 리스트 show
document.querySelector(".gnb_btn2.gadget_add_btn").addEventListener("click", function() {
	document.querySelector(".dashboard_add_gadget").style.width = '400px';
	document.querySelector(".dashboard_add_gadget").style.borderLeft = '3px solid #ddd';
	document.querySelector(".dashboard_container").style.gridTemplateColumns = '1fr 400px';
})

// 대시보드 더보기 show
document.querySelector("body").addEventListener("click", function(e) {
	const moreBtn = e.target.closest(".img_box.dashboard_more");
	if (moreBtn !== null) {
		moreBtn.querySelector(".more_gadget_option").classList.toggle("show");
		// 대시보드 더보기 버튼 이벤트(구성, 복제, 삭제)
		const btn = e.target.closest(".more_gadget_option .btn_box button");
		if (btn !== null) {
			const btnText = btn.querySelector("span").innerText;
			console.log(btnText);
			switch (btnText) {
				case "구성":
					const chartTitle = e.target.closest(".add_dashboard_content_header").querySelector("h2 span").innerText.split(":")[0];
					const projectName = e.target.closest(".add_dashboard_content_header").querySelector("h2 span").innerText.split(":")[1];
					const contentBox = e.target.closest(".add_dashboard_content");
					const colName = e.target.closest(".add_dashboard_content").querySelector(".dashboard_pie_chart_content .header h3").innerText;
					editChartChange(contentBox, chartTitle, projectName, colName);
					break;
				case "복제":
					break;
				case "삭제":
					break;
			}
		}
	} else {
		document.querySelector(".more_gadget_option.show")?.classList.remove("show");
	}
});

function editChartChange(contentBox, chartTitle, projectName, colName, idx) {
	contentBox.innerHTML = "";
	switch (chartTitle) {
		case "파이 차트":
			contentBox.innerHTML = setPieChartContent(true, projectName, colName, 1);
			break;
		case "나에게 할당됨":
			break;
		case "만듦 대비 해결됨 차트":
			break;
		case "최근에 만듦 차트":
			break;
		case "이슈 통계":
			break;
		case "결과 필터":
			break;
	}
}

function addDashboardGarget(tagStr) {
	const gadgetContentBox1 = document.querySelector(".dashboard_content_container .box1");
	const gadgetContentEmptyBox = document.querySelector(".dashboard_content_container .box1 .empty_box");
	const newDiv = document.createElement("div");
	newDiv.classList.add("add_dashboard_content");
	newDiv.innerHTML = tagStr;
	//gadgetContentEmptyBox.classList.remove("show");
	gadgetContentBox1.prepend(newDiv);
}
// 파이 차트 값 add
function setPieChartContent(isChange, projectName, colName, chartIdx) {
	const pieChartContent = `
						<div class="add_dashboard_content_header">
							<h2>
								<img src="/images/switch_position_icon.svg" />
								<span>파이 차트</span>
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
						<div class="add_dashboard_content_main piechart_content_main">
							<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
							<div class="main_group box1">
								<label for="project_name">프로젝트 또는 저장된 필터<span class="not_null_check">*</span></label>
								${isChange ? `<div class="project_name_box">${projectName}</div>` : `<div class="project_name_box">선택된 필터/프로젝트 없음</div>`}
								<input type="text" id="project_name" placeholder="검색" />
								<p>그래프에 기준으로 사용할 프로젝트 또는 저장된 필터입니다.</p>
								<div>
									<button>고급 검색</button>
								</div>
							</div>
							<div class="main_group box2">
								<label for="pie_statistic">통계 유형<span class="not_null_check">*</span></label>
								<select name="statistic" id="pie_statistic">
									<option value="담당자" ${colName === '담당자' ? 'selected' : ''}>담당자</option>
									<option value="레이블" ${colName === '레이블' ? 'selected' : ''}>레이블</option>
									<option value="보고자" ${colName === '보고자' ? 'selected' : ''}>보고자</option>
									<option value="상태" ${colName === '상태' ? 'selected' : ''}>상태</option>
									<option value="우선 순위" ${colName === '우선 순위' ? 'selected' : ''}>우선 순위</option>
									<option value="이슈 유형" ${colName === '이슈 유형' ? 'selected' : ''}>이슈 유형</option>
								</select>
								<p>이 필터를 표시할 통계의 유형을 선택.</p>
							</div>
							<div class="main_group box3">
								<label for="update">자동 새로 고침</label>
								<div class="update_check_box">
									<input type="checkbox" id="update" />
									<label for="update">매 15분마다 업데이트</label>
								</div>
							</div>
							<div class="save_btn">
								<button idx-data="${chartIdx}">저장</button>
							</div>
						</div>
						<div class="add_dashboard_content_footer">
							<img src="/images/refresh_icon.svg" width="16" height="16"/>
							<span>14분 전</span>
						</div>`;
	return pieChartContent;
}

const myResponsibilityIssueContent = `
						<div class="add_dashboard_content_header">
							<h2>
								<img src="/images/switch_position_icon.svg" />
								<span>나에게 할당됨</span>
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
						<div class="add_dashboard_content_main my_responsibility_issue_main">
							<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
							<div class="main_group box1">
								<label>결과의 수<span class="not_null_check">*</span></label>
								<input type="text" value="10" />
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
											<td>우선 순위</td>
											<td>
												<button>
													<img src="/images/trash_icon.svg" / width="16" height="16">
												</button>
											</td>
										</tr>
									</table>
								</form>
								<p>끌어서 놓아서 필드 순서를 변경합니다.</p>
							</div>
							<div class="main_group box3">
								<label>표시할 열 추가</label>
								<select>
									<option value="1">이슈 유형</option>
									<option value="2">키</option>
									<option value="3">요약</option>
									<option value="4">우선 순위</option>
									<option value="5">레이블</option>
									<option value="6">보고자</option>
									<option value="7">담당자</option>
									<option value="8">상태</option>
									<option value="9">프로젝트</option>
								</select>
								<p>필드를 선택하여 위의 목록에 해당 필드를 추가하십시오.</p>
							</div>
							<div class="main_group box4">
								<label for="update">자동 새로 고침</label>
								<div class="update_check_box">
									<input type="checkbox" id="update" />
									<label for="update">매 15분마다 업데이트</label>
								</div>
							</div>
							<div class="save_btn">
								<button>저장</button>
							</div>
						</div>
						<div class="add_dashboard_content_footer">
							<img src="/images/refresh_icon.svg" width="16" height="16" />
							<span>14분 전</span>
						</div>`
const issueComplete = `<div class="add_dashboard_content_header">
						<h2>
							<img src="/images/switch_position_icon.svg" />
							<span>만듦 대비 해결됨 차트</span>
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
					<div class="add_dashboard_content_main">
						<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
						<div class="main_group box1">
							<label for="project_name">프로젝트 또는 저장된 필터<span class="not_null_check">*</span></label>
							<div class="project_name_box">선택된 필터/프로젝트 없음</div>
							<input type="text" id="project_name" placeholder="검색" />
							<p>그래프에 기준으로 사용할 프로젝트 또는 저장된 필터입니다.</p>
							<div>
								<button>고급 검색</button>
							</div>
						</div>
						<div class="main_group box2">
							<label for="unit_period">기간<span class="not_null_check">*</span></label>
							<select name="statistic" id="unit_period">
								<option value="매시간">매시간</option>
								<option value="매일" selected>매일</option>
								<option value="매주">매주</option>
								<option value="매월">매월</option>
								<option value="매년">매년</option>
							</select>
							<p>이 필터를 표시할 통계의 유형을 선택.</p>
						</div>
						<div class="main_group box3">
							<label for="prev_date">이전 날짜<span class="not_null_check">*</span></label>
							<input type="text" id="prev_date" value="30" />
							<p>선택한 기간내에서 자료를 수집할 과거 일 수</p>
						</div>
						<div class="main_group box4">
							<label for="update">자동 새로 고침</label>
							<div class="update_check_box">
								<input type="checkbox" id="update" />
								<label for="update">매 15분마다 업데이트</label>
							</div>
						</div>
						<div class="save_btn">
							<button>저장</button>
						</div>
					</div>
					<div class="add_dashboard_content_footer">
						<img src="/images/refresh_icon.svg" width="16" height="16" />
						<span>14분 전</span>
					</div>`
const issueRecent = `<div class="add_dashboard_content_header">
								<h2>
									<img src="/images/switch_position_icon.svg" />
									<span>최근에 만듦 차트</span>
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
							<div class="add_dashboard_content_main">
								<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
								<div class="main_group box1">
									<label for="project_name">프로젝트 또는 저장된 필터<span class="not_null_check">*</span></label>
									<div class="project_name_box">선택된 필터/프로젝트 없음</div>
									<input type="text" id="project_name" placeholder="검색" />
									<p>그래프에 기준으로 사용할 프로젝트 또는 저장된 필터입니다.</p>
									<div>
										<button>고급 검색</button>
									</div>
								</div>
								<div class="main_group box2">
									<label for="pie_statistic">기간<span class="not_null_check">*</span></label>
									<select name="statistic" id="pie_statistic">
										<option value="매시간">매시간</option>
										<option value="매일" selected>매일</option>
										<option value="매주">매주</option>
										<option value="매월">매월</option>
										<option value="매년">매년</option>
									</select>
									<p>기간의 길이가 그래프에 표시됩니다.</p>
								</div>
								<div class="main_group box3">
									<label for="prev_date">이전 날짜<span class="not_null_check">*</span></label>
									<input type="text" id="prev_date" value="30" />
									<p>그래프에 표시할 일수(오늘 포함)</p>
								</div>
								<div class="main_group box4">
									<label for="update">자동 새로 고침</label>
									<div class="update_check_box">
										<input type="checkbox" id="update" />
										<label for="update">매 15분마다 업데이트</label>
									</div>
								</div>
								<div class="save_btn">
									<button>저장</button>
								</div>
							</div>
							<div class="add_dashboard_content_footer">
								<img src="/images/refresh_icon.svg" width="16" height="16" />
								<span>14분 전</span>
							</div>`
const issueStatistics = `<div class="add_dashboard_content_header">
							<h2>
								<img src="/images/switch_position_icon.svg" />
								<span>이슈 통계</span>
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
						<div class="add_dashboard_content_main">
							<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
							<div class="main_group box1">
								<label for="project_name">프로젝트 또는 저장된 필터<span class="not_null_check">*</span></label>
								<div class="project_name_box">선택된 필터/프로젝트 없음</div>
								<input type="text" id="project_name" placeholder="검색" />
								<p>그래프에 기준으로 사용할 프로젝트 또는 저장된 필터입니다.</p>
								<div>
									<button>고급 검색</button>
								</div>
							</div>
							<div class="main_group box2">
								<label for="statistic_type">통계 유형<span class="not_null_check">*</span></label>
								<select name="statistic" id="statistic_type">
									<option value="담당자">담당자</option>
									<option value="레이블">레이블</option>
									<option value="보고자">보고자</option>
									<option value="상태">상태</option>
									<option value="우선 순위">우선 순위</option>
									<option value="이슈 유형">이슈 유형</option>
								</select>
								<p>이 필터를 표시할 통계의 유형을 선택.</p>
							</div>
							<div class="main_group box3">
								<label for="view_num">결과의 수<span class="not_null_check">*</span></label>
								<input type="text" id="view_num" value="10" />
								<p>표시할 결과의 수(최대 50개)</p>
							</div>
							<div class="main_group box4">
								<label for="update">자동 새로 고침</label>
								<div class="update_check_box">
									<input type="checkbox" id="update" />
									<label for="update">매 15분마다 업데이트</label>
								</div>
							</div>
							<div class="save_btn">
								<button>저장</button>
							</div>
						</div>
						<div class="add_dashboard_content_footer">
							<img src="/images/refresh_icon.svg" width="16" height="16" />
							<span>14분 전</span>
						</div>`
const issueFilter = `<div class="add_dashboard_content_header">
						<h2>
							<img src="/images/switch_position_icon.svg" />
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
					<div class="add_dashboard_content_main piechart_content_main">
						<p>필수 필드는 별표로 표시되어 있습니다<span class="not_null_check">*</span></p>
						<div class="main_group box1">
							<label for="filter_name">저장된 필터<span class="not_null_check">*</span></label>
							<input type="text" id="filter_name" />
							<div>
								<button>고급 검색</button>
							</div>
						</div>
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
										<td>우선 순위</td>
										<td>
											<button>
												<img src="/images/trash_icon.svg" / width="16" height="16">
											</button>
										</td>
									</tr>
								</table>
							</form>
							<p>끌어서 놓아서 필드 순서를 변경합니다.</p>
						</div>
						<div class="main_group box4">
							<label>표시할 열 추가</label>
							<select>
								<option value="1">이슈 유형</option>
								<option value="2">키</option>
								<option value="3">요약</option>
								<option value="4">우선 순위</option>
								<option value="5">레이블</option>
								<option value="6">보고자</option>
								<option value="7">담당자</option>
								<option value="8">상태</option>
								<option value="9">프로젝트</option>
							</select>
							<p>필드를 선택하여 위의 목록에 해당 필드를 추가하십시오.</p>
						</div>
						<div class="main_group box5">
							<label for="update">자동 새로 고침</label>
							<div class="update_check_box">
								<input type="checkbox" id="update" />
								<label for="update">매 15분마다 업데이트</label>
							</div>
						</div>
						<div class="save_btn">
							<button>저장</button>
						</div>
					</div>
					<div class="add_dashboard_content_footer">
						<img src="/images/refresh_icon.svg" width="16" height="16" />
						<span>14분 전</span>
					</div>`

const htmlTagArr = [setPieChartContent(false, '', ''), myResponsibilityIssueContent, issueComplete, issueRecent, issueStatistics, issueFilter];
const gadgetAddBtn = document.querySelectorAll(".gadget_item_header button");
gadgetAddBtn.forEach(function(btn, idx) {
	btn.addEventListener("click", function() {
		addDashboardGarget(htmlTagArr[idx]);
	});
});

