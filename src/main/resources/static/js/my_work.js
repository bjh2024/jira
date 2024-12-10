// (작업, 확인, 나에게 할당, 별표 표시됨) 메뉴 변경시 이벤트
const gnbItems = document.querySelectorAll(".my_work_gnb>div");
const contents = document.querySelectorAll(".my_work_content");
gnbItems.forEach(function(item, idx) {
	item.addEventListener("click", function() {
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
		console.log(isLike);
		likeFetch(type, idx, isLike);
	});
});