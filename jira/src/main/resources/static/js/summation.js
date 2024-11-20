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

const doughnutChartTag = document.querySelector(".issue_status_doughnut_chart");
const statusNumArr = [3, 1, 2, 1];
const statusColor = ["#DFE1E6", "#FF7452", "#2684FF", "#57D9A3"];

const doughnutChart = new Chart(doughnutChartTag, {
	type: "doughnut",
	data: {
		labels: ["할일이 많다", "해야 할 일", "진행중", "완료"],
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
// 첫 페이지 동작시 퍼센트 값 넣기
initupdateChartRatio();
function initupdateChartRatio() {
	let color = statusColor[0]
	if (color === '#DFE1E6') {
		color = '#172b4d';
	}
	updateChartRatio(statusNumArr, statusNumArr[0], doughnutChart.data.labels[0], color);
}

function updateChartRatio(data, num, comment, color) {
	const total = data.reduce((prev, cur) => prev + cur, 0);

	const ratio = Math.round((num / total) * 100);

	const doughnutChartRatio = document.querySelector(
		".doughnut_chart_ratio"
	);
	doughnutChartRatio.innerHTML = `<p style="color:${color}">${ratio}%</p><span>${comment}</span>`;
}

const doughnutChartList = document.querySelectorAll('.doughnut_chart_box>.doughnut_chart_list>a');
doughnutChartList.forEach(function(item) {
	item.addEventListener("mouseover", function(e) {
		const num = this.querySelector(".doughnut_chart_number").innerText;
		const comment = this.querySelector(".doughnut_chart_header span").innerText;
		let color = getComputedStyle(this.querySelector(".doughnut_chart_color_box")).backgroundColor;
		if (color === 'rgb(223, 225, 230)') {
			color = '#172b4d';
		}
		console.log(color);
		updateChartRatio(statusNumArr, num, comment, color);
	});
});

const priorityChart = document.querySelector(".priority_chart");
const priorityNumArr = [1, 1, 2, 1, 1];
const priorityColor = ["#de350b", "#ff7452", "#ffab00", "#4c9aff", "#0065ff"];
new Chart(priorityChart, {
	type: "bar",
	data: {
		labels: ["Highest", "High", "Medium", "Low", "Lowest"],
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