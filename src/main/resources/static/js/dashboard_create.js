function resetDashboardCreate(idx) {
	// 프로젝트 reset
	const roleProject1 = document.querySelectorAll(".role_projct_item>div")[idx*2];
	const roleProject2 = document.querySelectorAll(".role_projct_item>div")[idx*2+1];
	roleProject1.querySelector("input").placeholder = "프로젝트 선택";
	roleProject2.querySelector("input").placeholder = "모든 역할";

	roleProject1.querySelector("input").nextElementSibling?.children[0].classList.add("active");
	roleProject2.querySelector("input").nextElementSibling?.children[0].classList.add("active");

	roleProject1.classList.remove("show");
	roleProject2.classList.remove("show");

	// 그룹 reset
	const roleGroup = document.querySelectorAll(".role_group_item")[idx];
	roleGroup.querySelector("input").placeholder = "그룹 선택";

	roleGroup.querySelector("input").nextElementSibling?.children[0].classList.add("active");

	roleGroup.classList.remove("show");

	// 사용자 reset
	const roleUser = document.querySelectorAll(".role_user_item")[idx];
	roleUser.querySelector("input").placeholder = "사용자 선택";

	roleUser.querySelector("input").nextElementSibling?.children[0].classList.add("active");

	roleUser.classList.remove("show");
	
	// 비공개 reset
	const rolePrivate = document.querySelector(".role_private_item");
	rolePrivate.classList.remove("show");
}

document.querySelectorAll(".role_input_item .role_input_lnb_item").forEach(function(item, idx) {
	item.addEventListener("mousedown", function(e) {
		// 클릭 타겟 기준 부모 찾아서 active 제거
		e.target.closest(".role_input_item").querySelector(".active").classList.remove("active");
		this.classList.add("active");
		const itemIdx = Math.floor(idx / 4); // (프로젝트, 그룹, 사용자, 비공개) * 2 => 총 8개

		resetDashboardCreate(itemIdx); // idx=0: 조회자 reset, idx=1: 편집자 reset

		switch (this.children[1].innerText) {
			case "프로젝트":
				document.querySelectorAll(".role_projct_item > div:nth-of-type(1)")[itemIdx].classList.add("show");
				break;
			case "그룹":
				document.querySelectorAll(".role_group_item")[itemIdx].classList.add("show");
				break;
			case "사용자":
				document.querySelectorAll(".role_user_item")[itemIdx].classList.add("show");
				break;
			case "비공개":
				document.querySelectorAll(".role_private_item")[itemIdx].classList.add("show");
				break;
		}

	});
});

// 프로젝트 선택 클릭시 다음 아이템 보여주기
document.querySelectorAll(".role_project_input").forEach(function(input, idx){
	input.addEventListener("blur", function(){
		if(this.placeholder !== "프로젝트 선택"){
			document.querySelectorAll(".role_projct_item > div:nth-of-type(2)")[idx].classList.add("show");
		}else{
			document.querySelectorAll(".role_projct_item > div:nth-of-type(2)")[idx].classList.remove("show");
		}
	})
})

document.querySelectorAll(".role_input_lnb_item").forEach(function(item){
	item.addEventListener("mousedown", function(){
		const imgSrc = this.children[0].getAttribute("src");
		const span = this.children[1].innerText;
		console.log(imgSrc, span);
	})
})

