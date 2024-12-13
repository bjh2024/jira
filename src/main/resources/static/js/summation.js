const projectDetailBtn = document.querySelector(".project_detail_btn_box button");
let isClick = false;
projectDetailBtn.addEventListener("click", function(e) {
	isClick = !isClick;
	if (isClick) {
		projectDetailBtn.nextElementSibling.classList.add("show");
	} else {
		projectDetailBtn.nextElementSibling.classList.remove("show");
	}
});

// 도넛차트 원안에 퍼센트 표시
function updateChartRatio(data, num, comment, color) {
	const total = data.reduce((prev, cur) => prev + cur, 0);
	const ratio = total != 0 ? Math.round((num / total) * 100) : 0;
	const doughnutChartRatio = document.querySelector(
		".doughnut_chart_ratio"
	);
	doughnutChartRatio.innerHTML = `<p style="color:${color}">${ratio}%</p><span>${comment}</span>`;
}

// 페이지 load시 도넛 차트
document.addEventListener('DOMContentLoaded', async function() {
	const doughnutChartTag = document.querySelector(".issue_status_doughnut_chart");
	// 이슈 상태 데이터 가져오기
	let statuslabelArr = [];
	let statusNumArr = [];
	const statusColor = ["#DFE1E6", "#FF7452", "#2684FF", "#57D9A3", "#8777D9", "#00C7E6"];
	// 동기처리로 fetch값 가져오기
	async function getStatusDTO() {
	        try {
				const url = `/api/summation/status_chart?uri=${window.location.pathname}`;
	            const res = await fetch(url, {method: "get"});
	            const statusChartDTO = await res.json();
	            
	            statusChartDTO.forEach(function(item) {
	                statuslabelArr.push(item.name);
	                statusNumArr.push(item.count);
	            });
	        } catch (e) {
	            console.error(e);
	        }
	    }
	await getStatusDTO();
	let doughnutChatSum = statusNumArr.reduce((acc, cur) => {return acc + cur});
	const doughnutChart = doughnutChatSum == 0 ?
	// 모든 값(issueType)이 없을경우
	new Chart(doughnutChartTag, {
			type: "doughnut",
			data: {
				labels: [""],
				datasets: [
					{
						data: [1],
						borderWidth: 0,
						backgroundColor: ["#DFE1E6"],
					},
				],
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				rotation: Math.PI,
				plugins: {
					tooltip: {
						enabled: false, // 툴팁 활성화
					},
					legend: {
						display: false,
					},
				},
			},
		})
	: new Chart(doughnutChartTag, {
		type: "doughnut",
		data: {
			labels: statuslabelArr,
			datasets: [
				{
					data: statusNumArr,
					borderWidth: 0,
					backgroundColor: statusColor,
				},
			],
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			rotation: Math.PI,
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
					updateChartRatio(statusNumArr, value, label, color);
				}
			}
		},
	});
	
	// chartList 그리기
	let chartListSum = 0;
	const chartListBox = document.querySelector(".doughnut_chart_list");
	statuslabelArr.forEach(function(label, idx){
		const aElement = document.createElement("a");
		aElement.innerHTML = `
				<div class="doughnut_chart_header">
					<div class="doughnut_chart_color_box" style="background-color: ${statusColor[idx%statusColor.length]};"></div>
					<span>${statuslabelArr[idx]}</span>
				</div>
				<span class="doughnut_chart_number">${statusNumArr[idx]}</span>
		`
		chartListBox.appendChild(aElement);
		chartListSum += statusNumArr[idx];
	});
	// 합계 그리기
	const sumDiv = document.createElement("div");
	sumDiv.classList.add("doughnut_chart_sum");
	sumDiv.innerHTML = `
			<div class="doughnut_chart_header sum">합계</div>
			<span>${chartListSum}</span>
	`
	chartListBox.appendChild(sumDiv);
	// 도넛차트 리스트 hover시 이벤트
	const doughnutChartList = document.querySelectorAll('.doughnut_chart_box>.doughnut_chart_list>a');
	doughnutChartList.forEach(function(item) {
		item.addEventListener("mouseover", function(e) {
			const num = this.querySelector(".doughnut_chart_number").innerText;
			const comment = this.querySelector(".doughnut_chart_header span").innerText;
			let color = getComputedStyle(this.querySelector(".doughnut_chart_color_box")).backgroundColor;
			if (color === 'rgb(223, 225, 230)') {
				color = '#172b4d';
			}
			updateChartRatio(statusNumArr, num, comment, color);
		});
	});
	
	
	// 첫 페이지 동작시 퍼센트 값 넣기
	function initupdateChartRatio() {
		let idx = 0;
		for(let i = 0; i < statusNumArr.length; i++){
			if(statusNumArr[i] != 0){
				idx = i;
				break;
			}
		}
		let color = statusColor[idx]
		if (color === '#DFE1E6') {
			color = '#172b4d';
		}
		updateChartRatio(statusNumArr, statusNumArr[idx], doughnutChart.data.labels[idx], color);
	}
	initupdateChartRatio();
	
	// 우선순위 차트
	const priorityChart = document.querySelector(".priority_chart");
	const priorityLabelArr = [];
	const priorityNumArr = [];
	const priorityColor = ["#DE350B", "#FF7452", "#FFAB00", "#4C9AFF", "#0065FF"];
	async function getPriorityCharDTO(){
		const url = `/api/summation/priority_chart?uri=${window.location.pathname}`;
		const res = await fetch(url, { method: "get"});
        const priorityCharDTO = await res.json();
		console.log(priorityCharDTO);
		
		priorityCharDTO.forEach(function(item){
			priorityLabelArr.push(item.name);
			priorityNumArr.push(item.count);
		});
		
	}
	await getPriorityCharDTO();
	new Chart(priorityChart, {
		type: "bar",
		data: {
			labels: priorityLabelArr,
			datasets: [
				{
					label: '',
					data: priorityNumArr,
					backgroundColor: priorityColor,
				},
			],
		},
		options: {
			responsive: false,               // 반응형 차트
			maintainAspectRatio: false,
			scales: {
				y: {
					beginAtZero: true,  // y축 0부터 시작
					ticks: {
						stepSize: 1,  // y축 그리드 간격을 1로 설정
					},
				},
				x: {
					display: false,  // Y축 그리드 선 표시
				},
			},
			barPercentage: 1,  // 각 막대의 너비 비율을 설정 (0.9 기본값)
			categoryPercentage: 0.5,
			plugins: {
				tooltip: {
					enabled: false, // 툴팁 비활성화
				},
				legend: {
					display: false, // 범례 숨기기
				},
			},
		},
	});
});

