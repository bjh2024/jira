// aside 어디 페이지인지 표시
document.addEventListener("DOMContentLoaded", function() {

	let url = new URL(window.location.href);

	const itemList = document.querySelectorAll(".aside_more_box .more_item_box");
	itemList.forEach(function(item) {
		if (item.getAttribute("href") === null) return;
		
		if (item.getAttribute("href") !== "/dashboard/list" &&
			item.getAttribute("href").includes("/dashboard")) {
			if (item.getAttribute("href").split("/")[3] === url.pathname.split("/")[3]) {
				item.classList.add("active2");
				item.querySelector(".more_item").classList.add("active3");
			}
		}else if (item.getAttribute("href") !== "/project/list" &&
			item.getAttribute("href").includes("/project")) {
			if (item.getAttribute("href").split("/")[2] === url.pathname.split("/")[2]) {
				item.classList.add("active2");
			}
		} else if (item.getAttribute("href") === url.pathname + url.search) {
			item.classList.add("active2");
			item.querySelector(".more_item").classList.add("active3");
		} else if (item.getAttribute("href") === url.pathname && !item.getAttribute("href").includes("/filter")) {
			item.classList.add("active2");
			item.querySelector(".more_item").classList.add("active3");
		}
	});

	if (url.pathname.split("/").length == 2) {
		document.querySelector(".my_work.aside_item_box").classList.add("active2");
		return;
	};
	if (url.pathname.includes("/team")) {
		document.querySelector(".team.aside_item_box").classList.add("active2");
		return;
	}
	if (url.pathname.includes("/project")) {
		const projectItemBox = document.querySelector(".project.aside_item_box")
		projectItemBox.classList.add("active2");
		projectItemBox.click();
		return;
	}
	if (url.pathname.includes("/filter")) {
		const filterItemBox = document.querySelector(".filter.aside_item_box")
		filterItemBox.classList.add("active2");
		filterItemBox.click();
		return;
	}
	if (url.pathname.includes("/dashboard")) {
		const dashboardItemBox = document.querySelector(".dashboard.aside_item_box")
		dashboardItemBox.classList.add("active2");
		dashboardItemBox.click();
		return;
	}
});

// 별표 표시 추가 제거(즐겨찾기)
document.querySelectorAll(".more_sub_item.star").forEach(function(btn) {
	btn.addEventListener("click", function() {
		let img = this.querySelector("img");
		let type = img.getAttribute("type-data");
		let idx = img.getAttribute("idx-data");
		let isLike = img.getAttribute("src") === "/images/star_icon_empty.svg" ? true : false;
		likeFetch(type, idx, isLike);
		location.reload();
	});
})

// filterBox reset
function filterBoxReset() {
	document
		.querySelectorAll(".like_filter_box > button")
		.forEach(function(btn) {
			btn.classList.remove("active");
		});
	document
		.querySelector(".like_filter_box > button:first-of-type")
		.classList.add("active");

	document
		.querySelector(".like_filter_container.active")
		?.classList.remove("active");
	document
		.querySelector(".filter_select_result_box.show")
		?.classList.remove("show");
	document.querySelector(".filter_select_result_item > span").innerText =
		"전체";

	document.querySelector(".like_content_list").classList.add("show");
	document.querySelector(".like_content_dynamic.show")?.classList.remove("show");

}

// 최근, 별표 표시됨
let prevRightBtn = "";
document.querySelector("body").addEventListener("click", function(e) {
	const filterContainer = e.target.closest(".like_filter_container");

	if (filterContainer !== null) {
		document.querySelector(".like_filter_box").classList.toggle("show");
	} else {
		document.querySelector(".like_filter_box").classList.remove("show");
		if (e.target.closest(".aside_click_view_container.show") === null) {
			filterBoxReset();
		}
	}

	const rightBoxBtn = e.target.closest(".view_right_box_btn");

	if (e.target.closest(".aside_click_view_container.show") || e.target.closest(".all_view_btn_box")) return;
	if (prevRightBtn === rightBoxBtn) {
		rightBoxBtn?.classList.toggle("active");
		rightBoxBtn
			?.querySelector(".aside_click_view_container")
			.classList.toggle("show");
	} else {
		document
			.querySelector(".view_right_box_btn.active")
			?.classList.remove("active");
		rightBoxBtn?.classList.add("active");

		document
			.querySelector(".aside_click_view_container.show")
			?.classList.remove("show");
		rightBoxBtn
			?.querySelector(".aside_click_view_container")
			.classList.add("show");
	}
	prevRightBtn = rightBoxBtn;
});

document.querySelectorAll(".like_content_list .img_box").forEach(function(btn) {
	btn.addEventListener("click", function(e) {
		e.preventDefault();
		let img = this.querySelector("img");
		if (img.getAttribute("src") === "/images/star_icon_yellow.svg") {
			img.setAttribute("src", "/images/star_icon_empty.svg");
		} else {
			img.setAttribute("src", "/images/star_icon_yellow.svg");
		}
		let type = img.getAttribute("type-data");
		let idx = img.getAttribute("idx-data");
		let isLike = img.getAttribute("src") === "/images/star_icon_empty.svg" ? false : true;
		likeFetch(type, idx, isLike);
	});
})

function removeRigthMoreBox(element) {
	const asideViewBtn = element.closest(".view_right_box_btn");
	asideViewBtn.classList.remove("active");
	const asideClickView = element.closest(".aside_click_view_container");
	asideClickView.classList.remove("show");
}

// 최근 항목 모두 보기
document.querySelector(".recent.all_view_btn_box").addEventListener("click", function() {
	const uri = new URL(window.location.href);
	if (uri.pathname === "/") {
		removeRigthMoreBox(this);
		document.querySelectorAll(".my_work_content").forEach(function(content) {
			content.classList.remove("show");
		});
		document.querySelector(".my_work_content.check").classList.add("show");

		document.querySelectorAll(".my_work_gnb > div").forEach(function(gnbItem) {
			if (gnbItem.innerText === "확인") {
				gnbItem.classList.add("active");
			} else {
				gnbItem.classList.remove("active");
			}
		});

		const newURL = '/?recentAll';
		history.pushState({}, '', newURL);
	} else {
		location.href = "/?recentAll"
	}

});

// 별표 표시된 항목 모두 보기
document.querySelector(".like.all_view_btn_box").addEventListener("click", function() {
	const uri = new URL(window.location.href);
	if (uri.pathname === "/") {
		removeRigthMoreBox(this);
		document.querySelectorAll(".my_work_content").forEach(function(content) {
			content.classList.remove("show");
		});
		document.querySelector(".my_work_content.like").classList.add("show");

		document.querySelectorAll(".my_work_gnb > div").forEach(function(gnbItem) {
			if (gnbItem.innerText === "별표 표시됨") {
				gnbItem.classList.add("active");
			} else {
				gnbItem.classList.remove("active");
			}
		});

		const newURL = '/?starAll';
		history.pushState({}, '', newURL);
	} else {
		location.href = "/?starAll"
	}
});

// 별표 표시됨 필터박스
document
	.querySelectorAll(".like_filter_box > button")
	.forEach(function(btn, idx) {
		btn.addEventListener("click", function() {
			const filterSelectBox = document.querySelector(
				".filter_select_result_box"
			);
			const filterContainer = document.querySelector(".like_filter_container");

			document
				.querySelector(".like_filter_box > button.active")
				.classList.remove("active");
			this.classList.add("active");
			if (idx !== 0) {
				filterContainer.classList.add("active");
				filterSelectBox.classList.add("show");
				filterSelectBox.querySelector("span").innerText =
					this.querySelector("span").innerText;

				let url = "";
				if (this.querySelector("span").innerText == "필터") {
					url = `/api/aside/like/filter`
				} else if (this.querySelector("span").innerText == "대시보드") {
					url = `/api/aside/like/dashboard`
				} else if (this.querySelector("span").innerText == "프로젝트") {
					url = `/api/aside/like/project`
				}

				fetch(url, { method: "GET" })
					.then(res => res.json())
					.then(res => {
						document.querySelector(".like_content_list.show")?.classList.remove("show");
						document.querySelector(".like_content_dynamic").classList.add("show");

						const dynamicContainer = document.querySelector(".like_content_dynamic");
						dynamicContainer.innerHTML = "";
						if (res.length === 0) {
							dynamicContainer.innerHTML = `<div class="no_search_box">
													<img src="/images/no_search_icon_light.svg">
													<p>원하는 항목을 찾을 수 없습니다.</p>
												  </div>`;
							return;
						}
						res.forEach(function(item) {
							const aElement = document.createElement("a");
							let href = "";
							if (item.iconFilename === "dashboard_icon.svg") {
								href = `/dashboard/detail/${item.idx}`;
							} else if (item.iconFilename === "filter_icon.svg") {
								href = `/filter/filter_issue?filter=${item.idx}`;
							} else {
								href = `/project/${item.key}/summation`;
							}
							aElement.setAttribute("href", href);
							aElement.classList.add("like_item_box", "accent_btn");
							aElement.innerHTML = `
									<div class="accent_border"></div>
									<div class="like_item">
										<img src="/images/${item.iconFilename}" alt="" width="24" height="24" />
										<span>${item.name}</span>
									</div>
									<button class="img_box">
										<img src="/images/star_icon_yellow.svg" alt="" width="16" height="16" />
									</button>`
							dynamicContainer.appendChild(aElement);
						});
					}).catch(error => {
						console.error("Fetch error:", error);
					});
			} else {// 전체 버튼 클릭시
				filterBoxReset();
			}
		});
	});

// project/create 이동
function goProjectCreatePage() {
	location.href = `/project/create`;
}

// dashboard create modal창 표시
function showDashboardCreate() {
	const modal = document.querySelector(".dashboard_craete_modal");
	modal.classList.add("show");
}

// 프로젝트, 필터, 대시보드
document.querySelectorAll(".view_under_box_btn").forEach(function(btn) {
	btn.addEventListener("click", function(e) {
		if (e.target.closest(".img_container") !== null) return;
		this.parentElement.nextElementSibling.classList.toggle("show");

		if (this.parentElement.nextElementSibling.className.includes("show")) {
			this.querySelector(".img_box.mouseover img").setAttribute(
				"src",
				"/images/arrow_under_icon.svg"
			);
		} else {
			this.querySelector(".img_box.mouseover img").setAttribute(
				"src",
				"/images/arrow_right_icon.svg"
			);
		}
	});
});

// more sub Box show 이벤트
let prevMoreSubBox = "";
document.querySelector("body").addEventListener("click", function(e) {
	const moreItemContainer = e.target.closest(".more_item_container");
	const viewMoreBox = e.target.closest(".view_more_box");
	const imgContainer = e.target.closest(".img_container");
	const imgBox = e.target.closest("aside .img_box");

	if (e.target.closest(".more_sub_box.show") !== null) return;
	if (
		e.target.closest("aside .img_box") === prevMoreSubBox &&
		prevMoreSubBox !== null
	) {
		e.preventDefault();

		imgContainer?.classList.toggle("show");
		imgBox?.classList.toggle("active");
		viewMoreBox?.classList.toggle("active");
		if (imgBox.className.includes("aside_more")) {
			moreItemContainer
				.querySelector(".more_sub_box.aside_more")
				.classList.toggle("show");
		} else if (imgBox?.className.includes("aside_plus")) {
			moreItemContainer
				.querySelector(".more_sub_box.aside_plus")
				.classList.toggle("show");
		}
	} else {
		document.querySelector("aside .more_sub_box.show")?.classList.remove("show");
		document.querySelector("aside .img_container.show")?.classList.remove("show");
		document.querySelector("aside .img_box.active")?.classList.remove("active");
		document.querySelector("aside .view_more_box.active")?.classList.remove("active");

		if (e.target.closest("aside .img_container") !== null) {
			e.preventDefault();
			imgContainer.classList.add("show");
			imgBox.classList.add("active");
			viewMoreBox.classList.add("active");
			if (imgBox.className.includes("aside_more")) {
				moreItemContainer
					.querySelector(".more_sub_box.aside_more")
					.classList.add("show");
			} else if (imgBox.className.includes("aside_plus")) {
				moreItemContainer
					.querySelector(".more_sub_box.aside_plus")
					.classList.add("show");
			}
		}
	}
	prevMoreSubBox = e.target.closest(".img_box");
});

// aside more box 보여주기 위해 클릭시 paddingRight 늘림 이벤트
document.querySelector("body").addEventListener("click", function() {
	const asideContianer = document.querySelector(".aside_container");
	const asideItemBox = document.querySelector(".aside_item_box.view_right_box_btn.active");
	const moreSubItem = document.querySelector(".more_sub_box.aside_more.show");
	const moreSubPlusItem = document.querySelector(".more_sub_box.aside_plus.show");
	if (asideItemBox !== null || moreSubItem !== null || moreSubPlusItem !== null) {
		asideContianer.style.paddingRight = "600px";
	} else {
		asideContianer.style.paddingRight = "0";
	}
});

// 기본값 필터 click 이벤트
document
	.querySelector(".more_item_box.default_filter_btn")
	.addEventListener("click", function() {
		if (
			this.querySelector("img").getAttribute("src") ===
			"/images/arrow_right_icon.svg"
		) {
			this.querySelector("img").setAttribute(
				"src",
				"/images/arrow_under_icon.svg"
			);
		} else {
			this.querySelector("img").setAttribute(
				"src",
				"/images/arrow_right_icon.svg"
			);
		}
		this.nextElementSibling.classList.toggle("show");
	});

// 추출한 username을 사용하여 동적으로 링크 생성
document.getElementById("filter_move").href = "/filter/filter_issue";

