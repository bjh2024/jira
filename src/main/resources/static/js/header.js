let previtem = "";
// 박스 밖을 클릭했을때 show => hidden
document.querySelector("body").addEventListener("mousedown", function(e) {
	if (e.target.closest(".show")?.className.includes("show")) {
		return;
	}

	const activeItem = document.querySelector(".jira_header .item.active");
	const lnbShowBox = document.querySelector(".jira_header .item_lnb.show");
	const searchLnbShowBox = document.querySelector(".jira_header .search_lnb_box.show");
	const gnb2BoxActiveIcon = document.querySelector(".jira_header .gnb2_box_icon.active");
	const gnb2BoxShowContent = document.querySelector(".jira_header .gnb2_box_content.show");

	activeItem?.classList.remove("active");
	lnbShowBox?.classList.remove("show");

	searchLnbShowBox?.classList.remove("show");

	gnb2BoxActiveIcon?.classList.remove("active");
	gnb2BoxShowContent?.classList.remove("show");

	// gnb1_box 클릭시
	// 클릭해야되는 아이템
	const item = e.target.closest(".jira_header .item");
	if (item !== null) {
		item.classList.add("active");
		// 클릭시 보여줄 lnb아이템
		item.nextElementSibling.classList.add("show");
	}

	// search_Box 클릭시
	// 클릭해야되는 아이템
	const searchBox = e.target.closest(".jira_header .search_box");
	// item 클릭시 보여줄 lnb아이템
	const searchLnbBox = document.querySelector(".jira_header .search_lnb_box");
	// 클릭시 input의 width 늘어남
	const searchBoxInput = document.querySelector(".jira_header .search_box input");
	if (searchBox !== null) {
		searchBoxInput.style.width = "730px";
		searchLnbBox.classList.add("show");
		document.querySelector(".jira_header .search_box > input").placeholder = "Search Jira"
	} else {
		document.querySelector(".jira_header .search_box > input").style.width = "200px";
		document.querySelector(".jira_header .search_box > input").placeholder = "검색"
	}

	// gnb2_box 클릭시
	// 클릭해야되는 아이템
	const gnbBoxBtn = e.target.closest(".jira_header .gnb2_box_btn");

	if (gnbBoxBtn !== null) {
		gnbBoxBtn.children[0]?.classList.add("active");
		// 아이템 클릭시 보여줄 lnb 아이템
		gnbBoxBtn.children[1]?.classList.add("show");
	}

});
// header box1 div 클릭시
const lngBox1Header = document.querySelectorAll(".item_lnb_header>div");
lngBox1Header.forEach(function(headerItem) {
	headerItem.addEventListener("click", function(e) {
		document.querySelector(".item_lnb_header>.active").classList.remove("active");
		e.target.classList.add("active");
		document.querySelector(".item_lnb >.show")?.classList.remove("show");
		if (e.target.innerText === "나에게 할당") {
			document.querySelector(".my_assignment_main")?.classList.add("show");
		} else if (e.target.innerText === "최근") {
			document.querySelector(".recent_main")?.classList.add("show");
		} else {
			document.querySelector(".board_main")?.classList.add("show");
		}
	})
})
// .search_lnb_box_header의 div 클릭시 이벤트
const searchLbnHeaderDivs = document.querySelectorAll(".search_lnb_box_header>div");
searchLbnHeaderDivs.forEach(function(div) {
	div.addEventListener("click", function() {
		document.querySelector(".search_lnb_box_header>.active").classList.remove("active");
		this.classList.add("active");
	});
})
const searchCancleBtn = document.querySelector(".search_box>img:nth-of-type(2)");
searchCancleBtn.addEventListener("click", function() {
	document.querySelector(".search_box > input").value = "";
	document.querySelector(".search_box > input").focus();
});

// last_update_box의 li 클릭시 이벤트
const lastUpdateBtn = document.querySelectorAll(".last_update_box>ul>li");
lastUpdateBtn.forEach(function(btn) {
	btn.addEventListener("click", function() {
		document.querySelector(".last_update_box>ul>li.active").classList.remove("active");
		this.classList.add("active");
	});
});

// 대시보드 만들기 클릭시 모달창
document
	.querySelector(".dashbaord_crate_header_btn")
	.addEventListener("click", function() {
		document.querySelector(".dashboard_craete_modal").classList.add("show");
	});

// jira에 사용자 초대 클릭시 모달창
document
	.querySelector(".jira_user_invite_btn")
	.addEventListener("click", function() {
		document.querySelector(".jira_user_add_modal").classList.add("show");
	});

// 팀 만들기 클릭시 모달창
document
	.querySelector(".team_create_modal_btn")
	.addEventListener("click", function() {
		document.querySelector(".team_create_modal").classList.add("show");
	});

// 별표 클릭시 즐겨찾기 추가 제거
document.querySelectorAll(".jira_header .star_box").forEach(function(box) {
	box.addEventListener("click", function(e) {
		e.preventDefault();

		const starImg = this.children[0];
		if (starImg.getAttribute("src") === "/images/star_icon_yellow.svg") {
			starImg.setAttribute("src", "/images/empty_star_icon.svg");
		} else {
			starImg.setAttribute("src", "/images/star_icon_yellow.svg")
		}

	});
});

// (직접, 관찰중)알림 클릭시 active 추가 제거
document.querySelectorAll(".alarm_gnb_box > div").forEach(function(box){
	box.addEventListener("click", function(e){
		document.querySelector(".alarm_gnb_box > .active").classList.remove("active");
		this.classList.add("active");
	})
})