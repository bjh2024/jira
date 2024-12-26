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

// 추가 작업 클릭 이벤트
document.querySelector("body").addEventListener("click", function(e){
	const btn = e.target.closest(".project_list_main td:last-of-type .img_box");
	if(btn === null) {
		document.querySelector(".project_list_main td:last-of-type .img_box.active")?.classList.remove("active");
		document.querySelector(".project_list_main td:last-of-type .more_box.show")?.classList.remove("show");
	}
});

document.querySelectorAll(".project_list_main td:last-of-type .img_box").forEach(function(btn){
	btn.addEventListener("click", function(e){
		const previousBtn = document.querySelector(".project_list_main td:last-of-type .more_box.show");
		document.querySelector(".project_list_main td:last-of-type .img_box.active")?.classList.remove("active");
		document.querySelector(".project_list_main td:last-of-type .more_box.show")?.classList.remove("show");
		
		if(previousBtn !== this.nextElementSibling){
			this.classList.add("active");
			this.nextElementSibling.classList.add("show");	
		}
	});
});

document.querySelector("body").addEventListener("mousedown", function(e){
	const modal = document.querySelector(".project_delete_modal");
	const projectDeleteBox = e.target.closest(".project_delete_box");
	const cancleBtn = e.target.closest(".cancle_btn");
	const deleteBtn = e.target.closest(".delete_btn");
	
	if(projectDeleteBox === null || cancleBtn !== null){
		modal.classList.remove("show");
		return;
	}
	
	if(deleteBtn !== null){
		const uri = `/api/project/delete`;
		const projectIdx = modal.getAttribute("idx-data");
		fetch(uri, {method:"post",
				    headers:{"Content-Type":"application/json"}, 
					body:JSON.stringify(projectIdx)})
		.catch(err => {
			console.error(err);
		});
		location.href = "/project/list";
	}
});

document.querySelectorAll(".project_list_main td:last-of-type .more_box").forEach(function(moreBox){
	moreBox.addEventListener("click", function(e){
		const removeBtn = e.target.closest(".remove");
		if(removeBtn === null) return;
		const projectIdx = this.getAttribute("idx-data");
		const projectName = this.getAttribute("project-name-data");
		
		const deleteModal = document.querySelector(".project_delete_modal");
		deleteModal.setAttribute("idx-data",projectIdx);
		deleteModal.classList.add("show");
		
		const projectDeleteBoxH2 = deleteModal.querySelector(".project_delete_box h2");
		projectDeleteBoxH2.innerHTML = `<img src="/images/alaret_icon.svg" width="16" height="16"/>
										<span>${projectName}을(를) 삭제하겠습니까?</span>`;
	});
});




