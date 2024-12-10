document.querySelector("body").addEventListener("click", function(e) {
	const menu = e.target.closest(".filter_menu");  // 클릭된 메뉴 찾기
	const menuWithShow = document.querySelector(".filter_issue_box.show");  // 열린 .filter_issue_box 찾기
	const menuBoxWithActive = document.querySelector(".filter_category_div.active");  // 활성화된 메뉴 찾기
	const button = document.querySelector(".filter_category_div")
	console.log(e.target);
	
	// 이미 열린 .filter_issue_box를 클릭한 경우 아무 작업도 하지 않음
	if (menuWithShow && menuWithShow.contains(e.target)) {
		return;
	}

	// 열린 메뉴가 있다면 그 메뉴의 show 클래스를 제거하고 스타일 초기화
	if (menuWithShow) {
					menuWithShow.classList.remove("show");  // 열린 메뉴 숨기기
					if (menuBoxWithActive) {
							menuBoxWithActive.classList.remove("active");  // 활성화된 메뉴 스타일 초기화
						}
					return;
				}

	// 클릭된 메뉴가 존재하면 해당 메뉴 열기
	if (menu !== null) {
		const filterBox = menu.querySelector(".filter_issue_box");  // 클릭한 메뉴의 filter_issue_box 찾기
		const menuBox = menu.querySelector(".filter_category_div");  // 해당 메뉴의 부모 .filter_category_div 찾기

		
		// 이미 열린 상태라면 메뉴 닫기
		if (filterBox.classList.contains("show")) {
			filterBox.classList.remove("show");  // 메뉴 닫기
			menuBox.classList.remove("active");  // 스타일 초기화
			
		} else {
			// 클릭된 메뉴 열기
			filterBox.classList.add("show");  // 해당 메뉴 열기
			menuBox.classList.add("active");  // 메뉴 스타일 활성화
		}
	
	}
});

document.querySelector("body").addEventListener("click", function(e) {
	const account = e.target.closest(".filter_save_button");
	const modal = document.querySelector(".modal");
	const active = document.querySelector(".modal.active")
	const modalContent = document.querySelector(".modal_content")
	const cancleButton = modalContent.querySelector(".cancle_button")
	if (account) {
		modal.classList.add("active");
	}
	if(modal.classList.contains("active")){
		if(cancleButton && cancleButton.contains(e.target)){
			modal.classList.remove("active");
		}else if (!modalContent.contains(e.target) && !e.target.closest(".filter_save_button")) {
			modal.classList.remove("active");
		}
		
	}
		
});

document.querySelector(".button_div").addEventListener("click", function(e) {
	const issueListFilter = e.target.closest(".button_div");
	const issueListFilterbox = document.querySelector(".button_div_box");
	const issueListFiltershow = document.querySelector(".button_div_box.show");
	
	if(issueListFilterbox){
		issueListFilterbox.classList.toggle("show");
	}
		
});

document.querySelectorAll(".viewer_div_1").forEach(function(item){
  item.addEventListener("click", function(e){
    let view = e.target;  // 클릭된 요소를 바로 가져옵니다.

    let view1 = view.querySelector(".viewer_div_1_category");
    let view2 = view.querySelector(".viewer_div_2_category");
   

    if (view1) {
      let view1Category = view.querySelector(".viewer_div_1_category");
      let style = window.getComputedStyle(view1Category);
      if (style.display === "block") {
        view1Category.style.display = "none";
      } else {
        view1Category.style.display = "block";
      }
    } else if (view2) {
      let view2Category = view.querySelector(".viewer_div_2_category");
      let style = window.getComputedStyle(view2Category);
      if (style.display === "block") {
        view2Category.style.display = "none";
      } else {
        view2Category.style.display = "block";
      }
    }
  });
});


