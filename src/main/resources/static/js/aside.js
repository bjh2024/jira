// aside 어디 페이지인지 표시
document.addEventListener("DOMContentLoaded", function(){
	document.querySelector(".my_work").classList.remove("active2");
	let url = new URL(window.location.href);
	if(url.pathname.split("/").length == 2){
		document.querySelector(".my_work").classList.add("active2");
		return;
	};
	if(url.pathname.includes("/team")){
		document.querySelector(".team").classList.add("active2");
		return;
	}
});

// 별표 표시 추가 제거(즐겨찾기)
document.querySelectorAll(".more_sub_item.star").forEach(function(btn){
	btn.addEventListener("click",function(){
		let img = this.querySelector("img");		
		let type = img.getAttribute("type-data");
		let idx = img.getAttribute("idx-data");
		let isLike = img.getAttribute("src") === "/images/star_icon_empty.svg" ? true : false ;
		likeFetch(type, idx, isLike);
	});
})

// filterBox reset
function filterBoxReset() {
  document
    .querySelectorAll(".like_filter_box > button")
    .forEach(function (btn) {
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
document.querySelector("body").addEventListener("click", function (e) {
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

  if (e.target.closest(".aside_click_view_container.show")) return;
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

document.querySelectorAll(".like_content_list .img_box").forEach(function(btn){
	btn.addEventListener("click", function(e){
		e.preventDefault();
		let img = this.querySelector("img");
		if(img.getAttribute("src") === "/images/star_icon_yellow.svg"){
			img.setAttribute("src", "/images/star_icon_empty.svg");
		}else{
			img.setAttribute("src", "/images/star_icon_yellow.svg");
		}
		let type = img.getAttribute("type-data");
		let idx = img.getAttribute("idx-data");
		let isLike = img.getAttribute("src") === "/images/star_icon_empty.svg" ? false : true;
		likeFetch(type, idx, isLike);
	});
})

// 별표 표시됨 필터박스
document
  .querySelectorAll(".like_filter_box > button")
  .forEach(function (btn, idx) {
    btn.addEventListener("click", function () {
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
		  if(this.querySelector("span").innerText == "보드"){
			document.querySelector(".like_content_list").classList.add("show");
			document.querySelector(".like_content_dynamic.show")?.classList.remove("show");
			return;
		  }else if(this.querySelector("span").innerText == "필터"){
		  	url = `/api/aside/like/filter?uri=${window.location.pathname}`
		  }else if(this.querySelector("span").innerText == "대시보드"){
		    url = `/api/aside/like/dashboard?uri=${window.location.pathname}`
		  }else if(this.querySelector("span").innerText == "프로젝트"){
			url = `/api/aside/like/project?uri=${window.location.pathname}`
		  }
		  
		  fetch(url,{method: "GET"})
		  .then(res => res.json())
		  .then(res => {
				document.querySelector(".like_content_list.show")?.classList.remove("show");
		 		document.querySelector(".like_content_dynamic").classList.add("show");
				
				const dynamicContainer = document.querySelector(".like_content_dynamic");
				dynamicContainer.innerHTML = "";
				if(res.length === 0){
					dynamicContainer.innerHTML = `<div class="no_search_box">
													<img src="/images/no_search_icon_light.svg">
													<p>원하는 항목을 찾을 수 없습니다.</p>
												  </div>`;
					return;
				}
				res.forEach(function(item){
					const aElement = document.createElement("a");
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
function goProjectCreatePage(element){
	let jiraName = element.getAttribute("data-jira-name");
	location.href = `/${jiraName}/project/create`;
}
  
// 프로젝트, 필터, 대시보드
document.querySelectorAll(".view_under_box_btn").forEach(function (btn) {
  btn.addEventListener("click", function (e) {
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
document.querySelector("body").addEventListener("click", function (e) {
  const moreItemContainer = e.target.closest(".more_item_container");
  const viewMoreBox = e.target.closest(".view_more_box");
  const imgContainer = e.target.closest(".img_container");
  const imgBox = e.target.closest(".img_box");
  
  if (e.target.closest(".more_sub_box.show") !== null) return;
  if (
    e.target.closest(".img_box") === prevMoreSubBox &&
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
    document.querySelector(".more_sub_box.show")?.classList.remove("show");
    document.querySelector(".img_container.show")?.classList.remove("show");
    document.querySelector(".img_box.active")?.classList.remove("active");
    document.querySelector(".view_more_box.active")?.classList.remove("active");

    if (e.target.closest(".img_container") !== null) {
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

// 기본값 필터 click 이벤트
document
  .querySelector(".more_item_box.default_filter_btn")
  .addEventListener("click", function () {
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
