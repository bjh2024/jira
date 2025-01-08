// aside 최근 항목 모두 보기, 별표 표시된 항목 모두 보기
const gnbItems = document.querySelectorAll(".my_work_gnb>div");
const contents = document.querySelectorAll(".my_work_content");
window.addEventListener("DOMContentLoaded", function(){
	const params = new URLSearchParams(window.location.search);
	const recentAll = params.get("recentAll");
	const starAll = params.get("starAll");
	if(recentAll !== null){
		gnbItems[1].click();
		return;
	}
	if(starAll !== null){
		gnbItems[3].click();
		return;
	}
	
});

// (작업, 확인, 나에게 할당, 별표 표시됨) 메뉴 변경시 이벤트
gnbItems.forEach(function(item, idx) {
	item.addEventListener("click", function() {
		const newURL = window.location.origin + window.location.pathname;
		window.history.replaceState({}, '', newURL);
		gnbItems.forEach(function(item, idx) {
			item.classList.remove("active");
			contents[idx].classList.remove("show");
		});
		this.classList.add("active");
		contents[idx].classList.add("show");
	});
});

// 별표 표시됨 on off
const myWorkStarBoxs = document.querySelectorAll(".my_work_content .star_img_box")
myWorkStarBoxs.forEach(function(box) {
	box.addEventListener("click", function(e) {
		e.preventDefault();
		const starImg = this.children[0];

		if (starImg.getAttribute("src") === "/images/star_icon_yellow.svg") {
			starImg.setAttribute("src", "/images/star_icon_empty.svg");
		} else {
			starImg.setAttribute("src", "/images/star_icon_yellow.svg")
		}
		let type = starImg.getAttribute("type-data");
		let idx = starImg.getAttribute("idx-data");
		let isLike = starImg.getAttribute("src") === "/images/star_icon_empty.svg" ? false : true;
		likeFetch(type, idx, isLike);
	});
});