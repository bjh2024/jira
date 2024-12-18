// 나에게 할당된 이슈 목록 그리기
function drawAllot(allot, pageNum = 0) {
	allot.setAttribute("page-num-data", pageNum);
	const rowNum = allot.getAttribute("row-num-data");
	const tbody = allot.querySelector("tbody");
	const total = allot.getAttribute("issue-total");
	const uri = `/api/dashboard/allot?pageNum=${pageNum}&col=${rowNum}&uri=${window.location.pathname}`;
	fetch(uri, { method: "get" })
		.then(res => res.json())
		.then(issueList => {
			tbody.innerHTML = "";
			issueList.forEach(function(issue) {
				tbody.innerHTML += `<tr>
										<td>
											<a href="#" class="img_box"><img src="/images/${issue.iconFilename}" width="16" height="16" /></a>
										</td>
										<td><a href="#">${issue.key}</a></td>
										<td><a href="#">${issue.name}</a></td>
										<td><img src="/images/${issue.priorityIconFilename}" width="16" height="16" /></td>
									</tr>`
			});
			const start = Number(pageNum) * rowNum + 1;
			const end = (Number(pageNum) + 1) * rowNum > total ? total : (Number(pageNum) + 1) * rowNum;
			const endPageNum = Math.ceil(total / rowNum)-1;
			allot.querySelector(".paging_box").innerHTML = `<p>
																<a href="#">${total}</a>개의 이슈 중 <span>${start}</span>에서 <span>${end}</span>까지 표시.
															</p>
															<div class="paging_btn_box">
																<span class="prev_btn" style="display:${Number(pageNum) === 0 ? 'none' : 'block'}"></span>
																<div class="number_box">
																</div>
																<span class="next_btn" style="display:${Number(pageNum) === endPageNum ? 'none' : 'block'}"></span>
															</div>`
			for (let i = 0; i <= endPageNum; i++) {
				allot.querySelector(".paging_box .number_box").innerHTML += `<button class="${Number(pageNum) === i ? 'active' : ''}">${i + 1}</button>`
			}
		})
		.catch(err => {
			console.error(err);
		});
}
// 파이차트 비율
function updateChartRatio(data, num, comment, color, canvas) {
	const total = data.reduce((prev, cur) => prev + cur, 0);

	const ratio = total !== 0 ? Math.round((num / total) * 100) : 0;
	canvas.previousElementSibling.innerHTML = `<p style="color:${color}">${ratio}%</p><span>${comment}</span>`;
}
// 파이차트 그리기
async function drawPieChart(pieChart) {
	let idx = pieChart.getAttribute("idx-data");
	let dashboardColIdx = pieChart.getAttribute("dashboardColIdx-data");
	let projectIdx = pieChart.getAttribute("projectIdx-data");
	// jiraID + projectKey
	let statusNumArr = [];
	let labels = [];
	async function pieChartFetch(type) {
		let uri = "/api/dashboard/issue_info_chart/";
		switch (type) {
			case "1":
				uri += "manager";
				break;
			case "2":
				uri += "label";
				break;
			case "3":
				uri += "reporter";
				break;
			case "4":
				uri += "issue_status";
				break;
			case "5":
				uri += "priority";
				break;
			case "6":
				uri += "issue_type";
				break;
		}
		uri += `?projectIdx=${projectIdx}`;
		const res = await fetch(uri, { method: "get" });
		const data = await res.json();
		data.forEach(function(item) {
			statusNumArr.push(item.count);
			labels.push(item.name !== '' ? item.name : '없음');
		});
		const total = statusNumArr.reduce((acc, num) => acc + num);
		pieChart.querySelector(".dashboard_pie_chart_content .header .issue_total").innerHTML = `<a href="#">모든 이슈: ${total}</a>`
	}
	await pieChartFetch(dashboardColIdx);
	
	const doughnutChartTag = document.querySelector(`#pieChart-${idx}`);
	const statusColor = ["#4688EC", "#F15B50", "#B38600", "#22A06B", "#2898BD", "#AF59E1"];
	const doughnutChart = new Chart(doughnutChartTag, {
		type: "doughnut",
		data: {
			labels: labels,
			datasets: [
				{
					data: statusNumArr,
					borderWidth: 1,
					backgroundColor: statusColor,
				},
			],
		},
		options: {
			cutout: '35%',
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				tooltip: {
					enabled: false, // 툴팁 활성화
				},
				legend: {
					display: false,
				},
			},
			onHover: function(event, chartElement) {
				if (chartElement && chartElement.length) {
					const index = chartElement[0].index; // hover된 요소의 데이터 인덱스
					const label = this.data.labels[index]; // 해당 인덱스의 label
					const value = this.data.datasets[0].data[index];
					let color = this.data.datasets[0].backgroundColor[index];
					if (color === '#DFE1E6') {
						color = '#172b4d';
					}
					updateChartRatio(statusNumArr, value, label, color, this.canvas);
				}
			}
		},
	});
	// 파이차트 리스트
	labels.forEach(function(item, idx) {
		pieChart.querySelector(".main").innerHTML += `<a href="#">
															<div>
																<div class="color_box" style="background-color: ${statusColor[idx % statusColor.length]}"></div>
																<p>${labels[idx]}</p>
															</div>
															<span>${statusNumArr[idx]}</span>
														</a>`
	});
	// 파이차트 리스트 mouseover 이벤트
	pieChart.querySelector(".dashboard_pie_chart_content .main").addEventListener("mouseover", function(e) {
		const item = e.target.closest("a");
		if (item !== null) {
			const num = item.querySelector("span").innerText;
			const comment = item.querySelector("p").innerText
			const color = item.querySelector(".color_box").style.backgroundColor;
			updateChartRatio(statusNumArr, num, comment, color, doughnutChartTag);
		}
	});
}

// 날짜 배열 생성
function getDayArr(startDay, endDay) {
	let resArr = [];
	const startDate = new Date(startDay);
	const endDate = new Date(endDay);
	while (startDate.getTime() <= endDate.getTime()) {
		let month = startDate.getMonth() + 1;
		month = month < 10 ? '0' + month : month;
		let date = startDate.getDate();
		date = date < 10 ? '0' + date : date;
		startDate.setDate(startDate.getDate() + 1);
		resArr.push(`${startDate.getFullYear()}-${month}-${date}`);
	}
	return resArr;
}

// 만듦 대비 해결됨 차트 그리기
async function drawIssueComplete(issueComplete) {
	const idx = issueComplete.getAttribute("idx-data");
	const projectIdx = issueComplete.getAttribute("projectIdx-data");
	const viewDate = issueComplete.getAttribute("viewDate-data");
	const endDate = new Date();
	let startDate = new Date();
	startDate.setDate(endDate.getDate() - viewDate);
	startDate = startDate.toISOString().replace("Z", "");

	let labels = getDayArr(startDate, endDate);
	async function fetchIssueCompleteChart(uri) {
		const res = await fetch(uri, { method: "get" });
		const datas = await res.json();
		let chartDatas = [];
		labels.forEach(function(label, idx) {
			let result = 0;
			for (let i = 0; i < datas.length; i++) {
				if (datas[i].date === label) {
					result = datas[i].count;
					break;
				}
			}
			chartDatas.push(result);
		});

		return chartDatas;
	}
	let finishIssueDatas = await fetchIssueCompleteChart(`/api/dashboard/issue_complete/finish?projectIdx=${projectIdx}&startDate=${startDate}`);
	let createIssueDatas = await fetchIssueCompleteChart(`/api/dashboard/issue_complete/create?projectIdx=${projectIdx}&startDate=${startDate}`);
	const issueCompleteChartTag = document.querySelector(`#issueCompleteChart-${idx}`);
	const completeChart = new Chart(issueCompleteChartTag, {
		type: "line",
		data: {
			labels: labels,
			datasets: [
				{
					data: finishIssueDatas, // 해결된 이슈의 개수 배열
					borderColor: '#8eb021',
					borderWidth: 3,
					label: "해결된 이슈",
				},
				{
					data: createIssueDatas, // 만든 이슈의 개수 배열
					borderColor: '#d04437',
					borderWidth: 3,
					label: "생성된 이슈",
				},
			],
		},
		options: {
			cutout: '35%',
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				legend: {
					display: false,
				},
			},
			scales: {
				x: {
					grid: {
						display: false, // 가로선 제거
					},
				},
				y: {
					min: 0,
					ticks: { // 단위를 1의 배수로
						display: true,
						callback: function(value) {
							return value % 1 === 0 ? value : '';
						},
					},
					grid: {
						display: true,
						lineWidth: 1,
						color: '#eee',
					},
					stepSize: 0.2, // 0.2 단위마다 그리드 선을 표시
				},
			},

		},
	});
	let createTotal = createIssueDatas.reduce((acc, num) => acc + num);
	let finishTotal = finishIssueDatas.reduce((acc, num) => acc + num);
	// 만듦 대비 해결됨 차트 리스트
	issueComplete.querySelector(".dashboard_issue_complete_content .main").innerHTML
		= `<div class="box">
				 <svg height="16" width="16" style="stroke-width: 2.5; stroke: #ff5630; fill: #fff;"><circle cx="8" cy="8" r="5"></circle></svg>
				 <p>생성된 이슈 <span>(${createTotal})</span></p>
			 </div>
			 <div class="box">
				 <svg height="16" width="16" style="stroke-width: 2.5; stroke: #36b37e; fill: #fff;"><circle cx="8" cy="8" r="5"></circle></svg>
				 <p>해결된 이슈 <span>(${finishTotal})</span></p>										
			 </div>`
};

async function drawIssueRecent(chart) {
	const idx = chart.getAttribute("idx-data");
	const projectIdx = chart.getAttribute("projectIdx-data");
	const viewDate = chart.getAttribute("viewDate-data");
	const endDate = new Date();
	let startDate = new Date();
	startDate.setDate(endDate.getDate() - viewDate);
	startDate = startDate.toISOString().replace("Z", "");

	let labels = getDayArr(startDate, endDate);
	async function fetchIssueRecentChart(uri) {
		const res = await fetch(uri, { method: "get" });
		const datas = await res.json();
		let chartDatas = [];
		labels.forEach(function(label, idx) {
			let result = 0;
			for (let i = 0; i < datas.length; i++) {
				if (datas[i].date === label) {
					result = datas[i].count;
					break;
				}
			}
			chartDatas.push(result);
		});

		return chartDatas;
	}
	let createBeginIssueDatas = await fetchIssueRecentChart(`/api/dashboard/issue_recent/begin?projectIdx=${projectIdx}&startDate=${startDate}`);
	let createFinishIssueDatas = await fetchIssueRecentChart(`/api/dashboard/issue_recent/finish?projectIdx=${projectIdx}&startDate=${startDate}`);
	const issueRecentChartTag = document.querySelector(`#issueRecentChart-${idx}`);
	const recentChart = new Chart(issueRecentChartTag, {
		type: "bar",
		data: {
			labels: labels,
			datasets: [
				{
					data: createBeginIssueDatas, // 해결되지않은 이슈의 개수 배열
					backgroundColor: '#d04437',
					label: '해결되지 않은 이슈'
				},
				{
					data: createFinishIssueDatas, // 해결된 이슈의 개수 배열
					backgroundColor: '#8eb021',
					label: '해결된 이슈'
				},
			],
		},
		options: {
			cutout: '35%',
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				legend: {
					display: false
				},
			},
			scales: {
				x: {
					grid: {
						display: false, // 가로선 제거
					},
					stacked: true
				},
				y: {
					min: 0,
					ticks: { // 단위를 1의 배수로
						display: true,
						callback: function(value) {
							return value % 1 === 0 ? value : '';
						},
						stepSize: 1,
					},
					grid: {
						display: true,
						lineWidth: 1,
						color: '#eee',
					},
					stacked: true,
					title: {
						display: true,
						text: '이슈',
						font: {
							size: 16,
							weight: 'bold',
						},
						color: '#000',
					},
				},
			},
		},
	});
	let total = createBeginIssueDatas.reduce((acc, num) => acc + num) + createFinishIssueDatas.reduce((acc, num) => acc + num);
	chart.querySelector(".dashboard_issue_recent_content h3").innerHTML = `전체 이슈:<b>${total}</b>`
}

function drawIssueStatistics(issueStatistics){
	const tbody = issueStatistics.querySelector("tbody");
	const dashboardColIdx = issueStatistics.getAttribute("dashboardColIdx-data");
	const projectIdx = issueStatistics.getAttribute("projectIdx-data");
	function issueStatisticsFetch(type, tbody) {
		let uri = "/api/dashboard/issue_info_chart/";
		switch (type) {
			case "1":
				uri += "manager";
				break;
			case "2":
				uri += "label";
				break;
			case "3":
				uri += "reporter";
				break;
			case "4":
				uri += "issue_status";
				break;
			case "5":
				uri += "priority";
				break;
			case "6":
				uri += "issue_type";
				break;
		}
		uri += `?projectIdx=${projectIdx}`;

		fetch(uri, { method: "get" })
			.then(res => res.json())
			.then(datas => {
				const total = datas.reduce((acc, data) => acc + data.count, 0);
				datas.forEach(function(data) {
					const ratio = total !== 0 ? Math.round((data.count / total) * 100) : 0;
					tbody.innerHTML += `<tr>
									<td>
										<a href="#">
											${type === '우선순위' ? `<img src="/images/${data.name.toLowerCase()}_icon.svg" />` : ''}
											<span>${data.name !== '' ? data.name : '없음'}</span>
										</a>
									</td>
									<td><a href="#">${data.count}</a></td>
									<td>
										<div class="percent_box">
											<span style="width: ${ratio}%;"></span>
										</div>
									</td>
									<td>${ratio}%</td>
								</tr>`
				});
				tbody.innerHTML += `<tr>
									<td>합계</td>
									<td><a href="#">${total}</a></td>
								</tr>`

			})
			.catch(err => {
				console.error(err);
			});
	}
	issueStatisticsFetch(dashboardColIdx, tbody);
}

window.onload = function() {
	// 나에게 할당
	const allot = document.querySelectorAll(".dashboard_allot");
	if (allot !== null) {
		allot.forEach(function(item) {
			if(item.getAttribute("is-save-data") === "1"){
				item.setAttribute("page-num-data", 0);
				drawAllot(item);
			}
		});
	}
	// 파이 차트
	const pieChart = document.querySelectorAll(".dashboard_pie_chart");
	if (pieChart !== null) {
		pieChart.forEach(function(chart) {
			if(chart.getAttribute("is-save-data") === "1")
				drawPieChart(chart);
		});
	}
	// 	만듦 대비 해결됨 차트
	const issueCompleteChart = document.querySelectorAll(".dashboard_issue_complete");
	if (issueCompleteChart !== null) {
		issueCompleteChart.forEach(function(chart) {
			if(chart.getAttribute("is-save-data") === "1")
				drawIssueComplete(chart);
		});
	}
	// 최근의 만듦 차트
	const issueRecentChart = document.querySelectorAll(".dashboard_issue_recent");
	if (issueRecentChart !== null) {
		issueRecentChart.forEach(async function(chart) {
			if(chart.getAttribute("is-save-data") === "1")
				drawIssueRecent(chart);
		});
	}

	// 이슈 통계
	const issueStatisticsChart = document.querySelectorAll(".dashboard_issue_statistics");
	if (issueStatisticsChart !== null) {
		issueStatisticsChart.forEach(function(item) {
			if(item.getAttribute("is-save-data") === "1")
				drawIssueStatistics(item);			
		});
	}

	// 결과 필터
	const issueFilterChart = document.querySelectorAll(".dashboard_issue_filter");
	if (issueFilterChart !== null) {
		issueFilterChart.forEach(function(item) {
			// if(item.getAttribute("is-save-data") === "1");
		});
	}
}

// 나에게 할당됨 => 페이징 처리
document.querySelector(".dashboard_content_container").addEventListener("click", function(e){
	const pagingBox = e.target.closest(".paging_box");
	if(pagingBox === null) return;
	const allot = e.target.closest(".dashboard_allot");
	const prevBtn = e.target.closest(".prev_btn")
	const nextBtn = e.target.closest(".next_btn")
	const btn = e.target.closest("button");
	let pageNum = 0;
	if(prevBtn !== null){
		pageNum = Number(allot.getAttribute("page-num-data")) - 1;
	}else if(nextBtn !== null){
		pageNum = Number(allot.getAttribute("page-num-data")) + 1;
	}else if(btn !== null){
		if ((btn.innerText - 1).toString() === allot.getAttribute("page-num-data")) return;
		pageNum = btn.innerText - 1;
	}
	drawAllot(allot, pageNum);
})
