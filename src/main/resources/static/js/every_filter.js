document.querySelector("body").addEventListener("click", function(e) {
    const menu = e.target.closest(".select_top"); // 클릭된 .select_top 요소 찾기
    const menuWithShow = document.querySelector(".select_top_menu.show"); // 열린 메뉴 찾기

    // 만약 클릭된 곳이 .select_top이 아니고 열린 메뉴도 없다면
    if (menuWithShow && !menu) {
        menuWithShow.classList.remove("show"); // 열린 메뉴 숨기기
        menuWithShow.closest(".select_top").style.border = ""; // 해당 메뉴의 테두리 초기화
    }

    // 클릭된 메뉴가 .select_top인 경우
    if (menu !== null) {
        const filterBox = menu.querySelector(".select_top_menu"); // 클릭한 메뉴의 select_top_menu 찾기

        // 이미 열린 상태라면 메뉴 닫기
        if (filterBox.classList.contains("show")) {
            menu.style.border = ""; // 메뉴 테두리 초기화
            filterBox.classList.remove("show"); // 메뉴 닫기
        } else {
            // 다른 메뉴가 열려있는 상태라면 먼저 닫기
            if (menuWithShow) {
                menuWithShow.classList.remove("show");
                menuWithShow.closest(".select_top").style.border = ""; // 열린 메뉴의 테두리 초기화
            }
            // 클릭된 메뉴 열기
            menu.style.border = "2px solid #0C66E4"; // 클릭된 메뉴에 테두리 추가
            filterBox.classList.add("show"); // 해당 메뉴 열기
        }
    }
});
document.querySelectorAll(".delete_button").forEach(function(item){
	item.addEventListener("mouseup",function(){
		let delete_button_1 = this.querySelector(".delete_button_1");
		if(delete_button_1.style.display == "none"){
			delete_button_1.style.display = "block"
		}else{
			delete_button_1.style.display = "none"
		}
	})
})
document.querySelectorAll(".delete_button_1").forEach(function(item){
	item.addEventListener("click",function(){
		event.stopPropagation();
		let parent = this.closest(".delete_button");
		if(parent.querySelector(".delete_modal").style.display == "none"){
		parent.querySelector(".delete_modal").style.display = "block";
		}else{
		parent.querySelector(".delete_modal").style.display = "none";
		}
	})
})
document.querySelectorAll(".delete_alert_cancelbtn").forEach(function(item){
	item.addEventListener("click",function(){
		event.stopPropagation();
		let parent = this.closest(".delete_button");
		if(parent.querySelector(".delete_modal").style.display == "block"){
		parent.querySelector(".delete_modal").style.display = "none";
		parent.querySelector(".delete_button_1").style.display = "none";
		}else{
		
		}
	})
})