const pageNumArr = document.querySelectorAll(".project_list_paging_box ul li");

pageNumArr.forEach(function(page){
	page.addEventListener("click", function(e){
		document.querySelector(".project_list_paging_box ul li.active").classList.remove("active");
		this.classList.add("active");
	});
});

// 즐겨찾기 추가, 제거
const likeBtnImgs = document.querySelectorAll(".project_list_main td:first-of-type button img");

likeBtnImgs.forEach(function(img){
	img.addEventListener("click", function(e){
		if(e.target.getAttribute("src") === "/images/star_icon_empty.svg"){
			e.target.setAttribute("src", "/images/star_icon_yellow.svg");
		}else{
			e.target.setAttribute("src", "/images/star_icon_empty.svg");
		}
		let idx = e.target.getAttribute("idx-data");
		let isLike = e.target.getAttribute("src") === "/images/star_icon_empty.svg" ? false : true;
		likeFetch("project", idx, isLike);
	});
});