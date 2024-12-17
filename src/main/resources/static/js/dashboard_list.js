// modal창 on
document.querySelector(".dashboard_list_title button").addEventListener("click", function() {
		const modal = document.querySelector(".dashboard_craete_modal");
		modal.classList.add("show");
});

// 대시보드 리스트 필터 이벤트 
document.querySelectorAll(".dashboard_list_filter input").forEach(function(input) {
	input.addEventListener("blur", function() {
		this.nextElementSibling.classList.remove("show");
	});
});

document.querySelector("body").addEventListener("mousedown", function(e) {
	if (e.target.closest(".dashboard_list_filter_item") !== null) {
		if (e.target.closest(".dashboard_list_filter_item").children[3].className.includes("show")) {
			e.target.closest(".dashboard_list_filter_item").children[3].classList.remove("show");
		} else {
			e.target.closest(".dashboard_list_filter_item").children[3].classList.add("show");
		}
	} else {
		document.querySelector(".select_top_menue.show")?.classList.remove("show");
	}
});

document.querySelectorAll(".dashboard_list_filter_item .select_top_menue_list .item").forEach(function(item){
	item.addEventListener("mousedown", function(){
		const input = this.closest(".dashboard_list_filter_item").children[2]
		console.log(input);
		input.placeholder = this.innerText;
	});
});

// 즐겨찾기 추가 제거
document.querySelectorAll(".dashboard_list_item_box > .star_box").forEach(function(box) {
	box.addEventListener("click", function(e) {
		e.preventDefault();
		const starImg = this.children[0];

		if (starImg.getAttribute("src") === "/images/star_icon_yellow.svg") {
			starImg.setAttribute("src", "/images/star_icon_empty.svg");
		} else {
			starImg.setAttribute("src", "/images/star_icon_yellow.svg")
		}
	});
});



function dashboardListRemoveActiveShow() {
	const moreBtnActive = document.querySelectorAll(".dashboard_list_table td:last-of-type>.img_box.active");
	const moreBoxShow = document.querySelectorAll(".dashboard_list_table td:last-of-type>.more_box.show");
	moreBtnActive.forEach(function(btn) {
		btn.classList.remove("active");
	});
	moreBoxShow.forEach(function(box) {
		box.classList.remove("show");
	});

}

// 더보기 클릭시 추가 제거
document.querySelectorAll(".dashboard_list_table td:last-of-type>.img_box").forEach(function(box) {
	box.addEventListener("click", function() {
		const prevMoreBoxShow = document.querySelector(".dashboard_list_table td:last-of-type>.more_box.show");
		dashboardListRemoveActiveShow();

		if (prevMoreBoxShow !== this.nextElementSibling) {
			this.classList.add("active");
			this.nextElementSibling.classList.add("show");
		}
	})
});

document.querySelector("body").addEventListener("click", function(e) {
	const moreBtn = e.target.closest(".dashboard_list_table td:last-of-type>.img_box")
	const moreBox = e.target.closest(".dashboard_list_table td:last-of-type>.more_box")
	if (moreBtn === null && moreBox === null) {
		dashboardListRemoveActiveShow();
	}
});

document.querySelectorAll(".dashboard_list_filter_item .select_top_menue_list .item").forEach(function(item) {
	item.addEventListener("click", function(e) {
	})
})


